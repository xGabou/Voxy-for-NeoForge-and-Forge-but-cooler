# Voxy NeoForge Project Notes

## Scope

This repository is the NeoForge 1.21.1 build of Voxy.

## Work Rules

1. Inspect the current implementation before changing it.
2. Prefer verified references over memory or guesswork.
3. Keep changes minimal and buildable.
4. Run `./gradlew build` after meaningful changes.
5. Do not reintroduce legacy metadata, access wideners, or other removed build artifacts.

## Important Files

- `build.gradle` - build configuration and dependency wiring
- `gradle.properties` - version and project properties
- `settings.gradle` - Gradle bootstrap
- `src/main/resources/META-INF/neoforge.mods.toml` - mod metadata
- `src/main/resources/META-INF/accesstransformer.cfg` - access transformer rules

## Source Layout

- `src/main/java/me/cortex/voxy/client/**` - client code
- `src/main/java/me/cortex/voxy/common/**` - shared code
- `src/main/java/me/cortex/voxy/commonImpl/**` - implementation details

## Build Notes

- Use Java 21.
- Generated assets go in `src/generated/resources/`.
- Optional integrations may stay excluded from the default build until they are ported or verified.
