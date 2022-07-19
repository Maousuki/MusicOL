# MusicOL
在线音乐播放网站

参照[Yin-Hongwei (尹宏伟) (github.com)](https://github.com/Yin-Hongwei)相同项目的一个课程作业，并对其进行改写优化



## 技术栈

前端：Vue + Vuex + ElementPlus + Vue-Router + Axios

后端：SpringBoot + MySQL8.0 +  MyBatisPus + Redis + Shiro + RabbitMQ



## 项目参考

[Yin-Hongwei/music-website: 🎧 Vue + SpringBoot + MyBatis 音乐网站 (github.com)](https://github.com/Yin-Hongwei/music-website)



## 改进地方

前端：

- 将所有ts代码改成js

- 整体UI风格不变，将用户端和管理端合并为一套代码逻辑，当登录用户为管理员时，可从用户端跳转到管理端

![image-20220719233346194](https://krypton-picbed.oss-cn-chengdu.aliyuncs.com/img/202207192334228.png)

- 修复原项目管理端批量删除无法点击确认的Bug
- 修复原项目每次刷新页面自动播放歌曲的Bug
- 将单击播放音乐改为双击
- 对点赞逻辑进行重写，一位用户只能点赞一次，再次点击会取消点赞



后端：

- 后端整体代码重写，基本功能不变
- 使用Token令牌方式登录，token统一存储在Redis中
- 使用Shiro进行角色权限验证，根据User属性字段role来为角色赋予接口访问权限

![image-20220719235058100](https://krypton-picbed.oss-cn-chengdu.aliyuncs.com/img/202207192351480.png)

​		参考 [一看就懂！Springboot +Shiro +VUE 前后端分离式权限管理系统_大誌的博客-CSDN博客](https://blog.csdn.net/weixin_42236404/article/details/89319359)

- 考虑到热评点赞属于高并发场景，用Redis存储用户点赞状态和点赞数量，确保数据更新持久化，将数据库更新操作封装成消息发送给消息队列异步处理，点赞作为非重要数据，即使存在消息丢失也没关系
- 删除歌单打分功能
- 评论可以添加emoji表情