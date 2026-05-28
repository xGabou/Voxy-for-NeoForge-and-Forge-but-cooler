package me.cortex.voxy.client.core.util;

import me.cortex.voxy.client.core.VoxyRenderSystem;
import me.cortex.voxy.client.core.rendering.Viewport;
import net.caffeinemc.mods.sodium.client.render.chunk.ChunkRenderMatrices;
import net.neoforged.fml.ModList;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public final class IrisUtil {
    private IrisUtil() {
    }

    public record CapturedViewportParameters(ChunkRenderMatrices matrices, double x, double y, double z) {
        public Viewport<?> apply(VoxyRenderSystem vrs) {
            return vrs.setupViewport(this.matrices, this.x, this.y, this.z);
        }
    }

    public static CapturedViewportParameters CAPTURED_VIEWPORT_PARAMETERS;

    public static final boolean IRIS_INSTALLED = ModList.get().isLoaded("iris");
    public static final boolean SHADER_SUPPORT = true;

    public static boolean irisShadowActive() {
        if (!IRIS_INSTALLED) {
            return false;
        }
        try {
            Class<?> shadowRenderer = Class.forName("net.irisshaders.iris.shadows.ShadowRenderer");
            Field active = shadowRenderer.getField("ACTIVE");
            return active.getBoolean(null);
        } catch (ReflectiveOperationException | LinkageError e) {
            return false;
        }
    }

    public static void clearIrisSamplers() {
        if (!IRIS_INSTALLED) {
            return;
        }
        try {
            Class<?> irisRenderSystem = Class.forName("net.irisshaders.iris.gl.IrisRenderSystem");
            Method bindSampler = irisRenderSystem.getMethod("bindSamplerToUnit", int.class, int.class);
            for (int i = 0; i < 16; i++) {
                bindSampler.invoke(null, i, 0);
            }
        } catch (ReflectiveOperationException | LinkageError ignored) {
        }
    }

    public static void reload() {
        if (!IRIS_INSTALLED) {
            return;
        }
        try {
            Class<?> iris = Class.forName("net.irisshaders.iris.Iris");
            Class<?> irisApi = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            Method getInstance = irisApi.getMethod("getInstance");
            Object api = getInstance.invoke(null);
            Method getConfig = api.getClass().getMethod("getConfig");
            Object config = getConfig.invoke(api);
            Method areShadersEnabled = config.getClass().getMethod("areShadersEnabled");
            Method isShaderPackInUse = api.getClass().getMethod("isShaderPackInUse");

            boolean shadersEnabled = (boolean) areShadersEnabled.invoke(config);
            boolean shaderPackInUse = (boolean) isShaderPackInUse.invoke(api);
            if (shaderPackInUse || shadersEnabled) {
                iris.getMethod("reload").invoke(null);
            }
        } catch (ReflectiveOperationException | LinkageError e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean irisShaderPackEnabled() {
        if (!IRIS_INSTALLED) {
            return false;
        }
        return getCurrentPackPresent();
    }

    public static boolean irisShadersEnabledInConfig() {
        if (!IRIS_INSTALLED) {
            return false;
        }
        return getCurrentPackPresent();
    }

    public static void disableIrisShaders() {
        if (!IRIS_INSTALLED) {
            return;
        }
        try {
            Class<?> irisApi = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
            Method getInstance = irisApi.getMethod("getInstance");
            Object api = getInstance.invoke(null);
            Method getConfig = api.getClass().getMethod("getConfig");
            Object config = getConfig.invoke(api);
            Method setShadersEnabledAndApply = config.getClass().getMethod("setShadersEnabledAndApply", boolean.class);
            setShadersEnabledAndApply.invoke(config, false);
        } catch (ReflectiveOperationException | LinkageError e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean getCurrentPackPresent() {
        try {
            Class<?> iris = Class.forName("net.irisshaders.iris.Iris");
            Method getCurrentPack = iris.getMethod("getCurrentPack");
            Object pack = getCurrentPack.invoke(null);
            if (pack instanceof Optional<?> optional) {
                return optional.isPresent();
            }
            return pack != null;
        } catch (ReflectiveOperationException | LinkageError e) {
            return false;
        }
    }
}
