# Dev - 持续更新中

2025.03.30 新增邮件找回密码的功能-先调用接口获取验证码，然后请求另外一个接口更新新密码。  
2025.03.29 增加Task，引入MongoDB，引入MQ，完善雪花算法，MongoDB和MQ暂时未封装。MongoDB 和MQ通过 - Docker拉取部署 -
框架基本形成。  
2025.04.15 完善任务创建的代码，目前兼容信息抽取模块，优化代码为后续兼容其他化合成的任务创建代码。用户权限管理角色管理TODO  
2025.04.20 更新ReadMe，RocketMQ不支持在Windows环境下的本地测试，调研解决方案。
2025.05.19 学习实现文件的秒传、分片上传、断点续传功能。不使用阿里云OSS，直接在本地模拟。

# 化学文献多模态信息抽取平台-Java侧

该项目为化学文献多模态信息抽取平台-Java侧，包含以下功能:

1. 实现用户的注册、登录、修改密码、找回密码等功能
2. 实现多模态抽取中任务创建，任务管理，任务查询，任务删除等功能
3. Java侧与Python端功能通过消息队列进行交互

## 项目开发简介

1. 基于JDK17+SprigBoot3进行开发，支持SpringCloud进行路由动态转发
2. 设计技术：SpringBoot、SpringCloud、MySQL、Redis、RabbitMQ、Nginx

## SQL资源

位于 /source/sql

## 整体框架

<img src="./source/image/img.png" width="800" height="400" alt="整体框架">

## RocketMQ Topic and tap management

| Category | Topic           | Tag      | Description             |
|----------|-----------------|----------|-------------------------|
| 多模态信息抽取  | task-service_IE | text     | 文本模态反应参数结构化提取           |
| 多模态信息抽取  | task-service_IE | smiles   | 分子结构图片识别                |
| 多模态信息抽取  | task-service_IE | reaction | 反应方程式识别                 |
| 多模态信息抽取  | task-service_IE | reaxys   | ReaxysPDF文献端到端结构化反应数据提取 |

## 注意

### 用户名全剧唯一（为了支持用户名密码登录，防止重名登录错账号）

实现思路：注册的时候先利用布隆过滤器判断用户名是否已经存在。  
如果不存在，说明未注册，则正常注册。  
如果存在，但是可能存在误判，所以进一步检查RedisSet里面是否是真的存在这个元素，如果不存在，正常注册。  
否则，跑出异常，当前用户已经注册。  
并且在正常注册时，防止多个用户同时注册一个用户名，使用Redisson添加分布式锁防止资源竞争。

<img src="./source/image/username.png" width="500" height="400" alt="用户注册流程">

### 文件的秒传、断点续传、分片上传实现逻辑 - 实际使用的时候应该还需要有一个表用来记录用户已经上传的资源在哪里，方便寻找已经上传的资源。

- Redis记录的key: chunk-upload:uploaded:[slot]:username:filename:fileKey(fileMD5) --> value: ossSavePath(
  文件的保存路径)  
  slot需要计算，利用 filename.hash % 16 = filename.hash & 15 提升计算速度。

- 秒传：前端提供原始文件名、分片文件MD5
  根据参数构造Redis key，检查Redis key是否存在实际值，存在说明已经长传过了，直接返回，无需调用上传接口。
- 分片上传：前端提供当前分片文件资源、当前分片文件对应的MD5，总分片数量，当前分片序号，原始文件名  
  保存分片文件到本地
- 断点续传：前端提供当前分片文件资源、当前分片文件对应的MD5，总分片数量，当前分片序号，原始文件名，返回缺失的文件片序号,


