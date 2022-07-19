<template>
  <!-- form -->
  <div id="building">
    <div class="sign">
      <div class="sign-head">
        <span>用户注册</span>
      </div>
      <el-form ref="signUpForm" label-width="70px" status-icon :model="registerForm" :rules="SignUpRules">
        <el-form-item prop="username" label="用户名">
          <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" placeholder="密码" v-model="registerForm.password"></el-input>
        </el-form-item>
        <el-form-item prop="sex" label="性别">
          <el-radio-group v-model="registerForm.sex">
            <el-radio :label="0">女</el-radio>
            <el-radio :label="1">男</el-radio>
            <el-radio :label="2">保密</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item prop="phoneNum" label="手机">
          <el-input placeholder="手机" v-model="registerForm.phoneNum"></el-input>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
        </el-form-item>
        <el-form-item prop="birth" label="生日">
          <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width: 100%">
          </el-date-picker>
        </el-form-item>
        <el-form-item prop="introduction" label="签名">
          <el-input type="textarea" placeholder="签名" v-model="registerForm.introduction"></el-input>
        </el-form-item>
        <el-form-item prop="location" label="地区">
          <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%">
            <el-option v-for="item in AREA" :key="item.value" :label="item.label" :value="item.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item class="sign-btn">
          <el-button type="primary" @click="handleSignUp(signUpForm)">确定</el-button>
          <el-button :type="confirmBtnType" :status="confirmed" @click="resetForm">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>

</template>
    
<script setup>
import { ref, reactive, getCurrentInstance } from 'vue';
import { useStore } from 'vuex'
import { useRouter } from 'vue-router'
import { SignUpRules, NavName, RouterName, AREA } from '@/enums'
import utils from '@/utils'
import md5 from 'js-md5'
import { HttpManager } from '@/api'
import { ElMessage } from 'element-plus'

const signUpForm = ref()

const { changeActiveName, routerManager, getBirth } = utils()

const store = useStore()
const router = useRouter()

const confirmed = ref(false)
const confirmBtnType = ref("warning")

const registerForm = reactive({
  username: "",
  password: "",
  sex: "",
  phoneNum: "",
  email: "",
  birth: new Date(),
  introduction: "",
  location: "",
});

async function handleSignUp(formRef) {
  let canRun = true;

  formRef.validate((valid) => {
    if (!valid) return (canRun = false)
  })
  if (!canRun) return;

  const userInfo = {
    username: registerForm.username,
    password: md5(registerForm.password),
    sex: registerForm.sex,
    phoneNum: registerForm.phoneNum,
    email: registerForm.email,
    birth: getBirth(registerForm.birth),
    introduction: registerForm.introduction,
    location: registerForm.location,
    role: 0
  }

  try {

    const result = (await HttpManager.signUp(JSON.stringify(userInfo)));

    ElMessage({
      message: result.message,
      type: result.type,
    })

    if (result.message === 'SUCCESS') {
      changeActiveName(NavName.LogIn);
      routerManager(RouterName.LogIn, { path: RouterName.LogIn });
    }


  } catch (error) {
    console.error(error);
  }
}

function resetForm() {
  if (confirmed.value) {
    registerForm.username = ""
    registerForm.password = ""
    registerForm.sex = ""
    registerForm.phoneNum = ""
    registerForm.email = ""
    registerForm.birth = new Date()
    registerForm.introduction = ""
    registerForm.location = ""
    confirmBtnType.value = "warning"
    confirmed.value = false
  } else {
    confirmBtnType.value = "danger"
    confirmed.value = true
  }

}



</script>
    
<style lang="scss" scoped>
@import "@/assets/css/sign.scss";
</style>

    