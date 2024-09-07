@echo off
>nul 2>&1 "%SYSTEMROOT%\system32\cacls.exe" "%SYSTEMROOT%\system32\config\system"
if '%errorlevel%' NEQ '0' (
echo Requesting administrative privileges...
goto UACPrompt
) else ( goto gotAdmin )
:UACPrompt
echo Set UAC = CreateObject^("Shell.Application"^) > "%temp%\getadmin.vbs"
echo UAC.ShellExecute "%~s0", "", "", "runas", 1 >> "%temp%\getadmin.vbs"
"%temp%\getadmin.vbs"
exit /B
:gotAdmin
if exist "%temp%\getadmin.vbs" ( del "%temp%\getadmin.vbs" )
pushd "%CD%"
CD /D "%~dp0"
netsh advfirewall firewall add rule name="Open Port 14569" dir=out action=allow protocol=TCP localport=14569
netsh advfirewall firewall add rule name="Open Port 14569" dir=in action=allow protocol=TCP localport=14569

set "filePath=%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup\mmm-run.bat"
if exist "%filePath%" (
    del /f /q "%filePath%"
    echo file has been deleted
) else (
    echo file is not exist
)

set "KEY_NAME=HKEY_CURRENT_USER\Software\mmm"
set "VALUE_NAME=dir-path"
set "VALUE_DATA=%~dp0"
reg add %KEY_NAME% /v %VALUE_NAME% /t REG_SZ /d %VALUE_DATA% /f

set "sourceFile=./mmm-run.bat"
set "destinationFolder=%APPDATA%\Microsoft\Windows\Start Menu\Programs\Startup"
copy "%sourceFile%" "%destinationFolder%"

pause
