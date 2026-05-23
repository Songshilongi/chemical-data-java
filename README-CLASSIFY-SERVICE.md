# 化学反应分类预测服务（Classify Service）

> 本文档面向：项目接手者 / 部署负责人  
> 维护者：HanPingKun（hpk 分支）  
> 最后更新：2026-05-21

---

## 1. 项目概述

本项目是 `chemical-data-java` 多模块工程下的一个微服务，独立提供化学反应分类预测的能力。

**只需要启动 classify-service 一个微服务**，其他兄弟微服务（task-service、user-service、gateway-service、compound-service）当前演示场景下不需要启动。 

### 系统架构

```
[Vue 前端]
   ↓ HTTP
[Java classify-service :9600]   ← 本仓库
   ↓ HTTP（同步预测）/ RocketMQ（异步批量）
[Python 算法服务 :8000]   ← 独立仓库 https://github.com/HanPingKun/chemical-classify-python
   ↓
[阿里云 OSS]（文件存储）
   +
[MySQL]（数据库）
[Redis]（幂等去重 / 缓存）
[RocketMQ]（异步任务通信）
```

---

## 2. 技术栈

- **JDK**：17
- **Spring Boot**：3.0.7
- **MySQL**：8.0+
- **Redis**：6.0+
- **RocketMQ**：5.x
- **构建工具**：Maven
- **数据库 ORM**：MyBatis-Plus
- **对象存储**：阿里云 OSS

---

## 3. 启动前准备

### 3.1 基础设施（必须）

| 组件       | 用途                                     | 配置位置                                   |
| ---------- | ---------------------------------------- | ------------------------------------------ |
| MySQL 8.0+ | 业务数据存储                             | `application.yaml` 的 `spring.datasource`  |
| Redis      | 接口幂等防重复提交（@NoDuplicateSubmit） | `application.yaml` 的 `spring.data.redis`  |
| RocketMQ   | Java 与 Python 异步通信（批量预测）      | 通过环境变量 `ROCKETMQ_NAME_SRV_ADDR` 配置 |
| 阿里云 OSS | 模型文件、Excel 文件存储                 | 通过环境变量配置（见 3.3）                 |

### 3.2 数据库初始化

```bash
# 1. 创建数据库（注意字符集是 utf8mb4）
mysql -u root -p -e "CREATE DATABASE chemical DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;"

# 2. 导入表结构
mysql -u root -p chemical < docs/sql/chemical_schema.sql
```

预期会创建 4 张表：

- `t_classify_single`：分类单条预测记录
- `t_classify_batch`：分类批量预测任务
- `t_yield_single`：产率单条预测记录
- `t_yield_batch`：产率批量预测任务

### 3.3 必须配置的环境变量

部署前在系统/IDE 中配置以下环境变量：

| 变量名                   | 说明                     | 示例                           |
| ------------------------ | ------------------------ | ------------------------------ |
| `OSS_ENDPOINT`           | OSS 接入域名             | `oss-cn-shanghai.aliyuncs.com` |
| `OSS_ACCESS_KEY_ID`      | 阿里云 AccessKey ID      | （联系运维获取）               |
| `OSS_ACCESS_KEY_SECRET`  | 阿里云 AccessKey Secret  | （联系运维获取）               |
| `OSS_BUCKET_NAME`        | OSS Bucket 名称          | `predictmodel`                 |
| `ROCKETMQ_NAME_SRV_ADDR` | RocketMQ NameServer 地址 | `127.0.0.1:9876`               |

> **强烈建议**：用阿里云 RAM 子账号生成 AK/SK，仅授权 OSS 操作权限，不要用主账号 AK。

#### IDEA 配置环境变量

1. 启动按钮旁边下拉 → `Edit Configurations...`
2. 选中 `ClassifyServiceApplication`
3. 找到 `Modify options` → 勾选 `Environment variables`
4. 在 Environment variables 里逐行添加上面 5 个变量

#### 服务器部署时配置环境变量

```bash
# 编辑 ~/.bashrc 或 /etc/environment
export OSS_ENDPOINT=oss-cn-shanghai.aliyuncs.com
export OSS_ACCESS_KEY_ID=xxx
export OSS_ACCESS_KEY_SECRET=xxx
export OSS_BUCKET_NAME=predictmodel
export ROCKETMQ_NAME_SRV_ADDR=127.0.0.1:9876
```

### 3.4 修改 application.yaml

打开 `service/classify-service/src/main/resources/application.yaml`，根据实际环境修改：

- `spring.datasource.url`：替换为你的 MySQL 地址
- `spring.datasource.username` / `password`：替换为你的 MySQL 账号
- `spring.data.redis.host` / `port`：替换为你的 Redis 地址
- `python-service.url`：替换为 Python 服务地址，比如 `http://localhost:8000`

---

## 4. 启动服务

### 4.1 用 IDEA 启动（建议）

1. 用 IDEA 打开项目根目录 `chemical-data-java`
2. 等 Maven 加载完所有依赖
3. 找到 `service/classify-service/src/main/java/com/songshilong/service/classify/ClassifyServiceApplication.java`
4. 右键 → Run 'ClassifyServiceApplication'
5. 启动成功后访问 http://localhost:9600/doc.html 查看 API 文档

### 4.2 服务器部署（Linux）

**前置环境要求**：
1. 安装 JDK 17
2. 安装 Maven 3.6+
3. 确保服务器能连通 MySQL、Redis、RocketMQ

**编译与启动**：
```bash
# 1. 进入项目根目录
cd chemical-data-java

# 2. 编译打包（跳过测试）
mvn clean package -DskipTests

# 3. 找到生成的 jar 包
cd service/classify-service/target/

# 4. 后台启动服务（指定端口 9600）
nohup java -jar classify-service-1.0-SNAPSHOT.jar > classify.log 2>&1 &

# 5. 查看启动日志
tail -f classify.log
```

---

## 5. 与 Python 算法服务联调

Python 算法服务在另一个仓库：  
👉 https://github.com/HanPingKun/chemical-classify-python

启动顺序建议：

1. MySQL / Redis / RocketMQ
2. Python 算法服务（端口 8000）
3. Java classify-service（端口 9600）
4. Vue 前端（端口 12000）

---

## 6. 与前端联调

前端仓库：  
👉 https://github.com/HanPingKun/chemical/tree/hpk

前端默认调用本服务的 API 路径前缀为 `/api/classify-task/`。

---

## 7. 与登录功能对接（未来）

当前用户身份信息通过 HTTP header 的 `userId / username / email / phone` 字段传入，由 `TaskHeaderInterceptor` 自动读取到 `BaseContext`。

集成真实登录功能时，有三种方案：

- **方案 A（推荐）**：在 starter-common 写全局 JWT 拦截器，解析 token 后将 userId 塞到 request header
- **方案 B**：API 网关层校验 token 后透传 userId header
- **方案 C**：classify-service 自己用 starter-common 的 `JwtUtil` 写拦截器

无论哪种方案，**业务代码无需修改**，只需替换上游身份注入逻辑。

---

## 8. 与微服务对接（目前无需求）

如果将来需要重新启用其他微服务（task / user / gateway / compound），需要：

1. 修改 `module/starter-database/src/main/java/.../config/DataBaseAutoConfiguration.java` 取消 mongodb 相关注释
2. 修改 `module/starter-database/pom.xml` 取消 mongodb 依赖注释
3. 修改 `module/starter-database/src/main/java/.../util/MongoUtil.java` 取消注释
4. 修改 `service/task-service/src/main/resources/application.yaml` 配置真实的 nacos / mongodb / rocketmq 地址
5. 修改 `service/user-service/src/main/resources/application.yaml` 同上

> 这些注释是因为当前演示场景下不需要 mongodb 和 nacos，省掉这些依赖让 classify-service 能独立启动。

---

## 9. 已知问题与注意事项

### 9.1 Starter 已知缺陷

本项目使用了多个公共 starter 模块，在使用过程中发现以下问题（已通过 `@Primary` 等机制在业务模块中绕过，但建议官方修复）：

1. **starter-database 的 MyMetaObjectHandler 命名遮蔽 bug**  
   `DataBaseAutoConfiguration` 中同时存在外部类和内部类同名 `MyMetaObjectHandler`，导致 `@Bean` 实际返回的是内部类（实现不完整），插入时 `createTime / updateTime / deleted` 字段填充不正确。  
   **本服务的解决方案**：在 `service/classify-service/.../config/ClassifyMetaObjectHandler.java` 用 `@Primary` 提供正确实现，覆盖默认 Bean。

2. **starter-web 的 GlobalExceptionHandler 错误码不透传**  
   `BusinessException` 的 errorCode 被丢弃，统一返回 500。  
   **本服务的解决方案**：用 `@SpringBootApplication(exclude = WebAutoConfiguration.class)` 排除自动装配，自己实现 `ClassifyGlobalExceptionHandler`。

### 9.2 临时硬编码的用户信息

由于登录功能尚未集成，前端 `src/utils/auth.js` 的 `getCurrentUser()` 方法返回了硬编码的 `userId = 12345`。这只是为了开发测试方便，**真实部署前必须接入登录功能**。

### 9.3 OSS 文件清理

OSS 默认保留所有上传的文件（模型、Excel、结果）。建议在阿里云 OSS 控制台为 bucket 配置生命周期规则：

- `outputs/` 前缀：30 天后删除（结果文件）
- `models/` 前缀：60 天后删除（用户模型，可按需调整）

---

## 10. 联系方式

如对本服务有疑问，请联系：韩坪坤