package me.cortex.voxy.client.core.rendering.util;

import static org.lwjgl.opengl.GL33.glBindSampler;
import static org.lwjgl.opengl.GL45.glBindTextureUnit;

import net.minecraft.client.Minecraft;

public class LightMapHelper {
    public static void bind(int lightingIndex) {
        glBindSampler(lightingIndex, 0);
        // TODO: MC 1.21.1 - LightTexture.getTextureView() and Blaze3D GlTexture not accessible
        // Need mixin accessor for light texture GL ID
        throw new UnsupportedOperationException("Light texture GL ID access not yet implemented for MC 1.21.1");
        // glBindTextureUnit(lightingIndex, getLightTextureId());
    }
}