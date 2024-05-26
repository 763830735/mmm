@echo off
set "KEY_NAME=HKEY_CURRENT_USER\Software\mmm"
set "VALUE_NAME=dir-path"
set "VALUE_DATA=%~dp0"
reg add %KEY_NAME% /v %VALUE_NAME% /t REG_SZ /d %VALUE_DATA% /f

for /f "tokens=2*" %%i in ('reg query "HKEY_CURRENT_USER\Software\mmm" /v "dir-path" 2^>nul') do (
    set "registryValue=%%j"
)

cd /d "%registryValue%"
echo %cd%

rem set "combinedPath=%registryValue%\jdk-21.0.2\bin"
rem echo %combinedPath%

set combinedPath=.\jdk-21.0.2\bin

START "mmm" "%combinedPath%\javaw" -jar "%registryValue%\mmm-0.0.1-SNAPSHOT-7.jar"