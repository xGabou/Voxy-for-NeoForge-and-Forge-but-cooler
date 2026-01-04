# Voxy NeoForge 1.21.1

**Voxy** is a Level-of-Detail (LOD) rendering mod for Minecraft that extends your view distance far beyond vanilla limits.

This is an **unofficial NeoForge port** of the [original Voxy mod](https://github.com/MCRcortex/voxy) (Fabric) for Minecraft 1.21.1.

## Status

**Alpha** - Functional with known limitations.

### Working Features
- LOD terrain rendering beyond vanilla render distance
- Smooth transitions between LOD and vanilla chunks
- Fog integration (disabled at LOD boundaries)
- Block model baking for all render types (solid, cutout, cutout_mipped, translucent)
- Delayed chunk unloading to prevent pop-out effects

### Current Limitations
- Requires Sodium 0.6.13+ (NeoForge version)
- Some optional integrations not yet ported (Iris, Nvidium, Vivecraft)
- Debug screen integration disabled (MC 1.21.1 API changes)

## Requirements

- Minecraft 1.21.1
- NeoForge 21.1.x
- [Sodium](https://modrinth.com/mod/sodium) mc1.21.1-0.6.13-neoforge or later
- [Forgified Fabric API](https://modrinth.com/mod/forgified-fabric-api) for 1.21.1

## Installation

1. Install NeoForge for Minecraft 1.21.1
2. Install Sodium (NeoForge version)
3. Install Forgified Fabric API
4. Drop the Voxy JAR into your `mods` folder

## Building

```bash
./gradlew build
```

The built JAR will be in `build/libs/`.

## Development


### Validation Scripts

The `scripts/` directory contains validation tools:
- `validate_mixin_config.py` - Validates mixin registration and exclusions
- `validate_mixin_targets.py` - Validates mixin target signatures
- `validate_mixins.sh` - Post-build mixin verification
- `setup_reference_sources.sh` - Sets up reference sources for development

## Credits

- Original Voxy mod by [MCRcortex](https://github.com/MCRcortex/voxy)
- NeoForge port maintained by the community

## License

See [LICENSE.md](LICENSE.md)
