<template>
  <div class="setting">
    <h1>设置</h1>
    <el-tabs tab-position="left">
      <el-tab-pane label="个人资料" class="content">
        <Personal-data></Personal-data>
      </el-tab-pane>
      <el-tab-pane label="更改密码" class="content">
        <Password></Password>
      </el-tab-pane>
      <el-tab-pane label="账号和安全" class="content">
        <el-button type="danger" :icon="Delete" @click="cancelAccount">注销账号</el-button>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>
    
<script setup>
import { computed, reactive } from "vue";
import { Delete } from "@element-plus/icons-vue";
import PersonalData from "./PersonalData.vue";
import Password from "./Password.vue";
import { HttpManager } from "@/api";
import { useStore } from "vuex";
import utils from "@/utils";
import { RouterName } from "@/enums";
import { ElMessage } from "element-plus";

    const store = useStore();
    const { routerManager } = utils();

    const userId = computed(() => store.getters.userId);

    async function cancelAccount() {
      const result = (await HttpManager.deleteUser(userId.value,{headers: {'token': store.getters.token}}));
      ElMessage({
        message: result.message,
        type: result.type,
      })
      routerManager(RouterName.LogIn, { path: RouterName.LogIn });
      store.commit("setToken", "");
    }
</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

h1 {
  border-bottom: 1px solid $color-grey;
}

.content {
  padding-top: 20px;
  text-align: center;
}

@media screen and (min-width: $sm) {
  .setting {
    margin: 30px 10%;
    margin-top: 0;
    padding: 20px;
    min-height: 60vh;
  }
}

@media screen and (max-width: $sm) {
  .setting {
    padding: 20px;
  }
}
</style>
