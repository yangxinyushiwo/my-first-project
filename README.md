校园卡管理系统（Campus Card Management System）
一个基于 Vue 3 + Spring Boot 的校园卡管理平台，支持学生自助查询余额、充值、挂失及查看交易记录。

🌟 功能概览
✅ 学生登录（基于学号）
✅ 实时查看校园卡余额与状态（正常 / 已挂失）
✅ 自助在线充值
✅ 提交校园卡挂失申请
✅ 查看个人全部交易记录（充值 & 消费）
✅ 响应式前端界面，简洁易用
🛠 技术栈
层级	技术
前端	Vue 3 (Composition API + <script setup>), Vite, Axios, Vue Router（可选）
后端	Spring Boot 3, Spring Data JPA, H2/MySQL
数据库	MySQL（开发可用 H2 内存数据库）
构建工具	Maven（后端），npm（前端）
🚀 快速启动
前置要求
JDK 17+
Node.js 18+
MySQL 8（或使用 H2 内存数据库）
1. 启动后端（Spring Boot）
bash
编辑
# 进入后端目录
cd campus-card-backend

# 编译并运行（使用 Maven）
./mvnw spring-boot:run
# 或 Windows: mvnw.cmd spring-boot:run
默认访问地址：http://localhost:8080

2. 启动前端（Vue）
bash
编辑
# 进入前端目录
cd campus-card-frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
默认访问地址：http://localhost:5173

3. 访问系统
打开浏览器访问：

👉 http://localhost:5173

默认测试学号：20230001、20230002

📁 项目结构
text
编辑
campus-card-system/
├── campus-card-backend/      # 后端 Spring Boot 项目
│   ├── src/main/java/org/example/campuscardbackend
│   │   ├── controller/       # REST API 控制器
│   │   ├── entity/           # JPA 实体类
│   │   ├── repository/       # 数据访问接口
│   │   └── dto/              # 数据传输对象
│   └── application.yml       # 配置文件
│
└── campus-card-frontend/     # 前端 Vue 3 项目
    ├── src/components/       # Vue 组件（StudentHome, Recharge 等）
    └── main.js               # 入口文件
🧪 测试数据（可选）
已预置测试卡号：

学号 20230001 → 卡号 a1b2c3d4（状态：正常）
学号 20230002 → 卡号 d2b3c4d5（含三条消费记录）
可通过 SQL 手动插入更多测试数据（见文档或数据库脚本）。

📝 注意事项
前端通过 localStorage 存储当前学号（仅用于演示，生产环境需集成登录认证）
跨域问题已通过 @CrossOrigin 解决
充值与交易操作具备基本事务安全性
