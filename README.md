# mmm

用于管理多间多媒体教室的电脑的服务端(可以搭配mmm-v前端项目)

当运行该项目后,多媒体教师电脑会在后台运行一个web服务,并且开机自启,客户端可以搜索内网中所有ip的14569端口,以发现服务器.

使用流程:
0. 下载jdk21,并且将opencv中的opencv_java490.dll放在jdk/bin中,之后将jdk复制到mmm-package中
0. 复制mmm-package文件夹到多媒体教室电脑上
0. 在room-name.txt中填写教室的名称
0. 在shutDownTime.mmm中填写定时关机的时间(形如12:15),以换行符分隔,可以不写,表示不需要定时关机功能
0. 下载opencv,并且将opencv_java490.dll放在jdk/bin中
0. 双击mmm-run.bat运行本项目(会设置开机自启)