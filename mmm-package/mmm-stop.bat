@echo off
chcp 65001
set port=14569
for /f "tokens=1-5" %%i in ('netstat -ano^|findstr ":%port%"') do (
    echo kill the process %%m who use the port %port%
    echo 正在关闭,请等待 %%m
    taskkill /f /pid %%m
)
Pause