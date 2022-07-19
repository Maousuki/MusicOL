// 用户名和密码的校验规则
export const usernameRule = [
  { required: true, message: "请输入用户名", trigger: "blur" },
  { min: 3, max: 16, message: "用户名长度应该为3~16个字符", trigger: "blur" },
]

export const passwordRule = [
  { required: true, message: "请输入密码", trigger: "blur" },
  { min: 8, max: 20, message: "密码长度应该为8~20个字符" },
]

// 登录规则
export const logInRules = {
  username: usernameRule,
  password: passwordRule,
};

// 注册规则
export const SignUpRules = {
  username: usernameRule,
  password: passwordRule,
  sex: [{ required: true, message: "请选择性别", trigger: "change" }],
  phoneNum: [
    { required: true, message: "请输入手机号", trigger: "blur" },
    { pattern: /^1(3[0-9]|4[01456879]|5[0-35-9]|6[2567]|7[0-8]|8[0-9]|9[0-35-9])\d{8}$/, message: "请输入正确的手机号", trigger: "blur" },
  ],
  email: [
    { required: true, message: "请输入邮箱地址", trigger: "blur" },
    {
      type: "email",
      message: "请输入正确的邮箱地址",
      trigger: ["blur", "change"],
    },
  ],
  birth: [
    { required: true, type: "date", message: "请选择日期", trigger: "change" },
  ],
  introduction: [
    { max: 128, message: "签名长度超过128", trigger: "blur"}
  ],
  location: [],
};

