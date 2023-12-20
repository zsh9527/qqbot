@echo off
setlocal enabledelayedexpansion

cls
title ��ʼ��QQ������
color 0A

::����Ŀ¼
if not exist "%~dp0config" mkdir "%~dp0config"
if not exist "%~dp0device" mkdir "%~dp0device"
if not exist "%~dp0cache" mkdir "%~dp0cache"

echo.
echo ==============================
echo �밴����ʾ����������Ϣ
echo ==============================
echo.

set /p qqUsername="������qq��:"
set /p qqPassword="������qq����(Ŀǰʹ��ɨ���¼, ���벻������):"
set /p qqBroQQ="����ɧ��qq��:"
set /p qqBroContent="���뷢�͵�ɧ������:"
set /p qqGroups="�������Ⱥ��qq��:"

::���������ļ�����
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
echo ���ɵ������ļ���������
echo.
type %temp_file%
echo.
set /p confirm="�Ƿ񱣴�������ļ�? (Y/N)"
if /i "%confirm%"=="n" (
    DEL %temp_file%
    echo.
    echo ȡ������
    echo.
) else (
    move %temp_file% %application_file% >nul 2>&1
    echo.
    echo ���������ѱ���%application_file%
    echo.
)
pause