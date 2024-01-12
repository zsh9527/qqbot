@echo off
chcp 65001

cls
title 安装JDK17
color 0A

echo.
echo ==============================
echo 开始安装JDK17
echo ==============================
echo.

::判断是否已安装
call :CHECKJDK
if %errorlevel% == 0 (
    echo.
    echo JDK 已安装, 跳过安装步骤
    echo.
    goto :END
)

::设置安装路径
set install_path=C:\Program Files\Java
set script_path=%~dp0
set file_name=jdk-17.0.9
set copy_jdk_path=%script_path%%file_name%

:: 检查安装目录是否存在，不存在则创建
if not exist "%install_path%\" (
    mkdir "%install_path%"
)
if not exist "%install_path%\%file_name%\" (
    mkdir "%install_path%\%file_name%"
)

::安装
xcopy "%copy_jdk_path%" "%install_path%\%file_name%\" /S /Y > NUL
if %errorlevel% NEQ 0 (
    set fail_message=安装JDK失败
    goto :FAIL
) else (
    echo.
    echo 安装JDK成功，即将配置环境变量
    echo.
)

::设置环境变量
call :SETENV
if %errorlevel% NEQ 0 (
    set fail_message=配置环境变量失败
    goto :FAIL
) else (
    echo.
    echo 配置环境变量成功，即将测试
    echo.
)
::检查是否安装成功
call :CHECKJDK
if %errorlevel% NEQ 0 (
    set fail_message=java安装失败
    goto :FAIL
) else (
    echo.
    echo 安装成功
    echo.
    goto :END
)

::配置环境变量
:SETENV
setx PATH "%install_path%\%file_name%\bin;%path%"
set PATH=%install_path%\%file_name%\bin;%path%
exit /b %errorlevel%

:: 检查是否安装 JDK
:CHECKJDK
java -version >nul 2>&1
exit /b %errorlevel%

:: 安装失败
:FAIL
echo.
echo 安装失败: %fail_message%
echo.

:END
pause