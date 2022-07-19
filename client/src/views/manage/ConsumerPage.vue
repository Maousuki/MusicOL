<template>
  <div class="container">
    <div class="handle-box">
      <el-button @click="deleteAll">批量删除</el-button>
      <el-input v-model="searchWord" placeholder="筛选用户"></el-input>
    </div>

    <el-table height="550px" border size="small" :data="data" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="40" align="center"></el-table-column>
      <el-table-column label="ID" prop="id" width="50" align="center"></el-table-column>
      <el-table-column label="用户头像" width="102" align="center">
        <template v-slot="scope">
          <img :src="attachImageUrl(scope.row.avator)" style="width: 80px" />
        </template>
      </el-table-column>
      <el-table-column label="用户名" prop="username" width="80" align="center"></el-table-column>
      <el-table-column label="性别" width="50" align="center">
        <template v-slot="scope">
          <div>{{ changeSex(scope.row.sex) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="手机号码" prop="phoneNum" width="120" align="center"></el-table-column>
      <el-table-column label="邮箱" prop="email" width="120" align="center"></el-table-column>
      <el-table-column label="生日" width="120" align="center">
        <template v-slot="scope">
          <div>{{ getBirth(scope.row.birth) }}</div>
        </template>
      </el-table-column>
      <el-table-column label="签名" prop="introduction"></el-table-column>
      <el-table-column label="地区" prop="location" width="70" align="center"></el-table-column>
      <el-table-column label="收藏" width="90" align="center">
        <template v-slot="scope">
          <el-button @click="goCollectPage(scope.row.id)">收藏</el-button>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="90" align="center">
        <template v-slot="scope">
          <el-button type="danger" @click="deleteRow(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination class="pagination" background layout="total, prev, pager, next" :current-page="currentPage"
      :page-size="pageSize" :total="tableData.length" @current-change="handleCurrentChange">
    </el-pagination>
  </div>

  <!-- 删除提示框 -->
  <del-dialog :delVisible="delVisible" @confirm="confirm" @cancelRow="delVisible = $event" />
  <del-dialog :delVisible="delVisibleAll" @confirm="confirmAll" @cancelRow="delVisibleAll = $event" />
</template>
    
<script setup>
import { ref, watch, reactive, computed, onMounted } from 'vue'
import { HttpManager } from "@/api";
import { RouterName } from "@/enums";
import { useStore } from 'vuex';
import { ElMessage } from 'element-plus';
import utils from '@/utils'
import DelDialog from '@/components/manage/dialog/DelDialog.vue';

const store = useStore()

const tableData = ref([]); // 记录歌曲，用于显示
const tempDate = ref([]); // 记录歌曲，用于搜索时能临时记录一份歌曲列表
const pageSize = ref(10); // 页数
const currentPage = ref(1); // 当前页

const attachImageUrl = HttpManager.attachImageUrl
const { changeSex, routerManager, getBirth } = utils()

const data = computed(() => {
  return tableData.value.slice((currentPage.value - 1) * pageSize.value, currentPage.value * pageSize.value);
});

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

async function getData() {
  tableData.value = [];
  tempDate.value = [];
  const result = (await HttpManager.getAllUser({ headers: { 'token': store.getters.token } }));
  tableData.value = result.data;
  tempDate.value = result.data;
  currentPage.value = 1;
}

onMounted(getData)

function handleCurrentChange(value) {
  currentPage.value = value;

}

function goCollectPage(id) {
  const breadcrumbList = reactive([
    {
      path: RouterName.ManageConsumer,
      name: "用户管理",
    },
    {
      path: "",
      name: "收藏信息",
    },
  ]);

  store.commit("setBreadcrumbList", breadcrumbList);
  routerManager(RouterName.ManageCollect, { path: RouterName.ManageCollect, query: { id } });
}

const idx = ref(-1); // 记录当前要删除的行
const multipleSelection = ref([]); // 记录当前要删除的列表
const delVisible = ref(false); // 显示删除框
const delVisibleAll = ref(false); // 显示删除框


async function confirm() {
  const result = (await HttpManager.deleteUser(idx.value, { headers: { 'token': store.getters.token } }));

  ElMessage({
    message: result.message,
    type: result.type,
  })

  if (result.message === "SUCCESS") {
    getData();
    delVisible.value = false;
  }
}

async function confirmAll() {
  try {
    for (let item of multipleSelection.value) {
      idx.value = item.id;
      const result = (await HttpManager.deleteUser(idx.value, { headers: { 'token': store.getters.token } }));
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

function handleSelectionChange(value) {
  multipleSelection.value = value;
}

function deleteAll() {
    delVisibleAll.value = true;
}


</script>