# Voxy NeoForge 1.21.1

Voxy is a level-of-detail terrain rendering mod for Minecraft. This repository contains the NeoForge 1.21.1 build.

## Requirements

### Required

| Dependency | Version |
|------------|---------|
| Minecraft | 1.21.1 |
| NeoForge | 21.1.x |
| Sodium | mc1.21.1-0.6.13-neoforge |

### Recommended

| Dependency | Purpose |
|------------|---------|
| Lithium | General performance improvements |
| Reese's Sodium Options | Better Sodium settings UI |
| Sodium Options API | Additional Sodium configuration hooks |

## Building

```bash
git clone https://github.com/j-shelfwood/voxy-neoforge.git
cd voxy-neoforge
./gradlew build
```

The built JAR will be in `build/libs/`.

## Notes

- Some optional integrations are excluded from the default build.
- Generated resources are written to `src/generated/resources/`.

## License

The original Voxy project is licensed by its author. Review the upstream terms before redistributing any builds.
