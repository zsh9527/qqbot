@echo off
setlocal enabledelayedexpansion

cls
title 初始化QQ机器人
color 0A

::创建目录
if not exist "%~dp0config" mkdir "%~dp0config"
if not exist "%~dp0device" mkdir "%~dp0device"
if not exist "%~dp0cache" mkdir "%~dp0cache"

echo.
echo ==============================
echo 请按照提示输入配置信息
echo ==============================
echo.

set /p qqUsername="请输入qq号:"
set /p qqPassword="请输入qq密码(目前使用扫码登录, 密码不用输入):"
set /p qqBroQQ="输入骚扰qq号:"
set /p qqBroContent="输入发送的骚扰内容:"
set /p qqGroups="输入管理群的qq号:"

::创建配置文件内容
set temp_file=%~dp0config\application-prod.yml_temp
set application_file=%~dp0config\application-prod.yml
(
echo file:
echo     config:
echo         log-dir:  %~dp0logs
echo         qq-device-dir: %~dp0device\
echo         qq-cache-dir: %~dp0cache
echo qq:
echo     config:
echo         username: %qqUsername%
echo         password: %qqPassword%
echo         broQQ: %qqBroQQ%
echo         broContent: %qqBroContent%
echo         groups: %qqGroups%
) > %temp_file%

echo.
echo 生成的配置文件内容如下
echo.
type %temp_file%
echo.
set /p confirm="是否保存该配置文件? (Y/N)"
if /i "%confirm%"=="n" (
    DEL %temp_file%
    echo.
    echo 取消操作
    echo.
) else (
    move %temp_file% %application_file% >nul 2>&1
    echo.
    echo 配置内容已保存%application_file%
    echo.
)
pause