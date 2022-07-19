<template>
    <div class="upload">
        <el-upload drag :action="uploadUrl()" :show-file-list="false" :on-success="handleAvatarSuccess"
            :headers="headers" :before-upload="beforeAvatarUpload">
            <el-icon class="el-icon--upload">
                <upload-filled />
            </el-icon>
            <div class="el-upload__text">将文件拖到此处或点击上传</div>
            <template #tip>
                <p class="el-upload__tip">只能上传 {{ uploadTypes.join("、") }} 文件, 且不超过10M</p>
            </template>
        </el-upload>
    </div>
</template>

<script setup>
import { ref, computed } from "vue";
import { useStore } from "vuex";
import { UploadFilled } from "@element-plus/icons-vue";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";
import { RESPONSE_CODE } from "@/enums";

const store = useStore();

const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);
const userId = computed(() => store.getters.userId);

const headers = computed(() => {return {token: store.getters.token}})

function uploadUrl() {
    return HttpManager.uploadUrl(userId.value);
}

function beforeAvatarUpload(file) {
    const ltCode = 2;
    const isLt10M = file.size / 1024 / 1024;
    const isExistFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

    if (isLt10M > ltCode || isLt10M <= 0) {
        ElMessage.error(`图片大小范围是 0~${ltCode}MB!`)
    }
    if (!isExistFileType) {
        ElMessage.error(`图片只支持 ${uploadTypes.value.join("、")} 格式!`)
    }
    return isLt10M && isExistFileType;
}

function handleAvatarSuccess(response, file) {
    ElMessage({
        message: response.message,
        type: response.type,
    })

    if (response.code === RESPONSE_CODE.SUCCESS) store.commit("setUserPic", response.data);
}

</script>

