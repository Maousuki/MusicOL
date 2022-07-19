import { getBaseURL, get, post, deletes } from "./request";

const HttpManager = {
  // 获取图片信息
  attachImageUrl: (url) => url ? `${getBaseURL()}${url}`: "https://krypton-picbed.oss-cn-chengdu.aliyuncs.com/img/202206231344671.jpg" ,
  // =======================> 用户 API
  // 登录
  logIn: (params) => post(`login`, params),
  // 注册
  signUp: (params) => post(`register`, params),
  logOut: (token) => post(`user/logout`, {}, token),
  // 删除用户
  deleteUser: (id,token) => deletes(`user/delete?id=${id}`,token),
  // 更新用户信息
  updateUserMsg: (params, token) => post(`user/updateInfo`, params, token),
  updateUserPassword: (params, token) => post(`user/updatePwd`, params, token),
  // 返回指定ID的用户
  getUserOfId: (id) => get(`userDetail?id=${id}`),
  // 更新用户头像
  uploadUrl: (userId) => `${getBaseURL()}/user/avatar?id=${userId}`,

  // =======================> 歌单 API
  // 获取全部歌单
  getSongList: () => get("allSongList"),
  // 返回所给类型的歌单
  getSongListOfStyle: (style) => get(`songListByStyle?style=${style}`),
  // 返回标题包含文字的歌单
  getSongListOfLikeTitle: (keywords) => get(`songListByTitle?title=${keywords}`),
  // 返回歌单里指定歌单ID的歌曲
  getListSongOfSongId: (songListId) => get(`getBySongListId?songListId=${songListId}`),

  // =======================> 歌手 API
  // 返回所有歌手
  getAllSinger: () => get("allSinger"),
  // 通过性别对歌手分类
  getSingerOfSex: (sex) => get(`singerBySex?sex=${sex}`),

  // =======================> 收藏 API
  // 返回的指定用户ID的收藏列表
  getCollectionOfUser: (userId,token) => get(`collect/songList?userId=${userId}`,token),
  // 添加收藏的歌曲 type: 0 代表歌曲， 1 代表歌单
  setCollection: (params,token) => post(`collect/add`, params, token),

  deleteCollection: (userId, songId,token) => deletes(`collect/delete?userId=${userId}&&songId=${songId}`,token),

  isCollection: (params,token) => post(`collect/songStatus`, params,token),

  // =======================> 评论 API
  // 添加评论
  setComment: (params,token) => post(`comment/add`, params,token),
  // 删除评论
  deleteComment: (id,token) => deletes(`comment/delete?id=${id}`,token),
  // 点赞
  setSupport: (data, token) => post(`comment/like`, data, token),
  // 返回所有评论
  getAllComment: (type, id) => {
    let url = "";
    if (type === 1) {
      url = `commentBySongListId?songListId=${id}`;
    } else if (type === 0) {
      url = `commentBySongId?songId=${id}`;
    }
    return get(url);
  },
  getLikeStatus: (data, token) => post(`comment/likeStatus`, data, token),

  // =======================> 歌曲 API
  // 返回指定歌曲ID的歌曲
  getSongOfId: (id) => get(`songById?id=${id}`),
  // 返回指定歌手ID的歌曲
  getSongOfSingerId: (id) => get(`songBySingerId?singerId=${id}`),
  // 返回指定的歌曲
  // getSongOfSingerName: (keywords) => get(`songBySongName?name=${keywords}`),
  // 下载音乐
  downloadMusic: (url) => get(url, { responseType: "blob" }),


  // =====================================
  // =======================> 管理API



  // 返回所有用户
  // =======================> 用户 API
  // 返回所有用户
  getAllUser: (token) => get(`user/allUser`,token),
  // 返回指定ID的用户
  getUserOfId: (id) => get(`userDetail?id=${id}`),
  // 添加用户
  setUser: (params) => post(`user/add`, params),
  // 更新用户信息
  // updateUserMsg: (data, token) => post(`user/updateInfo`, data, token),
  // 删除用户
  deleteUser: (id,token) => deletes(`user/delete?id=${id}`,token),

  // =======================> 收藏列表 API
  // 返回的指定用户ID收藏列表
  getCollectionOfUser: (userId,token) => get(`collect/songList?userId=${userId}`,token),
  // 删除收藏的歌曲
  deleteCollection: (userId, songId,token) => deletes(`collect/delete?userId=${userId}&&songId=${songId}`,token),

  // =======================> 评论列表 API
  // 获得指定歌曲ID的评论列表
  getCommentOfSongId: (songId) => get(`commentBySongId?songId=${songId}`),
  // 获得指定歌单ID的评论列表
  getCommentOfSongListId: (songListId) => get(`commentBySongListId?songListId=${songListId}`),
  // 删除评论
  deleteComment: (id, token) => deletes(`comment/delete?id=${id}`,token),

  // =======================> 歌手 API
  // 返回所有歌手
  getAllSinger: () => get(`allSinger`),
  // 添加歌手
  setSinger: (params,token) => post(`singer/add`, params,token),
  // 更新歌手信息
  updateSingerMsg: (params, token) => post(`singer/update`, params, token),
  // 删除歌手
  deleteSinger: (id,token) => deletes(`singer/delete?id=${id}`,token),

  // =======================> 歌曲 API
  // 返回所有歌曲
  getAllSong: () => get(`allSong`),
  // 返回指定歌手ID的歌曲
  getSongOfSingerId: (id) => get(`songBySingerId?singerId=${id}`),
  // 返回的指定用户ID收藏列表
  getSongOfId: (id) => get(`songById?id=${id}`),
  // 返回指定歌名的歌曲
  getSongOfSongName: (id) => get(`songBySongName?name=${id}`),
  // 更新歌曲信息
  updateSongMsg: (params, token) => post(`song/updateInfo`, params, token),
  updateSongUrl: (id) => `${getBaseURL()}/song/updateUrl?id=${id}`,
  updateSongImg: (id) => `${getBaseURL()}/song/updatePic?id=${id}`,
  // 删除歌曲
  deleteSong: (id, token) => deletes(`song/delete?id=${id}`, token),

  // =======================> 歌单 API
  // 添加歌单
  setSongList: (params,token) => post(`songList/add`, params, token),
  // 获取全部歌单
  getSongList: () => get(`allSongList`),
  // 更新歌单信息
  updateSongListMsg: (params,token) => post(`songList/updateInfo`, params, token),
  // 删除歌单
  deleteSongList: (id,token) => deletes(`songList/delete?id=${id}`,token),

  // =======================> 歌单歌曲 API
  // 给歌单添加歌曲
  setListSong: (params, token) => post(`listSong/add`, params, token),
  // 返回歌单里指定歌单ID的歌曲
  getListSongOfSongId: (songListId) => get(`getBySongListId?songListId=${songListId}`),
  // 删除歌单里的歌曲
  deleteListSong: (songId, songListId, token) => deletes(`listSong/delete?songId=${songId}&songListId=${songListId}`, token)


};

export { HttpManager };
