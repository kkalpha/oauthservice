# oauthservice
提供OAuth2 的认证服务的一个POC 应用，
可以修改扩展作为企业集成中单点登录的认证服务器。

1，在MySQL 数据库新建表auth_client_details，sql 脚本文件在\oauth-service\etc\sql.txt
在oauth-service\src\main\resources\dev\application.properties中修改数据库对应的地址

2,运行 oauth-service\src\main\java\com\tech\OAuthServerApplication.java 的 main 方法启动 OAuth server
服务以 Web 服务启动在 8088 端口

3，运行 oauth-client\src\main\java\com\tech\oauthclient\OAuthClientApplication.java 的 main 方法启动 Oauth 客户端应用
应用以 Web 服务启动在 8082 端口

4，测试
访问 http://localhost:8082/，点击链接，跳转到认证服务器进行登录认证。
认证通过后跳转到客户端应用的功能页面。
登录可以使用 oauth-service\src\main\resources\dev\user-list 中的用户，密码可以是任意
