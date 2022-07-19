<template>
  <el-breadcrumb class="crumbs" separator="/">
    <el-breadcrumb-item v-for="item in breadcrumbList" :key="item.name" :to="{ path: item.path, query: item.query }">
      {{ item.name }}
    </el-breadcrumb-item>
  </el-breadcrumb>

  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-input placeholder="筛选歌曲" v-model="searchWord"></el-input>
    </div>
    <el-table height="550px" border size="small" :data="tableData" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column prop="name" label="歌手-歌曲"></el-table-column>
      <el-table-column label="操作" width="90" align="center">
        <template v-slot="scope">
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

  <!-- 删除提示框 -->
  <del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event" />
  <del-dialog :delVisible="delVisibleAll" @confirm="confirmAll" @cancelRow="delVisibleAll = $event" />

</template>

<script setup>
import { watch, ref, computed } from "vue";
import { useStore } from "vuex";
import { useRoute } from "vue-router";
import { ElMessage } from "element-plus";
import { HttpManager } from "@/api";
import DelDialog from "@/components/manage/dialog/DelDialog.vue"

const store = useStore();
const route = useRoute()

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
      if (item.name.includes(searchWord.value)) {
        tableData.value.push(item);
      }
    }
  }
});

getData();

// 通过用户 ID 获取用户收藏的歌曲 ID
async function getData() {
  tableData.value = [];
  tempDate.value = [];
  const result = (await HttpManager.getCollectionOfUser(route.query.id, { headers: { 'token': store.getters.token } }));
  for (let item of result.data) {
    const result = await HttpManager.getSongOfId(item.songId);
    tableData.value.push(result.data);
    tempDate.value.push(result.data);
  }
}

/**
 * 删除
 */
const idx = ref(-1); // 记录当前要删除的行
const multipleSelection = ref([]); // 记录当前要删除的列表
const delVisible = ref(false); // 显示删除框
const delVisibleAll = ref(false); // 显示删除框

async function confirm() {
  const result = (await HttpManager.deleteCollection(route.query.id, idx.value, { headers: { 'token': store.getters.token } }));


  ElMessage({
    message: result.message,
    type: result.type,
  })

  if (result.message === "SUCCESS") getData();
  delVisible.value = false;
}

async function confirmAll() {
  try {
    for (let item of multipleSelection.value) {
      idx.value = item.id;
      const result = (await HttpManager.deleteCollection(route.query.id, idx.value, { headers: { 'token': store.getters.token } }));
      // confirm();
      multipleSelection.value = [];
    }
    getData();
    delVisibleAll.value = false;
    ElMessage.success('删除成功')
  } catch (e) {
    ElMessage.error('删除时发生错误:' + e)
  }
}

function deleteRow(id) {
  idx.value = id;
  delVisible.value = true;
}

function handleSelectionChange(val) {
  multipleSelection.value = val;
}

function deleteAll() {
  delVisibleAll.value = true;
}
</script>

<style scoped>
</style>
