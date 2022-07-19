<template>
  <div class="comment">
    <h2 class="comment-title">
      <span>评论</span>
      <span class="comment-desc">共 {{ commentList.length }} 条评论</span>
    </h2>
    <el-input class="comment-input" type="textarea" maxlength="128" show-word-limit
      placeholder="期待您的精彩评论..." :rows="2" v-model="textarea" />
    <el-button class="sub-btn" type="primary" @click="submitComment()">发表评论</el-button>
  </div>
  <ul class="popular">
    <li v-for="(item, index) in commentList" :key="index" :class="{ liked: item.likeStatus }">
      <el-image class="popular-img" fit="contain" :src="attachImageUrl(item.avatar)" />
      <div class="popular-msg">
        <ul>
          <li class="name">{{ item.username }}</li>
          <li class="time">{{ formatDate(item.createTime) }}</li>
          <li class="content"><span>{{ item.content }}</span></li>
        </ul>
      </div>
      <!-- like -->
      <div ref="likeCount" class="comment-ctr" @click="setSupport(item, index)">
        <div class="like-btn">
          <my-logo :icon="iconList.Support" /> {{ item.likeCount }}
        </div>
        <el-icon class="icon" v-if="item.userId === userId" @click="deleteComment(item.id, index)">
          <delete />
        </el-icon>
      </div>
    </li>
  </ul>
</template>
    
<script setup>
import { ref, toRefs, computed, watch, reactive, onMounted } from "vue";
import { useStore } from "vuex";
import { Delete } from "@element-plus/icons-vue";
import MyLogo from "./layouts/MyLogo.vue";
import utils from "@/utils";
import { HttpManager } from "@/api";
import { Icon, RESPONSE_CODE } from "@/enums";
import { ElMessage } from "element-plus";

const props = defineProps({
  playId: Number || String, // 歌曲ID或歌单ID
  type: Number, // 歌单（1）/歌曲（0）
})

const store = useStore();
const { checkStatus, formatDate } = utils();
const attachImageUrl = HttpManager.attachImageUrl

const { playId, type } = toRefs(props);
const commentList = ref([]); // 存放评论内容
const textarea = ref(""); // 存放输入内容
const iconList = reactive({
  Support: Icon.Support,
});
const userId = computed(() => store.getters.userId);
const songId = computed(() => store.getters.songId);
const token = computed(() => store.getters.token);

watch(songId, () => {
  getComment();
});

// 获取所有评论
async function getComment() {
  try {
    const result = (await HttpManager.getAllComment(type.value, playId.value));
    commentList.value = result.data;
    for (let index = 0; index < commentList.value.length; index++) {
      // 获取评论用户的昵称和头像
      const resultUser = (await HttpManager.getUserOfId(commentList.value[index].userId));

      commentList.value[index].avatar = resultUser.data.avatar;
      commentList.value[index].username = resultUser.data.username;
      commentList.value[index].likeStatus = await getLikeStatus(commentList.value[index])
    }
  } catch (error) {
    console.error(error);
  }
  // console.log(commentList.value);
}


async function getLikeStatus(item) {
  // id userid type
  const data = {
    id: item.id,
    userId: store.getters.userId,
    type: item.type,
  }
  const result = await HttpManager.getLikeStatus(data, { headers: { token: store.getters.token } })
  if (result.code === RESPONSE_CODE.SUCCESS) {
    if (result.data === 1)
      return true
    else if (result.data === 0) {
      return false
    } else {
      ElMessage.error(result.message)
    }
  } else {
    throw new Error(result.message)
  }
  return false
}

// 提交评论
async function submitComment() {
  if (!checkStatus()) return;

  let data = {
    songListId: '',
    userId: '',
    type:'',
    content:'',
  };

  if (type.value === 1) {
    data = {
      songListId: `${playId.value}`,
      userId: userId.value,
      type: `${type.value}`,
      content: textarea.value
    }
  } else if (type.value === 0) {
    data = {
      songId: `${playId.value}`,
      userId: userId.value,
      type: `${type.value}`,
      content: textarea.value
    }
  }else{

  }

  if(data.content.length < 1){
    ElMessage.error("评论长度过短!")
    return
  }


  const result = (await HttpManager.setComment(JSON.stringify(data), { headers: { 'token': store.getters.token } }));

  ElMessage({
    message: result.message,
    type: result.type,
  })

  if (result.message === "SUCCESS") {
    textarea.value = "";
    await getComment();
  }
}

// 删除评论
async function deleteComment(id, index) {
  const result = (await HttpManager.deleteComment(id, { headers: { 'token': store.getters.token } }));
  ElMessage({
    message: result.message,
    type: result.type,
  })
  if (result.message === "SUCCESS") commentList.value.splice(index, 1);
}

// 点赞
async function setSupport(item, index) {
  if (!checkStatus()) return;

  const data = {
    id: item.id,
    userId: store.getters.userId,
    type: type.value
  };

  const result = (await HttpManager.setSupport(JSON.stringify(data), { headers: { 'token': store.getters.token } }));
  if (result.code === RESPONSE_CODE.SUCCESS) {
    // TODO: what the fuck is this
    item.likeCount = result.data.likeCount
    commentList.value[index].likeStatus = result.data.likeStatus
    // console.log(commentList.value)
    // proxy.$refs.up[index].children[0].style.color = "#2796dd";
    // await getComment();
  } else if (result.code === 0) {
    ElMessage.warning('请先登录!')
  }
}

onMounted(() => {
  getComment();
});
</script>

<style lang="scss" scoped>
@import "@/assets/css/var.scss";
@import "@/assets/css/global.scss";

/*评论*/
.comment {
  position: relative;
  margin-bottom: 60px;

  .comment-title {
    height: 50px;
    line-height: 50px;

    .comment-desc {
      font-size: 14px;
      font-weight: 400;
      color: $color-grey;
      margin-left: 10px;
    }
  }

  .comment-input {
    display: flex;
    margin-bottom: 20px;
  }

  .sub-btn {
    position: absolute;
    right: 0;
  }
}

/*热门评论*/
.popular {
  width: 100%;

  >li {
    border-bottom: solid 1px rgba(0, 0, 0, 0.1);
    padding: 15px 0;
    display: flex;

    .popular-img {
      width: 50px;
    }

    .popular-msg {
      padding: 0 20px;
      flex: 1;

      li {
        width: 100%;
      }

      .time {
        font-size: 0.6rem;
        color: rgba(0, 0, 0, 0.5);
      }

      .name {
        color: rgba(0, 0, 0, 0.5);
      }

      .content {
        font-size: 1rem;
        word-break: break-all; 

      }
    }

    .comment-ctr {
      display: flex;
      align-items: center;
      width: 80px;
      font-size: 1rem;
      cursor: pointer;

      .el-icon {
        margin: 0 10px;
      }

      :deep(.icon):hover {
        color: $color-grey;
      }
    }
  }
}

.icon {
  @include icon(1em);
}

.liked .like-btn {

  .icon,
  #text {
    color: #FF0000
  }
}
</style>