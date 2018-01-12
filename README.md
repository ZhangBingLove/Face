# Face
智能人脸识别+语音提示

<center>
 <img src="http://img.blog.csdn.net/20171220152831661?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvY29kZWx1Y2s=/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/SouthEast" width="30%" alt="center"/>
</center>


调查了很多第三方的人脸识别，对比百度、face++、虹软，鉴于百度和face++收费较高，最后还是采用了虹软的人脸识别。
关于虹软原来的Demo可以参考这篇博客https://www.jianshu.com/p/75733cff88a3
虹软有5个jar包，分别是，人脸录入，检测年龄、检测性别、人脸识别，人脸追踪，这个在官网是可以下载的。
因为so文件比较大的原因，我在这个项目中只用到了人脸录入，人脸识别，人脸追踪，这3个基本的功能，使包在原来的基础上减小了1/3。

# 项目简单的业务逻辑
整个项目以签到为场景，用户登录上来，会提示，新建一个签到活动，创建好活动项目后，进入签到活动页面，这时就可以录入人脸了，录入的人脸会存在本地数据库，
有了人
