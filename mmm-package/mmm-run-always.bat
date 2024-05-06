@echo off
rem 添加路径到注册表中
set "KEY_NAME=HKEY_CURRENT_USER\Software\mmm"
set "VALUE_NAME=dir-path"
set "VALUE_DATA=%~dp0"
reg add %KEY_NAME% /v %VALUE_NAME% /t REG_SZ /d %VALUE_DATA% /f

rem 把启动程序添加到系统自启动目录中
set "sourceFile=./mmm-run.bat"
set "destinationFolder=%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup"
copy "%sourceFile%" "%destinationFolder%"