import { RouterName } from "./router-name";

export const NavName = {
  "Home": "首页",
  "SongSheet": "歌单",
  "Singer": "歌手",
  "Personal": "个人主页",
  "Setting": "设置",
  "LogIn": "登录",
  "SignUp": "注册",
  "SignOut": "退出",
  "ManageHome": "管理界面",
}

// 左侧导航栏
export const HEADERNAVLIST = [
  {
    name: NavName.Home,
    path: RouterName.Home,
  },
  {
    name: NavName.SongSheet,
    path: RouterName.SongSheet,
  },
  {
    name: NavName.Singer,
    path: RouterName.Singer,
  },
];

// 右侧导航栏
export const SIGNLIST = [
  {
    name: NavName.LogIn,
    path: RouterName.LogIn,
  },
  {
    name: NavName.SignUp,
    path: RouterName.SignUp,
  },
];

// 用户下拉菜单项
export const MENULIST = [
  {
    name: NavName.Personal,
    path: RouterName.Personal,
  },
  {
    name: NavName.Setting,
    path: RouterName.Setting,
  },
  {
    name: NavName.SignOut,
    path: RouterName.SignOut,
  },
];

// 管理员下拉菜单
export const ADMINLIST = [
  {
    name: NavName.ManageHome,
    path: RouterName.ManageHome,
  },
  {
    name: NavName.SignOut,
    path: RouterName.SignOut,
  }
]