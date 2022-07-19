<template>
    <div id="building">
        <div class="sign">
            <div class="sign-head">
                <h3>账号登录</h3>
            </div>
            <el-form :model="logInForm" ref="form" :rules="logInRules" label-width="80px" :inline="false">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="logInForm.username" placeholder="username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input v-model="logInForm.password" placeholder="password" clearable type="password"
                        @keyup.enter="handleLogIn(form)">
                    </el-input>

                </el-form-item>

                <el-form-item class="sign-btn">
                    <el-button type="primary" @click="handleLogIn(form)">登录</el-button>
                    <el-button size="default" @click="goSignUp">注册</el-button>

                </el-form-item>
            </el-form>
        </div>
    </div>


</template>
    
<script setup>
import { reactive, ref } from 'vue';
import { logInRules, RouterName, NavName } from '@/enums';
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus'
import md5 from 'js-md5'
import { HttpManager } from '@/api';
import utils from '@/utils';

const store = useStore()

const form = ref()

const { changeActiveName, routerManager } = utils()

const logInForm = reactive({
    username: "",
    password: "",
})

async function handleLogIn(formRef) {
    let canRun = true
    formRef.validate((valid) => {
        if (!valid) return (canRun = false)
    })
    if (!canRun) return

    const data = {
        username: logInForm.username,
        password: md5(logInForm.password),
    }



    try {
        const result = await HttpManager.logIn(JSON.stringify(data))

        if (result.message === "SUCCESS") {
            ElMessage.success('登录成功')
            store.commit("setUserId", result.data.user.id)
            store.commit("setUsername", result.data.user.username)
            store.commit("setUserPic", result.data.user.avatar)
            store.commit("setToken", result.data.token)
            store.commit("setUserRole", result.data.user.role)

            // 更新store后返回主页
            changeActiveName(NavName.Home)
            routerManager(RouterName.Home, { path: RouterName.Home })
        } else {
            ElMessage.error('登录失败:' + result.message)
        }
    } catch (error) {
        // console.log(error)
    }
}

function goSignUp() {
    routerManager(RouterName.SignUp, { path: RouterName.SignUp })
}

</script>
    
<style lang="scss" scoped>
@import "@/assets/css/sign.scss";
</style>

    