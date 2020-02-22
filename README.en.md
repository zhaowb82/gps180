
# Gps180 server platform

This project is a front-end and back-end separation project, and this project is a back-end project

Back end project address: [` gps180 Gitee '] (https://gitee.com/wibim/gps180)

Back end item address: [` gps180 GitHub '] (https://github.com/zhaowb82/gps180)

Front end project address: [` gps180-vue Gitee '] (https://gitee.com/wibim/gps180-vue)

Front end project address: [` gps180-vue GitHub '] (https://github.com/zhaowb82/gps180-vue)

## Contact QQ group: 1053077770

## Project introduction

`Gps180 ` a GPS platform developed on spring boot2. X, using spring Boot, Shiro, mybatis, redis, bootstrap, vue2. X and other frameworks, including general monitoring, fence, alarm, command, SIM, data analysis, 2-point pressing, sub account, precise authority and other GPS platform functions of GPS; also including: company management, user management, role management, group management, menu management, timed task, parameter management, code generator, log management, dru ID monitoring, API module, front and back end separation, etc.

### Project presentation

**Demo address:**

- Computer end: [http://wibim.vicp.net:8089/] (http://wibim.vicp.net:8089/)

- Mobile terminal download (in the development of fluent):![](./doc/pic/appdown.png)

**Account password:**

- Super administrator: ` testadmin / 123456`

- Company administrator: ` testadmin2 / 123456`

- Device login: ` 13503548801 / 1133`

**Device connection address and port:**

- wibim.vicp.net : 6868

- You can download the Android test app to simulate the GPS report test [` GPS simulator '] (https://gitee.com/wibim/gps_simulator)

**Support agreement:**

-Jt808, tianqin, BoShiJie, konkas, millet, 32960

### SaaS demonstration of commercial tube vehicle

**Computer end: * * [https://biz.ccwcar.com/] (https://biz.ccwcar.com/)

**Pure GPS terminal: * * [https://gps.ccwcar.com/] (https://gps.ccwcar.com/)

**Mobile terminal:**

![](./doc/pic/syshare.png)

![](./doc/pic/syapp.jpg)

After registering an account, I joined the demo company with company number of '290383'. After applying for joining, I will contact QQ group

----------------------------------------------------------------------------------
### System architecture

![](./doc/pic/jsjg.png)

![](./doc/pic/js1.png)

### Technology selection

|Technical | description|
|---|--- |
|[spring boot] (https://spring.io/projects/spring-boot) | core framework|
|[Apache Shiro] (http://shiro.apache.org) | security framework|
|[mybatis plus] (https://mp.baomidou.com/) | ORM framework|
|[Druid] (https://github.com/alibaba/druid/wiki/% E5% B8% B8% E8% A7% 81% E9% 97% AE% E9% A2% 98) | data connection pool|
|[redis] (https://redis.io) | cache database|
|[swagger UI] (https://github.com/swagger-api/swagger-ui) | API document production tool|
|[JWT] (https://github.com/jwtk/jjwt) | JWT login support|
|[netty] (https://netty.io/) | device TCP UDP connection|
|[Lombok] (https://github.com/rzwiserloot/lombok) | simplified object encapsulation tool|
|[gradle] (https://gradle.org/) | project management|
|[docker] (https://www.docker.com/) | application container engine|

### System functions

- Company management: it can be used to configure the system organization structure, display the tree form, and open accounts for the subordinate subsidiaries
- User management: provide the user's related configuration. After opening a subordinate company, add an account to log in
- Role management: assign permissions and menus, set data permissions of account, and fine manage
- Menu management: menu dynamic routing has been realized, the back end can be configured, and multi-level menu is supported
- Operation log: log the user's operation
- SQL monitoring: use druid to monitor database access performance, default user name is admin, password is 123456
- Timing task: integrate quartz to do timing task, add task log, and the task operation is clear at a glance
- Code generation: high flexibility one key generation of front and back end code, reducing work tasks by about 80%
- API document: Show API interface with swagger UI, convenient for secondary development
- GPS monitoring: the home page shows the location of all the devices that users can see, online, offline, online devices can display in real time
- GPS tracking: after tracking, you can check the driving path of the equipment
- GPS alarm: real time monitoring alarm message, prompt at the computer end
- GPS event: real time monitoring event message, prompt on the computer
- GPS fence: provide fence setting, draw circle and polygon. GPS equipment can be bound to the fence, in and out of the fence will be notified to the computer in real time, and the home page can display a fence for monitoring
- GPS report: provide multiple functions of alarm analysis, position analysis, travel analysis and dwell point analysis
- GPS group: provide group management, multiple devices can be added to the group, and the group is reflected in the home page
- GPS recording: support recordable devices, upload to database for saving, download to AMR file, and then monitor
- GPS card management: if the SIM card interface can be docked, it can be developed and docked again here
- GPS command: provides commands for more than 50 devices, which is very convenient to add and distribute
- 809 platform docking: it can be used as a superior and subordinate platform docking to other ministerial standard platforms

### Version comparison

|Features | open source version | commercial version|
|---|---|---|
|API function | [x] | [x]|
|API WS connection | [x] | [x]|
|Code generator | [x] | [x]|
|Kafka queue | [x] | [x]|
|Gate protocol supports HTTP test and full protocol|
|Gate channel supports TCP + UDP|
|Gate issues command | [] | [x]|
|Gate multi protocol single port|
|Engine writes MySQL | [x] | [x]|
|Engine writes HBase | [] | [x]|
|Engine notifies alarm | [] | [x]|
|Cluster deployment support | []|[x]|
|Jt809 forward | [] | [x]|
|Initialize command data | [] | [x]|
|App developed later | [] | [x]|

### Project structure

``` lua
├─gps180
├── gps-api -- API interface module for front-end or future app
├── gps-common -- lib common module
├── gps-db -- GPS DB -- lib public database operation module
├── gps-gbt32960 -- lib 32960 electric vehicle national standard protocol analysis module
├── gps-gennerator -- code generator, for secondary development
├── gps-jt809 -- Protocol (server forwarding) module of ministry standard 809
├── gps-websocket -- lib provides real-time data update for front-end or app
├── hbase -- lib HBase storage (big data scheme) operation module
├── gps-gate -- data receiving and protocol analysis module of GPS equipment
├── gps-engine - GPS data processing and distribution module
```

### The latest 1.0 update is as follows (updated on February 15, 2020)
- Data permission upgrade.
- The front-end products are more easy to use, more concise and atmospheric.
- Modify many known issues.
- Add test permission and test user.
- Solve the conflict between device login and user login.

### Project features
- Based on springboot, using the latest technology stack, the community is rich in resources.
- Realize the separation of front end and back end, and carry out data interaction through token. The front end no longer needs to pay attention to the back end technology
- A complete log recording system can record login logs and business operation logs.
- The project is modularized according to the function to improve the efficiency of development and test.
- Efficient development, using code generator can generate front and back end code with one key.
- Flexible permission control, which can be controlled to the page or button to meet most of the permission needs.
- Prevent XSS attack, filter and replace all illegal strings.
- The introduction of quartz timing task can dynamically complete the functions of adding, modifying, deleting, suspending, resuming and viewing logs.
- It is convenient to write API documents by introducing swagger document support.
- Using netty as a long connection tool, one server supports 100000 + devices.
- Using big data scheme to store GPS track data.

### Partial screenshot

![](./doc/pic/func1.png)

![](./doc/pic/func4.png)

![](./doc/pic/func2.png)

![](./doc/pic/func3.png)

## Permit to permit

[Apache License 2.0](https://gitee.com/wibim/gps180/blob/master/LICENSEE)

## CCW open source project

Official website: https://www.ccwcar.com/

