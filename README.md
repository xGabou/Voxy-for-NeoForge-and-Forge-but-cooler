# Voxy NeoForge 1.21.1 and Forge 1.20.1

All credit for Voxy goes to [MCRcortex](https://github.com/MCRcortex), the original creator of the mod.

**Original repository:** [MCRcortex/voxy](https://github.com/MCRcortex/voxy)

**Original author:** [MCRcortex](https://github.com/MCRcortex)


This repository is my attempt at making an actual NeoForge and Forge port of Voxy for Minecraft 1.21.1.

The existing repo was useful as a starting point, but it still had Fabric leftovers and did not really work as a proper Forge or NeoForge port. This version is meant to clean that up and make something that actually launches and works.


https://github.com/j-shelfwood/voxy-neoforge

Since that version is highly vibe coded, this project is being made properly from the ground up.


## About

Voxy is a level of detail rendering mod for Minecraft that extends your view distance far beyond vanilla limits by rendering distant terrain at lower detail levels.

This repository focuses on a real NeoForge and Forge implementation.

## Why This Port?

You might wonder: "Why not just use the Fabric version with Sinytra Connector?"

| Aspect          | Native NeoForge and Forge Port             | Sinytra Connector                    |
| --------------- | ------------------------------------------ | ------------------------------------ |
| Performance     | No translation overhead                    | Runtime translation layer            |
| Mod Integration | Native Forge and NeoForge API calls        | Fabric API emulation through FFAPI   |
| Maintenance     | Must track upstream Voxy changes manually  | Can use the Fabric jar directly      |
| Stability       | Tested against Forge and NeoForge directly | May have edge cases from translation |
| Dependencies    | Forge or NeoForge dependencies only        | Connector and Forgified Fabric API   |

For a performance critical LOD mod like Voxy, avoiding the translation layer is worth it. If you prioritize simplicity and do not mind possible overhead or edge cases, Sinytra Connector is still a valid alternative.

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
git clone https://github.com/xGabou/Voxy-for-NeoForge-and-Forge-but-cooler.git
cd Voxy-for-NeoForge-and-Forge-but-cooler
./gradlew build
```

The built JAR will be in:

```bash
build/libs/
```

## Notes

This port is intended to remove Fabric specific leftovers such as `fabric.mod.json` and replace them with the proper Forge and NeoForge structure.

The goal is to make the mod launch, build, and behave like an actual Forge and NeoForge mod.

Some optional integrations may be excluded from the default build.

Generated resources may be written to:

```bash
src/generated/resources/
```

## License Notice

The original Voxy mod is licensed under All Rights Reserved by MCRcortex.

This port is provided for personal use. Please respect the original author's licensing terms and review the upstream license before redistributing any builds.
