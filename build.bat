@echo off
setlocal EnableExtensions

pushd "%~dp0" || exit /b 1

set "MC_VERSION="
set "REQUIRED_JAVA="

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

for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$ErrorActionPreference = 'Stop'; ^
   $required = [int]$env:REQUIRED_JAVA; ^
   function Get-Major([string]$javaExe) { ^
     if ([string]::IsNullOrWhiteSpace($javaExe)) { return $null } ^
     if (-not (Test-Path $javaExe)) { return $null } ^
     $lines = & $javaExe -version 2>&1; ^
     if ($LASTEXITCODE -ne 0) { return $null } ^
     $text = [string]::Join([Environment]::NewLine, $lines); ^
     if ($text -match '\"(?<ver>\d+)') { return [int]$Matches.ver } ^
     return $null ^
   }; ^
   function Test-Home([string]$home) { ^
     if ([string]::IsNullOrWhiteSpace($home)) { return $null } ^
     if (-not (Test-Path $home)) { return $null } ^
     $javaExe = Join-Path $home 'bin\java.exe'; ^
     if ((Get-Major $javaExe) -eq $required) { return (Resolve-Path $home).Path } ^
     return $null ^
   }; ^
   function Find-Home { ^
     $found = Test-Home $env:JAVA_HOME; ^
     if ($found) { return $found } ^
     $root = 'C:\Program Files\Eclipse Adoptium'; ^
     if (Test-Path $root) { ^
       foreach ($child in Get-ChildItem $root -Directory -ErrorAction SilentlyContinue) { ^
         $found = Test-Home $child.FullName; ^
         if ($found) { return $found } ^
       } ^
     } ^
     return $null ^
   }; ^
   $home = Find-Home; ^
   if ($home) { [Console]::Write($home) }"`) do set "JAVA_HOME=%%I"

:ask_java
if not defined JAVA_HOME (
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
  set /p "USER_JAVA=Your choice: "

  if /i "%USER_JAVA%"=="Q" (
    popd
    exit /b 1
  )

  if /i "%USER_JAVA%"=="D" (
    start "" "https://adoptium.net/temurin/releases/?os=windows&arch=x64&package=jdk&version=%REQUIRED_JAVA%"
    echo.
    echo Install Java %REQUIRED_JAVA%, then run this file again.
    popd
    pause
    exit /b 1
  )

  for /f "usebackq delims=" %%I in (`powershell -NoProfile -ExecutionPolicy Bypass -Command ^
    "$ErrorActionPreference = 'Stop'; ^
     $required = [int]$env:REQUIRED_JAVA; ^
     $inputPath = $env:USER_JAVA; ^
     function Get-Major([string]$javaExe) { ^
       if ([string]::IsNullOrWhiteSpace($javaExe)) { return $null } ^
       if (-not (Test-Path $javaExe)) { return $null } ^
       $lines = & $javaExe -version 2>&1; ^
       if ($LASTEXITCODE -ne 0) { return $null } ^
       $text = [string]::Join([Environment]::NewLine, $lines); ^
       if ($text -match '\"(?<ver>\d+)') { return [int]$Matches.ver } ^
       return $null ^
     }; ^
     function Test-Home([string]$home) { ^
       if ([string]::IsNullOrWhiteSpace($home)) { return $null } ^
       if (-not (Test-Path $home)) { return $null } ^
       $javaExe = Join-Path $home 'bin\java.exe'; ^
       if ((Get-Major $javaExe) -eq $required) { return (Resolve-Path $home).Path } ^
       return $null ^
     }; ^
     if ($inputPath.EndsWith('java.exe')) { ^
       if ((Get-Major $inputPath) -eq $required) { ^
         [Console]::Write((Resolve-Path (Split-Path (Split-Path $inputPath -Parent) -Parent)).Path) ^
       } ^
     } else { ^
       $home = Test-Home $inputPath; ^
       if ($home) { [Console]::Write($home) } ^
     }"`) do set "JAVA_HOME=%%I"

  if not defined JAVA_HOME (
    echo.
    echo The provided Java path is not Java %REQUIRED_JAVA%.
    goto ask_java
  )
)

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