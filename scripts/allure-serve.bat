@echo off
setlocal

cd /d "%~dp0.."

if not exist "target\site\allure-maven-plugin\index.html" (
    call mvn -q allure:report || exit /b 1
)

call .allure\bin\allure.bat open target\site\allure-maven-plugin
