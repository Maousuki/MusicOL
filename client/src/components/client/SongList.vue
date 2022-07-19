<template>
  <div class="content">
    <el-table highlight-current-row :data="dataList" @row-dblclick="handleClick">
      <el-table-column prop="songName" label="歌曲" />
      <el-table-column prop="singerName" label="歌手" />
      <el-table-column prop="introduction" label="专辑" />
      <el-table-column label="编辑" width="80" align="center">
        <template #default="scope">
          <el-dropdown>
            <el-icon @click="handleEdit(scope.row)">
              <MoreFilled />
            </el-icon>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item :icon="Download" @click="
                  downloadMusic({
                    songUrl: scope.row.url,
                    songName: scope.row.name,
                  })
                ">下载</el-dropdown-item>
                <!-- <el-dropdown-item :icon="Delete" v-if="show" @click="deleteCollection({ id: scope.row.id })">删除
                </el-dropdown-item> -->
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>
  </div>
</template>
    
<script setup>
import { toRefs, computed, reactive } from "vue";
import { useStore } from "vuex";
import utils from "@/utils";
import { MoreFilled, Delete, Download } from "@element-plus/icons-vue";
import { HttpManager } from "@/api";
import { Icon } from "@/enums";
import { ElMessage } from "element-plus";

const props = defineProps({
  songList: Array,
  show: {
    default: false
  }
})

const emits = defineEmits(['changeData'])

const store = useStore();

const { songList } = toRefs(props);
const { getSongTitle, getSingerName, playMusic, checkStatus, downloadMusic } = utils();

const iconList = reactive({
  dislike: Icon.Dislike,
  like: Icon.Like,
});

const songUrl = computed(() => store.getters.songUrl);
const singerName = computed(() => store.getters.singerName);
const songTitle = computed(() => store.getters.songTitle);
const dataList = computed(() => {
  const list = [];
  songList.value.forEach((item, index) => {
    item["songName"] = getSongTitle(item.name);
    item["singerName"] = getSingerName(item.name);
    item["index"] = index;
    list.push(item);
  });
  return list;
});

function handleClick(row) {
  // console.log(row)
  playMusic({
    id: row.id,
    url: row.url,
    pic: row.pic,
    index: row.index,
    name: row.name,
    lyric: row.lyric,
    currentSongList: songList.value,
  });
  store.commit('setIsPlay', true);
}

function handleEdit(row) {
  // console.log("row", row);
}

const userId = computed(() => store.getters.userId);

async function deleteCollection({ id }) {
  if (!checkStatus()) return;

  const result = (await HttpManager.deleteCollection(userId.value, id, { headers: { 'token': store.getters.token } }));
  ElMessage({
    message: result.message,
    type: result.type,
  })

  if (result.data === false) emits("changeData", result.data);
}


</script>