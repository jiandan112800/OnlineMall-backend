# 在线商城系统 - 后端服务

## 项目简介

在线商城系统后端服务，基于 Spring Boot 框架开发，提供完整的电商业务 API 接口，包括用户管理、商品管理、订单管理、购物车、支付等功能。

## 系统技术架构图

```
┌─────────────────────────────────────────────────────────────┐
│                        前端应用层                            │
│                    (Vue.js + Element UI)                     │
└────────────────────────┬────────────────────────────────────┘
                         │ HTTP/HTTPS
                         │ RESTful API
┌────────────────────────▼────────────────────────────────────┐
│                      API 网关层                              │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐      │
│  │  JWT 认证    │  │  跨域配置    │  │  权限拦截    │      │
│  │  Interceptor │  │  CorsConfig  │  │  Authority   │      │
│  └──────────────┘  └──────────────┘  └──────────────┘      │
└────────────────────────┬────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────┐
│                     业务逻辑层                               │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ 用户服务 │  │ 商品服务 │  │ 订单服务 │  │ 购物车   │   │
│  │UserService│ │GoodService│ │OrderService│ │CartService│   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ 地址服务 │  │ 分类服务 │  │ 文件服务 │  │ 收入服务 │   │
│  │Address   │  │Category  │  │FileService│ │Income    │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
└────────────────────────┬────────────────────────────────────┘
                         │
┌────────────────────────▼────────────────────────────────────┐
│                     数据访问层                               │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ MyBatis  │  │ MyBatis  │  │  Mapper  │  │   XML    │   │
│  │  Plus    │  │  Core    │  │ 接口层   │  │ 映射文件 │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
└────────────────────────┬────────────────────────────────────┘
                         │
         ┌───────────────┴───────────────┐
         │                               │
┌────────▼────────┐            ┌─────────▼────────┐
│   MySQL 数据库  │            │   Redis 缓存     │
│   (持久化存储)  │            │   (会话/缓存)    │
└─────────────────┘            └──────────────────┘
```

## 技术栈

### 核心框架
- **Spring Boot**: 2.5.6
- **Java**: 1.8
- **Maven**: 项目构建工具

### 数据持久化
- **MyBatis**: 2.2.2 - ORM 框架
- **MyBatis-Plus**: 3.5.1 - MyBatis 增强工具
- **MySQL**: 关系型数据库
- **Druid**: 1.2.2 - 数据库连接池

### 缓存
- **Redis**: 会话存储和缓存
- **Spring Data Redis**: Redis 集成

### 安全认证
- **JWT (java-jwt)**: 3.10.3 - Token 认证
- **自定义拦截器**: JWT 验证和权限控制

### 工具类库
- **Hutool**: 5.7.21 - Java 工具类库
- **FastJSON**: 1.2.73 - JSON 处理
- **Apache Commons Compress**: 1.21 - 文件压缩

### API 文档
- **Swagger 2**: 2.9.2 - API 接口文档

## 目录结构

```
OnlineMall-backend/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── muzi/
│       │           ├── annotation/          # 自定义注解
│       │           │   └── Authority.java   # 权限注解
│       │           ├── common/              # 通用类
│       │           │   └── Result.java      # 统一响应结果
│       │           ├── config/              # 配置类
│       │           │   ├── CorsConfig.java          # 跨域配置
│       │           │   ├── GlobalExceptionHandler.java  # 全局异常处理
│       │           │   ├── InterceptorConfig.java   # 拦截器配置
│       │           │   ├── MybatisPlusConfig.java   # MyBatis-Plus配置
│       │           │   ├── RedisConfig.java         # Redis配置
│       │           │   └── SwaggerConfig.java       # Swagger配置
│       │           ├── constants/           # 常量类
│       │           │   ├── Constants.java   # 系统常量
│       │           │   └── RedisConstants.java  # Redis常量
│       │           ├── controller/          # 控制器层
│       │           │   ├── AddressController.java   # 地址管理
│       │           │   ├── AvatarController.java    # 头像管理
│       │           │   ├── CarouselController.java  # 轮播图管理
│       │           │   ├── CartController.java      # 购物车管理
│       │           │   ├── CategoryController.java  # 分类管理
│       │           │   ├── FileController.java      # 文件管理
│       │           │   ├── GoodController.java      # 商品管理
│       │           │   ├── IconController.java      # 图标管理
│       │           │   ├── IncomeController.java    # 收入统计
│       │           │   ├── OrderController.java     # 订单管理
│       │           │   ├── OrderCommentController.java  # 订单评论管理
│       │           │   ├── RoleController.java      # 角色管理
│       │           │   └── UserController.java      # 用户管理
│       │           ├── entity/              # 实体类
│       │           │   ├── dto/             # 数据传输对象
│       │           │   │   ├── GoodDTO.java
│       │           │   │   └── UserDTO.java
│       │           │   ├── Address.java     # 地址实体
│       │           │   ├── AuthorityType.java   # 权限类型
│       │           │   ├── Avatar.java      # 头像实体
│       │           │   ├── Carousel.java    # 轮播图实体
│       │           │   ├── Cart.java        # 购物车实体
│       │           │   ├── Category.java    # 分类实体
│       │           │   ├── Good.java        # 商品实体
│       │           │   ├── GoodStandard.java    # 商品规格实体
│       │           │   ├── Icon.java        # 图标实体
│       │           │   ├── IconCategory.java    # 图标分类实体
│       │           │   ├── LoginForm.java   # 登录表单
│       │           │   ├── MyFile.java      # 文件实体
│       │           │   ├── Order.java       # 订单实体
│       │           │   ├── OrderComment.java    # 订单评论实体
│       │           │   ├── OrderGoods.java  # 订单商品实体
│       │           │   ├── OrderItem.java   # 订单项实体
│       │           │   ├── Standard.java    # 规格实体
│       │           │   └── User.java        # 用户实体
│       │           ├── exception/           # 异常处理
│       │           │   ├── GlobalExceptionHandler.java  # 全局异常处理器
│       │           │   └── ServiceException.java        # 业务异常
│       │           ├── interceptor/         # 拦截器
│       │           │   ├── AuthorityInterceptor.java   # 权限拦截器
│       │           │   └── JwtInterceptor.java         # JWT拦截器
│       │           ├── mapper/              # Mapper接口
│       │           │   ├── AddressMapper.java
│       │           │   ├── AvatarMapper.java
│       │           │   ├── CarouselMapper.java
│       │           │   ├── CartMapper.java
│       │           │   ├── CategoryMapper.java
│       │           │   ├── FileMapper.java
│       │           │   ├── GoodMapper.java
│       │           │   ├── IconCategoryMapper.java
│       │           │   ├── IconMapper.java
│       │           │   ├── IncomeMapper.java
│       │           │   ├── OrderCommentMapper.java
│       │           │   ├── OrderGoodsMapper.java
│       │           │   ├── OrderMapper.java
│       │           │   ├── StandardMapper.java
│       │           │   └── UserMapper.java
│       │           ├── service/             # 服务层
│       │           │   ├── AddressService.java
│       │           │   ├── AvatarService.java
│       │           │   ├── CarouselService.java
│       │           │   ├── CartService.java
│       │           │   ├── CategoryService.java
│       │           │   ├── FileService.java
│       │           │   ├── GoodService.java
│       │           │   ├── IconService.java
│       │           │   ├── IncomeService.java
│       │           │   ├── OrderCommentService.java
│       │           │   ├── OrderGoodsService.java
│       │           │   ├── OrderService.java
│       │           │   ├── StandardService.java
│       │           │   └── UserService.java
│       │           ├── utils/               # 工具类
│       │           │   ├── BaseApi.java     # API基础类
│       │           │   ├── PathUtils.java   # 路径工具类
│       │           │   ├── TokenUtils.java  # Token工具类
│       │           │   ├── UserHolder.java  # 用户上下文
│       │           │   └── Util.java        # 工具类
│       │           └── ElectronicMallApplication.java  # 启动类
│       └── resources/
│           ├── application.yml              # 应用配置文件
│           └── mapper/                      # MyBatis XML映射文件
│               ├── Address.xml
│               ├── Carousel.xml
│               ├── Cart.xml
│               ├── Category.xml
│               ├── GoodMapper.xml
│               ├── Icon.xml
│               ├── Income.xml
│               ├── Order.xml
│               ├── OrderGoods.xml
│               ├── Standard.xml
│               └── User.xml
├── avatar/                                  # 头像存储目录
├── file/                                    # 文件存储目录
├── pom.xml                                  # Maven配置文件
└── README.md                                # 项目说明文档
```

## 核心功能模块

### 1. 用户管理模块
- 用户注册/登录
- JWT Token 认证
- 用户信息查询/更新
- 用户权限管理
- 头像上传管理
- 用户分页查询
- 批量删除用户
- 密码重置

**主要接口：**
- `POST /login` - 用户登录
- `POST /register` - 用户注册
- `GET /userinfo/{username}` - 获取用户信息
- `GET /userid` - 获取当前登录用户ID
- `GET /user/` - 查询所有用户
- `GET /user/page` - 用户分页查询（支持用户名、昵称搜索）
- `POST /user` - 保存/更新用户
- `DELETE /user/{id}` - 删除用户
- `POST /user/del/batch` - 批量删除用户
- `GET /user/resetPassword` - 重置用户密码

### 2. 商品管理模块
- 商品 CRUD 操作
- 商品分类管理（多级分类）
- 商品规格管理
- 商品推荐管理
- 商品销量排行
- 商品分页查询
- 轮播图管理

**主要接口：**
- `GET /api/good` - 获取推荐商品列表
- `GET /api/good/{id}` - 获取商品详情
- `GET /api/good/page` - 商品分页查询（支持搜索和分类筛选）
- `GET /api/good/fullPage` - 商品完整分页查询
- `POST /api/good` - 新增商品（需权限）
- `PUT /api/good` - 更新商品（需权限）
- `DELETE /api/good/{id}` - 删除商品（需权限）
- `GET /api/good/rank` - 获取销量排行
- `GET /api/good/standard/{id}` - 获取商品规格信息
- `POST /api/good/standard` - 保存商品规格信息（需权限）
- `DELETE /api/good/standard` - 删除商品规格信息（需权限）
- `GET /api/good/recommend` - 设置商品推荐状态（需权限）
- `GET /api/category` - 获取分类列表
- `GET /api/category/{id}` - 根据ID获取分类详情
- `POST /api/category` - 新增分类
- `POST /api/category/add` - 新增下级分类（支持上下级关联）
- `PUT /api/category` - 更新分类（需权限）
- `GET /api/category/delete` - 删除分类（需权限）
- `GET /api/carousel` - 获取轮播图列表
- `GET /api/carousel/{id}` - 根据ID获取轮播图详情
- `POST /api/carousel` - 新增轮播图（需权限）
- `PUT /api/carousel` - 更新轮播图（需权限）
- `DELETE /api/carousel/{id}` - 删除轮播图（需权限）

### 3. 购物车模块
- 购物车商品添加/删除
- 购物车商品数量修改
- 购物车列表查询

**主要接口：**
- `GET /api/cart/{id}` - 根据ID获取购物车详情
- `GET /api/cart` - 获取所有购物车（需登录）
- `GET /api/cart/userid/{userId}` - 获取用户购物车
- `POST /api/cart` - 添加商品到购物车（需登录）
- `PUT /api/cart` - 更新购物车商品（需登录）
- `DELETE /api/cart/{id}` - 删除购物车商品（需登录）

### 4. 订单管理模块
- 订单创建
- 订单查询（按用户、订单号）
- 订单分页查询
- 订单状态管理
- 订单支付
- 订单发货
- 订单确认收货

**主要接口：**
- `GET /api/order` - 获取所有订单（需登录）
- `POST /api/order` - 创建订单（需登录）
- `GET /api/order/userid/{userid}` - 获取用户订单列表（需登录）
- `GET /api/order/orderNo/{orderNo}` - 根据订单号查询（需登录）
- `GET /api/order/page` - 分页查询订单（支持订单号和状态筛选，需登录）
- `PUT /api/order` - 更新订单（需登录）
- `DELETE /api/order/{id}` - 删除订单（需登录）
- `GET /api/order/paid/{orderNo}` - 支付订单（需登录）
- `GET /api/order/delivery/{orderNo}` - 订单发货（需权限）
- `GET /api/order/received/{orderNo}` - 确认收货（需登录）

### 5. 地址管理模块
- 收货地址 CRUD
- 用户地址列表查询

**主要接口：**
- `GET /api/address` - 获取所有地址（需登录）
- `GET /api/address/{userId}` - 获取用户地址列表（需登录）
- `POST /api/address` - 新增地址（需登录）
- `PUT /api/address` - 更新地址（需登录）
- `DELETE /api/address/{id}` - 删除地址（需登录）

### 6. 文件管理模块
- 文件上传/下载
- 头像上传/下载
- 文件存储管理
- 文件启用/禁用
- 文件批量删除

**主要接口：**
- `POST /file/upload` - 文件上传
- `GET /file/{fileName}` - 下载文件
- `GET /file/page` - 文件分页查询（支持文件名搜索）
- `GET /file/enable` - 修改文件启用状态（需权限）
- `DELETE /file/{id}` - 删除文件（需权限）
- `POST /file/del/batch` - 批量删除文件（需权限）
- `POST /avatar` - 头像上传
- `GET /avatar/{fileName}` - 下载头像
- `GET /avatar/page` - 头像分页查询
- `DELETE /avatar/{id}` - 删除头像（需权限）

### 7. 收入统计模块
- 收入数据统计
- 收入图表数据
- 周收入统计
- 月收入统计

**主要接口：**
- `GET /api/income/chart` - 获取收入图表数据（需权限）
- `GET /api/income/week` - 获取周收入统计（需权限）
- `GET /api/income/month` - 获取月收入统计（需权限）

### 8. 订单评论模块
- 商品评论查询
- 订单评论添加

**主要接口：**
- `GET /api/comment/good/{goodId}` - 获取商品评论列表
- `POST /api/comment` - 添加订单评论（需登录）

### 9. 图标管理模块
- 图标 CRUD 操作
- 图标分类管理

**主要接口：**
- `GET /api/icon` - 获取所有图标（带分类信息）
- `GET /api/icon/{id}` - 根据ID获取图标详情
- `POST /api/icon` - 新增图标（需权限）
- `PUT /api/icon` - 更新图标（需权限）
- `GET /api/icon/delete` - 删除图标（需权限）

### 10. 权限管理模块
- 基于注解的权限控制
- JWT Token 验证
- 角色权限管理

**主要接口：**
- `POST /role` - 获取当前用户角色

## 开发环境部署（Windows）

### 环境要求

1. **JDK 1.8+**
   - 下载地址：https://www.oracle.com/java/technologies/javase/javase8-archive-downloads.html
   - 配置环境变量 `JAVA_HOME` 和 `PATH`

2. **Maven 3.6+**
   - 下载地址：https://maven.apache.org/download.cgi
   - 配置环境变量 `MAVEN_HOME` 和 `PATH`

3. **MySQL 8.0+**
   - 下载地址：https://dev.mysql.com/downloads/mysql/
   - 安装并启动 MySQL 服务

4. **Redis**
   - Windows 版本下载：https://github.com/microsoftarchive/redis/releases
   - 或使用 WSL 安装 Redis
   - 启动 Redis 服务

### 部署步骤

#### 1. 克隆项目
```bash
git clone <项目地址>
cd OnlineMallSystem/OnlineMall-backend
```

#### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE db_electronic_mall CHARACTER SET utf8;
```

2. 导入数据库脚本：
```bash
# 在 MySQL 中执行
source database/electronic_mall.sql
```

3. 修改数据库配置：
编辑 `src/main/resources/application.yml`，修改数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/db_electronic_mall?serverTimezone=GMT%2b8&userSSL=false&allowPublicKeyRetrieval=true
    username: root  # 修改为你的数据库用户名
    password: '123456'  # 修改为你的数据库密码
```

#### 3. Redis 配置

确保 Redis 服务已启动，默认配置：
- 主机：127.0.0.1
- 端口：6379
- 数据库：0

如需修改，编辑 `src/main/resources/application.yml`：
```yaml
spring:
  redis:
    host: 127.0.0.1
    port: 6379
    database: 0
```

#### 4. 创建文件存储目录

在项目根目录下创建以下目录（如果不存在）：
- `avatar/` - 头像存储目录
- `file/` - 文件存储目录

#### 5. 编译项目

```bash
# 使用 Maven 编译
mvn clean compile

# 或使用 Maven 打包
mvn clean package
```

#### 6. 运行项目

**方式一：使用 Maven 运行**
```bash
mvn spring-boot:run
```

**方式二：使用 IDE 运行**
- 在 IntelliJ IDEA 或 Eclipse 中打开项目
- 找到 `ElectronicMallApplication.java` 主类
- 右键运行 `main` 方法

**方式三：运行 JAR 包**
```bash
java -jar target/OnlineMallBackend-0.0.1-SNAPSHOT.jar
```

#### 7. 验证部署

1. 访问 Swagger API 文档：
   ```
   http://localhost:8080/swagger-ui.html
   ```

2. 测试登录接口：
   ```bash
   POST http://localhost:8080/login
   Content-Type: application/json
   
   {
     "username": "admin",
     "password": "123456"
   }
   ```

### 项目配置说明

#### application.yml 主要配置项

```yaml
server:
  port: 8080  # 服务端口

spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/db_electronic_mall?serverTimezone=GMT%2b8&userSSL=false&allowPublicKeyRetrieval=true
    username: root
    password: '123456'
  servlet:
    multipart:
      max-file-size: 30MB  # 文件上传大小限制
  redis:
    host: 127.0.0.1
    port: 6379
    database: 0

mybatis:
  mapper-locations: classpath:mapper/*.xml
  configuration:
    map-underscore-to-camel-case: true  # 驼峰命名转换
```

## 注意事项

### 1. 数据库相关
- ⚠️ 确保 MySQL 服务已启动
- ⚠️ 数据库字符集必须为 `utf8` 或 `utf8mb4`
- ⚠️ 首次运行前必须执行数据库初始化脚本
- ⚠️ 修改数据库连接信息后需要重启服务

### 2. Redis 相关
- ⚠️ Redis 服务必须启动，否则会影响登录功能
- ⚠️ 确保 Redis 端口未被占用
- ⚠️ 生产环境建议配置 Redis 密码

### 3. 文件存储
- ⚠️ `avatar/` 和 `file/` 目录需要有写入权限
- ⚠️ 文件存储路径在 `Constants.java` 中配置
- ⚠️ 生产环境建议使用对象存储服务（OSS）

### 4. 跨域配置
- ⚠️ 前端开发环境需要配置代理或修改 `CorsConfig.java`
- ⚠️ 生产环境建议限制允许的域名

### 5. 安全相关
- ⚠️ JWT Token 密钥建议修改为复杂字符串
- ⚠️ 生产环境必须修改默认密码
- ⚠️ 敏感信息不要提交到代码仓库

### 6. 性能优化
- ⚠️ 生产环境建议配置数据库连接池参数
- ⚠️ 建议开启 MyBatis 二级缓存
- ⚠️ 大文件上传建议使用分片上传

### 7. 日志配置
- ⚠️ 生产环境建议配置日志级别和输出位置
- ⚠️ 建议使用日志框架（如 Logback）进行日志管理

### 8. 端口占用
- ⚠️ 默认端口 8080，如被占用需修改 `application.yml`
- ⚠️ 修改端口后需要同步修改前端 API 地址

### 9. Maven 依赖
- ⚠️ 首次运行需要下载依赖，可能需要较长时间
- ⚠️ 如遇依赖下载失败，可配置国内镜像源（已配置阿里云镜像）

### 10. 编码问题
- ⚠️ 确保 IDE 和文件编码为 UTF-8
- ⚠️ Windows 系统注意路径分隔符问题

## API 文档

项目集成了 Swagger 2，启动服务后访问：
```
http://localhost:8080/swagger-ui.html
```

可以查看所有 API 接口的详细文档和在线测试功能。

## 常见问题

### Q1: 启动失败，提示数据库连接错误
**A:** 检查 MySQL 服务是否启动，数据库名称、用户名、密码是否正确。

### Q2: Redis 连接失败
**A:** 检查 Redis 服务是否启动，端口是否为 6379。

### Q3: 文件上传失败
**A:** 检查 `avatar/` 和 `file/` 目录是否存在且有写入权限。

### Q4: 跨域请求被阻止
**A:** 检查 `CorsConfig.java` 配置，确保允许前端域名访问。

### Q5: JWT Token 验证失败
**A:** 检查请求头是否包含 `token` 字段，Token 是否过期。

## 开发规范

1. **代码规范**：遵循阿里巴巴 Java 开发手册
2. **命名规范**：使用驼峰命名法
3. **注释规范**：关键业务逻辑必须添加注释
4. **异常处理**：使用统一异常处理机制
5. **日志规范**：关键操作必须记录日志

## 联系方式

如有问题，请联系项目维护者。

---

**最后更新**: 2025年11月

