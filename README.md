# 伴好健康管理系统 🏥

> 企业级健康管理 SaaS 平台 | Spring Boot + MyBatis-Plus + Redis + Spring AI

面向企业及个人提供一站式健康管理服务，涵盖体检预约、套餐管理、AI 智能问答三大核心模块。

---

## 技术栈

| 层级 | 技术 |
|------|------|
| 后端框架 | Spring Boot 3.x |
| ORM | MyBatis-Plus |
| 数据库 | MySQL 8.0 |
| 缓存 | Redis |
| 对象存储 | 阿里云 OSS |
| AI 能力 | Spring AI + 向量数据库 + RAG |
| 构建工具 | Maven |
| 版本控制 | Git |

---

## 核心功能

### 1. 体检预约管理
- 基于日期驱动的动态名额配置，支持多时段、多类型体检项目灵活排期
- 用户端查看可预约时段，实时展示剩余名额
- 管理端设置每日各时段最大预约量，自动校验冲突

### 2. 检查套餐管理
- 套餐 CRUD 完整功能，支持套餐图片异步上传至阿里云 OSS
- 前端文件大小校验（≤ 5MB）+ 后端二次校验
- 套餐列表支持分类筛选与模糊搜索

### 3. AI 智能问答 🤖
- 基于 **Spring AI + RAG（检索增强生成）** 架构实现
- 支持体检项目推荐、预约流程咨询、套餐对比等智能问答
- **上下文记忆**：集成 ChatMemory + Advisor 实现多轮对话
- **流式输出**：实时逐字返回 AI 回答，提升交互体验
- 回答准确率：基线 62% → 优化后 **89%**

---

## 性能优化

| 优化项 | 优化前 | 优化后 | 手段 |
|--------|--------|--------|------|
| 首页套餐接口响应 | ~800ms | ~120ms | Redis 缓存 + 过期策略 |
| 数据库 QPS 峰值 | - | 降低约 60% | 缓存命中分担读压力 |
| 预约查询 | type=ALL | type=range | 联合索引优化 |

---

## 快速启动

```bash
# 1. 克隆项目
git clone https://github.com/zzdloul/health-management.git
cd health-management

# 2. 配置数据库
# 修改 application.yml 中的 MySQL 和 Redis 连接信息
# 执行 sql/init.sql 初始化表结构

# 3. 配置阿里云 OSS（可选，用于图片上传）
# 在 application.yml 中填写 AccessKey 和 Bucket 信息

# 4. 配置 AI 服务
# 在 application.yml 中填写大模型 API Key

# 5. 启动项目
mvn spring-boot:run
```

---

## 项目结构

```
src/main/java/com/health/
├── controller/     # 接口层
├── service/        # 业务逻辑层
│   └── ai/        # AI 问答服务
├── mapper/         # 数据访问层
├── entity/         # 实体类
├── config/         # 配置类（Redis、OSS、AI）
└── utils/          # 工具类
```

---

## 个人职责

- 独立负责「预约设置」与「检查套餐」模块全流程开发
- 设计数据模型与 10+ RESTful 接口，交付后零返修
- 搭建 RAG 智能问答系统，实现多轮对话与流式输出
- 引入 Redis 缓存与联合索引，显著提升接口性能

---

## 许可证

MIT License
