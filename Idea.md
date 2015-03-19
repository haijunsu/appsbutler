# 简介 #

参考了springside和appfuse的一些框架，觉得离快速开发还是差了一点。appsbutler想做为一个集各类services的总管，提供一些公共的接口，使用JMX的技术来使services具备状态管理的功能。


# 内容 #

初步想法：
  * 不能放弃配置文件，对springside的零配置持保留意见
  * 利用Hibernate配置文件，提供更多的codegen的功能，简化开发
  * sitemash装饰页面
  * 使用taglib（eXtremeTable）来显示页面表格 http://sourceforge.net/projects/extremecomp/
  * DWR和jQuery实现ajax功能 http://jquery-api-zh-cn.googlecode.com/svn/trunk/xml/jqueryapi.xml
  * Xfile实现web service功能
  * ActiveMQ实现services的订阅功能
  * 使用encache做为缓存框架
  * 定义基本的Thread和Timer框架
  * 增加对JCA的支持