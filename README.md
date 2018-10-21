安装部署流程
1. 执行SQL运行脚本，CTRL+SHIFT+R快捷键可以直接搜到
2. idea中通过maven导入项目，并且修改application-dev.yml中间件地址，mysql,rabbitmq,redis都要修改
3. maven编译install项目
4. 启动项目，顺序Eureka->Zuul->其他
5. 访问Swagger，测试接口访问