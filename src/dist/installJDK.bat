@echo off

cls
title ��װJDK17
color 0A

echo.
echo ==============================
echo ��ʼ��װJDK17
echo ==============================
echo.

::�ж��Ƿ��Ѱ�װ
call :CHECKJDK
if %errorlevel% == 0 (
    echo.
    echo JDK �Ѱ�װ, ������װ����
    echo.
    goto :END
)

::���ð�װ·��
set install_path=C:\Program Files\Java
set script_path=%~dp0
set file_name=jdk-17.0.9
set copy_jdk_path=%script_path%%file_name%

:: ��鰲װĿ¼�Ƿ���ڣ��������򴴽�
if not exist "%install_path%\" (
    mkdir "%install_path%"
)
if not exist "%install_path%\%file_name%\" (
    mkdir "%install_path%\%file_name%"
)

::��װ
xcopy "%copy_jdk_path%" "%install_path%\%file_name%\" /S /Y > NUL
if %errorlevel% NEQ 0 (
    set fail_message=��װJDKʧ��
    goto :FAIL
) else (
    echo.
    echo ��װJDK�ɹ����������û�������
    echo.
)

::���û�������
call :SETENV
if %errorlevel% NEQ 0 (
    set fail_message=���û�������ʧ��
    goto :FAIL
) else (
    echo.
    echo ���û��������ɹ�����������
    echo.
)
::����Ƿ�װ�ɹ�
call :CHECKJDK
if %errorlevel% NEQ 0 (
    set fail_message=java��װʧ��
    goto :FAIL
) else (
    echo.
    echo ��װ�ɹ�
    echo.
    goto :END
)

::���û�������
:SETENV
setx PATH "%install_path%\%file_name%\bin;%path%"
set PATH=%install_path%\%file_name%\bin;%path%
exit /b %errorlevel%

:: ����Ƿ�װ JDK
:CHECKJDK
java -version >nul 2>&1
exit /b %errorlevel%

:: ��װʧ��
:FAIL
echo.
echo ��װʧ��: %fail_message%
echo.

:END
pause