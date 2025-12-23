# 校园卡管理系统

> 基于 Vue 3 + Spring Boot 的校园卡管理平台，支持学生自助查询余额、充值、挂失及查看交易记录。

## 🌟 功能特点
- 学生登录（学号）
- 实时查看校园卡余额与状态（正常 / 已挂失）
- 自助在线充值
- 提交校园卡挂失申请
- 查看个人全部交易记录（充值 & 消费）

## 🛠 技术栈
- **前端**：Vue 3（Composition API + `<script setup>`）、Vite、Axios
- **后端**：Spring Boot 3、Spring Data JPA
- **数据库**：MySQL（开发阶段可使用 H2）
- **构建工具**：Maven（后端）、npm（前端）

## 🚀 快速启动

### 前置要求
- JDK 17+
- Node.js 18+
- MySQL 8（可选，H2 内存数据库已配置）

### 启动后端
```bash
cd campus-card-backend
./mvnw spring-boot:run
