export default {
    state:{
        token: "",       // 登录状态
        showAside: false,   // 展示侧边栏
        searchWords: "",    // 搜索关键字
        activeNavName: "",  // 当前激活的导航栏名
    },
    getters: {
        token: (state) => state.token,
        activeNavName: (state) => state.activeNavName,
        showAside: (state) => state.showAside,
        searchWords: (state) => state.searchWords,
    },
    mutations:{
        setToken: (state, token) => {
            state.token = token;
        },
        setActiveNavName: (state, activeNavName) => {
            state.activeNavName = activeNavName;
        },
        setShowAside: (state, showAside) => {
            state.showAside = showAside;
        },
        setSearchWords: (state, searchWords) => {
            state.searchWords = searchWords;
        },
    }
}