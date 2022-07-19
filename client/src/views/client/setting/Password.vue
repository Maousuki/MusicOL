<template>
    <el-form ref="passwordForm" label-width="70px" :model="form" :rules="rules">
        <el-form-item label="旧密码" prop="oldPassword">
            <el-input type="password" v-model="form.oldPassword" />
        </el-form-item>
        <el-form-item label="新密码" prop="newPassword">
            <el-input type="password" v-model="form.newPassword" />
        </el-form-item>
        <el-form-item label="密码确认" prop="confirmPassword">
            <el-input type="password" v-model="form.confirmPassword" />
        </el-form-item>
        <el-form-item>
            <el-button @click="clearData()">重置</el-button>
            <el-button type="primary" @click="confirm(passwordForm)">确认修改</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import { ref, computed, reactive } from "vue";
import { useStore } from "vuex";
import utils from "@/utils";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";
import md5 from "js-md5"
import { RESPONSE_CODE, passwordRule } from "@/enums";

const store = useStore();
const { goBack } = utils();

const passwordForm = ref()

const form = reactive({
    oldPassword: "",
    newPassword: "",
    confirmPassword: "",
});
const userId = computed(() => store.getters.userId);
const username = computed(() => store.getters.username);

const validateCheck = (rule, value, callback) => {
    if (value === "") {
        callback(new Error("请确认密码"));
    } else if (value !== form.newPassword) {
        callback(new Error("确认密码与新密码不相同"));
    } else {
        callback();
    }
};

const rules = reactive({
    oldPassword: passwordRule,
    newPassword: passwordRule,
    confirmPassword: [{ validator: validateCheck, trigger: "blur"}, passwordRule[0]],
});

async function clearData() {
    form.oldPassword = "";
    form.newPassword = "";
    form.confirmPassword = "";
}

async function confirm(formRef) {
    let canRun = true;
    formRef.validate((valid) => {
        if (!valid) return (canRun = false);
    });
    if (!canRun) return;

    const data = {
        id: userId.value,
        username: username.value,
        oldPassword: md5(form.oldPassword),
        password: md5(form.newPassword),
    }

    const result = (await HttpManager.updateUserPassword(data, { headers: { 'token': store.getters.token } }));
    ElMessage({
        message: result.message,
        type: result.type,
    })
    if (result.code === RESPONSE_CODE.SUCCESS) clearData();
}
</script>

