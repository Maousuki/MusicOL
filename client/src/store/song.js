import { Icon } from "@/enums";

export default {
  state: {
    songId: "",
    songTitle: "",
    songUrl: "",
    songPic: `/img/songPic/tubiao.jpg`,
    singerName: "",
    lyric: [],

    isPlay: false,
    playBtnIcon: Icon.BOFANG,
    volume: 0,
    duration: 0,
    currentTime: 0,
    changeTime: 0,
    autoNext: true,

    currentPlayList: [],
    songDetails: null,
    currentPlayIndex: -1,
  },
  getters: {
    songId: (state) => state.songId,
    songTitle: (state) => state.songTitle,
    songUrl: (state) => state.songUrl,
    songPic: (state) => state.songPic,
    singerName: (state) => state.singerName,
    lyric: (state) => state.lyric,

    isPlay: (state) => state.isPlay,
    playBtnIcon: (state) => state.playBtnIcon,
    volume: (state) => state.volume,
    duration: (state) => state.duration,
    curTime: (state) => state.curTime,
    changeTime: (state) => state.changeTime,
    autoNext: (state) => state.autoNext,

    currentPlayList: (state) => state.currentPlayList,
    songDetails: (state) => state.songDetails,
    currentPlayIndex: (state) => state.currentPlayIndex,
  },
  mutations: {
    setSongId: (state, songId) => {
      state.songId = songId;
    },
    setSongTitle: (state, songTitle) => {
      state.songTitle = songTitle;
    },
    setSongUrl: (state, songUrl) => {
      state.songUrl = songUrl;
    },
    setSongPic: (state, songPic) => {
      state.songPic = songPic;
    },
    setSingerName: (state, singerName) => {
      state.singerName = singerName;
    },
    setAutoNext: (state, autoNext) => {
      state.autoNext = autoNext;
    },
    setLyric: (state, lyric) => {
      state.lyric = lyric;
    },

    setIsPlay: (state, isPlay) => {
      state.isPlay = isPlay;
    },
    setPlayBtnIcon: (state, playBtnIcon) => {
      state.playBtnIcon = playBtnIcon;
    },
    setVolume: (state, volume) => {
      state.volume = volume;
    },
    setDuration: (state, duration) => {
      state.duration = duration;
    },
    setCurTime: (state, curTime) => {
      state.curTime = curTime;
    },
    setChangeTime: (state, changeTime) => {
      state.changeTime = changeTime;
    },

    setCurrentPlayList: (state, currentPlayList) => {
      state.currentPlayList = currentPlayList;
    },
    setSongDetails: (state, songDetails) => {
      state.songDetails = songDetails;
    },
    setCurrentPlayIndex: (state, currentPlayIndex) => {
      state.currentPlayIndex = currentPlayIndex;
      return true
    },
  },
  actions: {
    playMusic: ({ commit }, { id, url, pic, index, songTitle, singerName, lyric, currentSongList }) => {
      commit("setSongId", id);
      commit("setSongUrl", url);
      commit("setSongPic", pic);
      commit("setCurrentPlayIndex", index);
      commit("setSongTitle", songTitle);
      commit("setSingerName", singerName);
      commit("setLyric", lyric);
      commit("setCurrentPlayList", currentSongList);
    },
  }
}