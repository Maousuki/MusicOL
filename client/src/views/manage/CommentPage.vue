<template>
  <el-breadcrumb class="crumbs" separator="/">
    <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.name" :to="{ path: item.path, query: item.query }">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>

  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-input v-model="searchWord" placeholder="筛选关键词"></el-input>
    </div>
    <el-table height="550px" border size="small" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column prop="id" label="ID" width="50"></el-table-column>
      <el-table-column prop="username" label="用户" width="80"></el-table-column>
      <el-table-column prop="content" label="评论内容"></el-table-column>
      <el-table-column label="操作" width="100" align="center">
        <template v-slot="scope">
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 删除提示框 -->
  <del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event" />
</template>

<script setup>
import { watch, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { HttpManager } from "@/api";
import { ElMessage } from "element-plus";
import DelDialog from "@/components/manage/dialog/DelDialog.vue"


const store = useStore();
const route = useRoute();

const tableData = ref([]); // 记录歌曲，用于显示
const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
const breadcrumbList = computed(() => store.getters.breadcrumbList);

const searchWord = ref(""); // 记录输入框输入的内容
watch(searchWord, () => {
  if (searchWord.value === "") {
    tableData.value = tempDate.value;
  } else {
    tableData.value = [];
    for (let item of tempDate.value) {
      if (item.username.includes(searchWord.value)) {
        tableData.value.push(item);
      }
    }
  }
});

getData();

// 获取评论
function getData() {
  tableData.value = [];
  tempDate.value = [];
  let promise = null;
  
  if (route.query.type == "0") {
    promise = HttpManager.getCommentOfSongId(route.query.id);
  } else if (route.query.type == "1") {
    promise = HttpManager.getCommentOfSongListId(route.query.id);
  }

  promise.then((res) => {
    for (let item of res.data) {
      getUsers(item.userId, item);
    }
  });
}
async function getUsers(id, item) {
  const result = (await HttpManager.getUserOfId(id))
  item.username = result.data.username;
  tableData.value.push(item);
  tempDate.value.push(item);
}

/**
 * 删除
 */
const idx = ref(-1); // 记录当前要删除的行
const multipleSelection = ref([]); // 记录当前要删除的列表
const delVisible = ref(false); // 显示删除框

async function confirm() {
  const result = (await HttpManager.deleteComment(idx.value, { headers: { 'token': store.getters.token } }))
  ElMessage({
    message: result.message,
    type: result.type,
  })

  if (result.message === "SUCCESS") getData();
  delVisible.value = false;
}
function deleteRow(id) {
  idx.value = id;
  delVisible.value = true;
}
function handleSelectionChange(val) {
  multipleSelection.value = val;
}
function deleteAll() {
  for (let item of multipleSelection.value) {
    deleteRow(item.id);
    confirm();
  }
  multipleSelection.value = [];
}
</script>

<style scoped>
</style>
