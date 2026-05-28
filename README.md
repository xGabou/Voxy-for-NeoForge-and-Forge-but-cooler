# Voxy NeoForge and Forge 1.21.1

This is my attempt at making an actual NeoForge and Forge port (1.20.1) of Voxy for Minecraft 1.21.1.

The existing repo was useful as a starting point, but it still had Fabric leftovers and did not really work as a proper Forge or NeoForge port. This version is meant to clean that up and make something that actually launches and works.


https://github.com/j-shelfwood/voxy-neoforge

Since that version is highly vibe coded, this project is being made properly from the ground up.


## About

Voxy is a level of detail terrain rendering mod for Minecraft.

This repository focuses on a real NeoForge and Forge implementation.

## Requirements

### Required

| Dependency | Version                  |
| ---------- | ------------------------ |
| Minecraft  | 1.21.1                   |
| NeoForge   | 21.1.x                   |
| Sodium     | mc1.21.1 0.6.13 NeoForge |

### Recommended

| Dependency             | Purpose                               |
| ---------------------- | ------------------------------------- |
| Lithium                | General performance improvements      |
| Reese's Sodium Options | Better Sodium settings UI             |
| Sodium Options API     | Additional Sodium configuration hooks |

## Building

```bash
git clone https://github.com/xGabou/Cool-voxy.git
cd voxy-neoforge
./gradlew build
```

The built JAR will be in:

```bash
build/libs/
```

## License

The original Voxy project is licensed by its author.

Review the upstream license before redistributing any builds.
