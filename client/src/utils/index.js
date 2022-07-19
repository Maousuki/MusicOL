import { ref, computed } from "vue";
import { NavName, RouterName, Icon } from "@/enums";
import { Store, useStore } from "vuex";
import { useRouter } from "vue-router";
import { ElMessage } from "element-plus";
import { HttpManager } from "@/api";

export default () => {
  const store = useStore();
  const router = useRouter();
  const token = computed(() => store.getters.token)

  // 限制的图像上传类型
  const uploadTypes = ref(["jpg", "jpeg", "png", "gif"]);

  /**
   * 客户端：设置activeNavName
   * @param {string} name 
   */
  function changeActiveName(name) {
    store.commit("setActiveNavName", name);
  }

  /**
   * 路由管理，用于跳转页面。（有设计遗留问题）
   * @param {string} routerName 路径名
   * @param {object} options {path: string, [query: string]} 参数
   */
  function routerManager(routerName, options) {
    if (options.query) {
      router.push({ path: options.path, query: options.query });
    } else {
      router.push({ path: options.path });
    }
  }
  /**
   * 格式化生日
   * @param {string} value 
   * @returns yyyy-mm-dd格式的日期字符串
   */
  function getBirth(value) {
    if (value == null || value == "") return "";
    const date = new Date(value);
    const year = date.getFullYear();
    const month =
      date.getMonth() + 1 < 10
        ? "0" + (date.getMonth() + 1)
        : date.getMonth() + 1;
    const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    return year + "-" + month + "-" + day;
  }



  /**
   * 完整的登出流程
   */
  async function logOut() {
    if (store.getters.token !== '') {
      const result = (await HttpManager.logOut({ headers: { token: store.getters.token } }))
      if (result.code === 200) {
        ElMessage({
          message: "退出成功",
          type: "success",
        });
        resetUserState()
      } else if (result.code === 1) {
        ElMessage({
          message: "错误:" + result.message + ".",
          type: "error",
        })
        resetUserState()
      }
      else {
        ElMessage({
          message: "退出失败:" + result.message,
          type: 'error',
        })
      }
    } else {
      ElMessage({
        message: "错误:token为空,已重置状态",
        type: 'error',
      })
      resetUserState()
    }
  }

  /**
   * 重置store中与用户有关的相关状态，并跳转到客户端首页
   */
  function resetUserState() {
    store.commit("setUsername", "");
    store.commit("setUserPic", "");
    store.commit("setUserId", "");
    store.commit("setUserRole", 0);
    store.commit("setToken", "");

    changeActiveName(NavName.Home);
    routerManager(RouterName.Home, { path: RouterName.Home });
  }

  /**
   * 将number类型的性别值改为字符串表示
   * @param {number} value 性别值
   * @returns value对应的性别字符串
   */
  function changeSex(value) {
    if (value === 0) {
      return "女";
    } else if (value === 1) {
      return "男";
    } else if (value === 2) {
      return "组合";
    } else if (value === 3) {
      return "不明";
    } else if (value === "男" || value === "女") {
      return value;
    } else {
      return "error"
    }
  }

  /**
   * 在图像文件上传前进行的检查，检查文件类型与文件大小。
   * @param {*} file 
   * @returns 一个布尔值，表示检查是否通过
   */
  function beforeImgUpload(file) {
    const ltCode = 2;
    const isLt2M = file.size / 1024 / 1024 < ltCode;
    const isExistFileType = uploadTypes.value.includes(file.type.replace(/image\//, ""));

    if (!isExistFileType) {
      ElMessage({
        message: `图片只支持 ${uploadTypes.value.join("、")} 格式!`,
        type: 'error'
      })
    }
    if (!isLt2M) {
      ElMessage({
        message: `上传头像图片大小不能超过 ${ltCode}MB!`,
        type: 'error'
      })
    }

    return isExistFileType && isLt2M;
  }

  /**
   * 下载歌曲的行为
   * @param {{songUrl: string, songName: string}}} 包含歌曲url和歌曲名的对象
   * @returns 
   */
  async function downloadMusic({ songUrl, songName }) {
    if (!songUrl) {
      ElMessage({
        message: "下载链接为空！",
        type: "error",
      })
      console.error("下载链接为空！");
      return;
    }

    const result = (await HttpManager.downloadMusic(songUrl));
    const eleLink = document.createElement("a");
    eleLink.download = `${songName}.mp3`;
    eleLink.style.display = "none";
    // 字符内容转变成 blob 地址
    // const blob = new Blob([result.data]);
    eleLink.href = URL.createObjectURL(result);
    document.body.appendChild(eleLink); // 触发点击
    eleLink.click();
    document.body.removeChild(eleLink); // 移除
  }

  /**
   * 播放歌曲，通过更新store中的状态来触发watch监听器实现歌曲播放。
   * @param {{ id:number, url:string, pic:string, index:number, name:string, lyric:Array, currentSongList:Array }} 包含歌曲信息的对象
   */
  async function playMusic({ id, url, pic, index, name, lyric, currentSongList }) {
    const songTitle = getSongTitle(name);
    const singerName = getSingerName(name);
    store.dispatch("playMusic", {
      id,
      url,
      pic,
      index,
      songTitle,
      singerName,
      lyric,
      currentSongList,
    });
  }

  /**
   * 检查登录状态
   * @param {boolean} message 是否显示提示信息
   * @return {boolean} 是否已登录
   */
  function checkStatus(message = true) {
    if (token.value === "") {
      // console.log(token)
      if (message === true) {
        ElMessage({
          message: "请先登录",
          type: "warning",
        })
      }
      return false
    }
    return true

  }

  /**
   * 通过格式化的文件名（歌手名-歌曲名）获取歌曲名
   * @param {string} str 格式为(歌手名-歌曲名)的文件名
   * @returns 歌曲名
   */
  function getSongTitle(str) {
    return str.split("-")[1];
  }

  /**
   * 通过格式化的文件名（歌手名-歌曲名）获取歌手名
   * @param {*} str 格式为(歌手名-歌曲名)的文件名
   * @returns 歌手名
   */
  function getSingerName(str) {
    return str.split("-")[0];
  }

  /**
   * 通过数值的sex获取字符串的sex，0为女，1为男
   * @param {number} sex 数值的sex
   * @returns “男”或“女”
   */
  function getUserSex(sex) {
    if (sex === 0) {
      return "女";
    } else if (sex === 1) {
      return "男";
    }
  }

  /**
   * 控制路由进行页面返回，也可以传参step进行前进或后退多步
   * @param {number} step 步数，整数，缺省值为-1，默认后退一步
   */
  function goBack(step = -1) {
    router.go(step);
  }

  /**
   * 格式化日期
   * @param {*} cellValue 
   * @returns yyyy-mm-dd格式的日期
   */
  function formatDate(cellValue) {
    if (cellValue == null || cellValue == "") return "";
    const date = new Date(cellValue);
    const year = date.getFullYear();
    const month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
    const day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
    const hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
    const minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
    const seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
    return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
  }

  /**
   * 将歌词文本处理为可用的列表
   * @param {string} text 要处理的歌词文本
   * @returns 歌词列表
   */
  function parseLyric(text) {
    let lines = text.split("\n");
    const pattern = /\[\d{2}:\d{2}.(\d{3}|\d{2})\]/g;
    const result = [];

    // 对于歌词格式不对的特殊处理
    if (!/\[.+\]/.test(text)) {
      return [[0, text]];
    }

    while (!pattern.test(lines[0])) {
      lines = lines.slice(1);
    }

    lines[lines.length - 1].length === 0 && lines.pop();
    for (const item of lines) {
      const time = item.match(pattern); // 存前面的时间段
      const value = item.replace(pattern, ""); // 存歌词
      for (const item1 of time) {
        const t = item1.slice(1, -1).split(":");
        if (value !== "") {
          result.push([parseInt(t[0], 10) * 60 + parseFloat(t[1]), value]);
        }
      }
    }
    result.sort((a, b) => a[0] - b[0]);
    return result;
  }

  /**
   * 将秒数处理为 ”时：分：秒“ 的格式
   * @param {string} value 秒
   * @returns ”时：分：秒“格式的字符串
   */
  function formatSeconds(value) {
    let theTime = parseInt(value);
    let theTime1 = 0;
    let theTime2 = 0;
    if (theTime > 60) {
      theTime1 = parseInt((theTime / 60).toString()); // 分
      theTime = parseInt((theTime % 60).toString()); // 秒
      // 是否超过一个小时
      if (theTime1 > 60) {
        theTime2 = parseInt((theTime1 / 60).toString()); // 小时
        theTime1 = 60; // 分
      }
    }
    // 多少秒
    let result = "";
    if (parseInt(theTime.toString()) < 10) {
      result = "0:0" + parseInt(theTime.toString());
    } else {
      result = "0:" + parseInt(theTime.toString());
    }
    // 多少分钟时
    if (theTime1 > 0) {
      if (parseInt(theTime.toString()) < 10) {
        result = "0" + parseInt(theTime.toString());
      } else {
        result = parseInt(theTime.toString()).toString();
      }
      result = parseInt(theTime1.toString()) + ":" + result;
    }
    // 多少小时时
    if (theTime2 > 0) {
      if (parseInt(theTime.toString()) < 10) {
        result = "0" + parseInt(theTime.toString());
      } else {
        result = parseInt(theTime.toString()).toString();
      }
      result = parseInt(theTime2.toString()) + ":" + parseInt(theTime1.toString()) + ":" + result;
    }
    return result;
  }

  /**
   * 重置歌曲状态
   */
  function resetSongState() {
    store.commit('setIsPlay', false);
    store.commit('setCurrentPlayIndex', -1)
    store.commit('setPlayBtnIcon', Icon.BOFANG)
    store.dispatch("playMusic", {
      id: '',
      url: '',
      pic: '',
      index: -1,
      songTitle: '',
      singerName: '',
      lyric: [],
      currentSongList: [],
    });
  }

  return {
    changeActiveName,
    routerManager,
    getBirth,
    logOut,
    changeSex,
    beforeImgUpload,
    playMusic,
    getSongTitle,
    getSingerName,
    goBack,
    downloadMusic,
    getUserSex,
    formatDate,
    parseLyric,
    formatSeconds,
    checkStatus,
    resetSongState,
  };
};
