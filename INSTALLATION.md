# How to build Voxy from GitHub on Windows

## 1. Choose the correct branch

Before downloading the project, make sure you are using the correct version.

For Minecraft 1.21.1 NeoForge, use this page:

https://github.com/xGabou/Voxy-for-NeoForge-and-Forge-but-cooler

For Minecraft 1.20.1 Forge, use this branch:

https://github.com/xGabou/Voxy-for-NeoForge-and-Forge-but-cooler/tree/forge-1.20.1

Be careful to select the right branch before downloading the ZIP.

## 2. Download the project

On the GitHub page, click the green **Code** button, then click **Download ZIP**.

After the download is finished, extract the ZIP somewhere on your computer.

## 3. Run the build script

Open the extracted folder and run:

```cmd
cmd.exe /c build.bat
````

The script will detect the Minecraft version from the project files and choose the required Java version automatically.

For example, for Minecraft 1.21.1, it should detect Java 21:

```cmd
Detected Minecraft version: 1.21.1
Required Java version: 21
```

For Minecraft 1.20.1, it should detect Java 17.

## 4. Java detection

The script will try to find the correct Java installation automatically.

If Java is found, you should see something like this:

```cmd
Using JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot
```

If Java is not found, the script may ask you for the path to your Java installation.

You can enter either the Java folder:

```cmd
C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot
```

Or the `java.exe` file:

```cmd
C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot\bin\java.exe
```

If you do not have the correct Java version installed, the script can open the Java download page for you.

## 5. Wait for the build to finish

When the build works correctly, you should eventually see:

```cmd
BUILD SUCCESSFUL
```

Example output:

```cmd
cmd.exe /c build.bat
Detected Minecraft version: 1.21.1
Required Java version: 21

Using JAVA_HOME=C:\Program Files\Eclipse Adoptium\jdk-21.0.8.9-hotspot

> Task :build

BUILD SUCCESSFUL in 10s
31 actionable tasks: 3 executed, 28 up-to-date

Process finished with exit code 0
```

Warnings about deprecated Gradle features can usually be ignored as long as the build says `BUILD SUCCESSFUL`.

## 6. Install the built mod

After the build finishes, a Windows Explorer window should open automatically in the output folder.

Select the normal version `.jar` file.

Do not select the `thin` jar.

Copy the correct `.jar` file into your modpack `mods` folder.

Then launch your modpack.
