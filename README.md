一个事件管理系统
背景
开发一个事件管理系统，提供增、删、改查接口，并确保接口调用的正确性。

主要功能
创建事件 删除事件 修改事件 列出所有事件

使用 Mysql 数据库进行数据存储

安装和使用
前提条件
安装JDK 17 或更高版本（推荐使用 Java 17）

安装Maven（用于构建项目）

运行
​ 下载项目到本地 git clone https://github.com/huiz-liutaotao/transactionMgr

​ 需要修改application.yml的Mysql数据库配置，可以下面的参考“调整配置”小节

​ 切换到根目录，执行打包：mvn clean package，在target目录下生成项目的后台jar：eventMgr-0.0.1-SNAPSHOT.jar

​ 将以上jar包放到任何一个目录，执行 java -jar eventMgr-0.0.1-SNAPSHOT.jar 来运行该项目。

​ 应用程序启动后，您可以通过浏览器访问以下 URL：http://localhost:8080

调整配置
​ 当需要链接Mysql数据库时，将修改以下对应的配置项就可以链接上数据库。

image20241128121549276

当需要修改缓存配置时，可以修改如下配置项：

image20241128121658283

设计文档
请参考《事件管理系统设计文档》

扩展文档
请参考《事件管理系统扩展计划文档》
