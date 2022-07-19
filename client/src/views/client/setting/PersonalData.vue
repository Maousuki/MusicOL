<template>
  <el-form ref="updateForm" label-width="70px" :model="registerForm" :rules="SignUpRules">
    <el-form-item prop="username" label="用户名">
      <el-input v-model="registerForm.username" placeholder="用户名"></el-input>
    </el-form-item>
    <el-form-item label="性别">
      <el-radio-group v-model="registerForm.sex">
        <el-radio :label="0">女</el-radio>
        <el-radio :label="1">男</el-radio>
        <el-radio :label="2">保密</el-radio>
      </el-radio-group>
    </el-form-item>
    <el-form-item prop="birth" label="生日">
      <el-date-picker type="date" placeholder="选择日期" v-model="registerForm.birth" style="width: 100%"></el-date-picker>
    </el-form-item>
    <el-form-item prop="introduction" label="签名">
      <el-input type="textarea" maxlength="128" show-word-limit placeholder="签名" v-model="registerForm.introduction"></el-input>
    </el-form-item>
    <el-form-item prop="location" label="地区">
      <el-select v-model="registerForm.location" placeholder="地区" style="width: 100%">
        <el-option v-for="item in AREA" :key="item.value" :label="item.label" :value="item.value"> </el-option>
      </el-select>
    </el-form-item>
    <el-form-item prop="phoneNum" label="手机">
      <el-input placeholder="手机" v-model="registerForm.phoneNum"></el-input>
    </el-form-item>
    <el-form-item prop="email" label="邮箱">
      <el-input v-model="registerForm.email" placeholder="邮箱"></el-input>
    </el-form-item>
    <el-form-item>
      <el-button @click="goBack(-1)">取消</el-button>
      <el-button type="primary" @click="saveMsg(updateForm)">保存</el-button>
    </el-form-item>
  </el-form>
</template>
    
<script setup>
import { computed, onMounted, reactive, ref } from "vue";
import { useStore } from "vuex";
import utils from "@/utils";
import { AREA, SignUpRules } from "@/enums";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";

const store = useStore()
const { getBirth, goBack } = utils()

const updateForm = ref()

// 注册
    const registerForm = reactive({
      username: "",
      sex: "",
      phoneNum: "",
      email: "",
      birth: new Date(),
      introduction: "",
      location: "",
      userPic: "",
    });
    const userId = computed(() => store.getters.userId);

    async function getUserInfo(id) {
      const result = (await HttpManager.getUserOfId(id));
      registerForm.username = result.data.username;
      registerForm.sex = result.data.sex;
      registerForm.phoneNum = result.data.phoneNum;
      registerForm.email = result.data.email;
      registerForm.birth = result.data.birth;
      registerForm.introduction = result.data.introduction;
      registerForm.location = result.data.location;
      registerForm.userPic = result.data.avatar;
    }

    async function saveMsg(formRef) {
      let canRun = true;

      formRef.validate((valid) => {
        if (!valid) return (canRun = false);
      });
      if (!canRun) return;

      const data = {
        "id": userId.value,
        "username": registerForm.username,
        "sex": registerForm.sex,
        "phoneNum": registerForm.phoneNum,
        "email": registerForm.email,
        "birth": getBirth(registerForm.birth),
        "introduction": registerForm.introduction,
        "location": registerForm.location
      }

      const result = (await HttpManager.updateUserMsg(JSON.stringify(data),{headers: {'token': store.getters.token}}));
      ElMessage({
        message: result.message,
        type: result.type,
      })
      if (result.success) {
        store.commit("setUsername", registerForm.username);
        goBack(-1);
      }
    }

    onMounted(() => {
      getUserInfo(userId.value);
    });
</script>