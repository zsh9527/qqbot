@echo off
setlocal enabledelayedexpansion
chcp 65001

cls
title 更新授权码
color 0A

echo.
echo ==============================
echo 请输入授权码
echo ==============================
echo.

:CHOICE
set /p license=
set trimLicense=!license: =!

::创建配置文件内容
set temp_file=%~dp0config\application-prod.properties_temp
set application_file=%~dp0config\application-prod.properties
(
echo license.code = %trimLicense%
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