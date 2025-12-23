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
- **数据库**：MySQL（开发阶段可使用 H2 内存数据库）  
- **构建工具**：Maven（后端）、npm（前端）

## 🚀 快速启动

### 前置要求

- JDK 17+
- Node.js 18+
- MySQL 8（可选，项目已配置 H2 内存数据库用于开发）

### 启动后端

cd campus-card-backend
./mvnw spring-boot:run

服务运行于：`http://localhost:8080`

### 启动前端

cd campus-card-frontend
npm install
npm run dev

访问地址：`http://localhost:5173`

### 默认测试账号

- 学号：`20230001`（校园卡状态：正常）  
- 学号：`20230002`（含三条消费记录，可用于测试交易历史）

### 📁 项目结构
campus-card-system/
├── campus-card-backend/      # Spring Boot 后端项目
│   ├── src/main/java/org/example/campuscardbackend
│   │   ├── controller/       # RESTful API 控制器
│   │   ├── entity/           # JPA 实体类（CampusCard, TransactionRecord）
│   │   ├── repository/       # 数据访问接口
│   │   └── dto/              # 数据传输对象（如 RechargeRequest）
│   └── src/main/resources/application.yml  # 配置文件
└── campus-card-frontend/     # Vue 3 前端项目
    └── src/components/
        ├── StudentHome.vue   # 学生主页（主界面）
        ├── Recharge.vue      # 充值组件
        ├── CardApplication.vue # 挂失申请组件
        └── TransactionHistory.vue # 交易记录组件

### ⚠️ 注意事项

- 前端通过 `localStorage` 存储当前学号，仅用于演示，**生产环境应集成身份认证**（如 JWT）。
- 跨域请求已通过 `@CrossOrigin(origins = "http://localhost:5173")` 解决。
- 充值操作使用 `@Transactional` 注解，确保交易记录与余额更新的原子性。
- 交易类型：`recharge`（充值）、`consume`（消费），前端根据类型区分显示样式。
