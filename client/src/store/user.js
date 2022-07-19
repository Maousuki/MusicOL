export default {
    state: {
        userId: "",
        username: "",
        userPic: "",
        userRole: 0,            // 0为普通用户，1为管理员
    },
    getters: {
        userId: (state) => state.userId,
        username: (state) => state.username,
        userPic: (state) => state.userPic,
        userRole: (state) => state.userRole,
    },
    mutations: {
        setUserId: (state, userId) => {
            state.userId = userId;
        },
        setUsername: (state, username) => {
            state.username = username;
        },
        setUserPic: (state, userPic) => {
            state.userPic = userPic;
        },
        setUserRole:(state, role) => {
            state.userRole = role;
        }
    }
}