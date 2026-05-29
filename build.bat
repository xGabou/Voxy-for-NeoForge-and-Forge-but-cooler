@echo off
setlocal EnableExtensions EnableDelayedExpansion

pushd "%~dp0" || exit /b 1

set "MC_VERSION="
set "REQUIRED_JAVA="
set "JAVA_HOME_FOUND="

if exist "gradle.properties" (
  for /f "usebackq tokens=1,* delims==" %%A in ("gradle.properties") do (
    if /i "%%A"=="minecraft_version" set "MC_VERSION=%%B"
  )
)

if "%MC_VERSION%"=="1.21.1" set "REQUIRED_JAVA=21"
if "%MC_VERSION%"=="1.20.1" set "REQUIRED_JAVA=17"

if not defined REQUIRED_JAVA (
  echo Could not detect a supported Minecraft version from gradle.properties.
  echo Found minecraft_version=%MC_VERSION%
  echo Supported versions are 1.21.1 and 1.20.1.
  popd
  pause
  exit /b 1
)

echo Detected Minecraft version: %MC_VERSION%
echo Required Java version: %REQUIRED_JAVA%

if defined JAVA_HOME (
  call :try_java_home "%JAVA_HOME%"
)

if not defined JAVA_HOME_FOUND (
  if exist "C:\Program Files\Eclipse Adoptium" (
    for /d %%D in ("C:\Program Files\Eclipse Adoptium\*") do (
      if not defined JAVA_HOME_FOUND call :try_java_home "%%D"
    )
  )
)

:ask_java
if not defined JAVA_HOME_FOUND (
  echo.
  echo Java %REQUIRED_JAVA% was not found.
  echo.
  echo Type the path to your Java installation.
  echo It can be either:
  echo   C:\Program Files\Eclipse Adoptium\jdk-%REQUIRED_JAVA%...
  echo or:
  echo   C:\Program Files\Eclipse Adoptium\jdk-%REQUIRED_JAVA%...\bin\java.exe
  echo.
  echo Type D if you want to open the Java download page instead.
  echo Type Q to quit.
  echo.

  set "USER_JAVA="
  set /p "USER_JAVA=Your choice: "

  set "USER_JAVA=!USER_JAVA:"=!"

  if /i "!USER_JAVA!"=="Q" (
    popd
    exit /b 1
  )

  if /i "!USER_JAVA!"=="D" (
    start "" "https://adoptium.net/temurin/releases/?os=windows&arch=x64&package=jdk&version=%REQUIRED_JAVA%"
    echo.
    echo Install Java %REQUIRED_JAVA%, then run this file again.
    popd
    pause
    exit /b 1
  )

  if exist "!USER_JAVA!\bin\java.exe" (
    call :try_java_home "!USER_JAVA!"
  ) else (
    if exist "!USER_JAVA!" (
      call :try_java_exe "!USER_JAVA!"
    )
  )

  if not defined JAVA_HOME_FOUND (
    echo.
    echo The provided Java path is not Java %REQUIRED_JAVA%.
    goto ask_java
  )
)

set "JAVA_HOME=%JAVA_HOME_FOUND%"
set "PATH=%JAVA_HOME%\bin;%PATH%"

echo.
echo Using JAVA_HOME=%JAVA_HOME%
echo.

call gradlew.bat build
if errorlevel 1 goto build_failed

start "" explorer "%CD%\build\libs"
popd
exit /b 0

:build_failed
set "BUILD_ERROR=%ERRORLEVEL%"
popd
exit /b %BUILD_ERROR%

:try_java_home
set "TEST_HOME=%~1"

if "%TEST_HOME%"=="" exit /b 0
if not exist "%TEST_HOME%\bin\java.exe" exit /b 0

call :try_java_exe "%TEST_HOME%\bin\java.exe"
exit /b 0

:try_java_exe
set "TEST_JAVA=%~1"
set "JAVA_VERSION="
set "JAVA_MAJOR="

if "%TEST_JAVA%"=="" exit /b 0
if not exist "%TEST_JAVA%" exit /b 0

for /f tokens^=2^ delims^=^" %%V in ('"%TEST_JAVA%" -version 2^>^&1') do (
  set "JAVA_VERSION=%%V"
  goto parse_java_version
)

exit /b 0

:parse_java_version
for /f "tokens=1 delims=." %%M in ("%JAVA_VERSION%") do set "JAVA_MAJOR=%%M"

if "%JAVA_MAJOR%"=="%REQUIRED_JAVA%" (
  for %%P in ("%TEST_JAVA%\..\..") do set "JAVA_HOME_FOUND=%%~fP"
)

exit /b 0