package me.cortex.voxy.client.mixin.minecraft;


// MC 1.21.1: ShaderSource and ShaderType classes moved/removed
// import com.mojang.blaze3d.shaders.ShaderSource;
// import com.mojang.blaze3d.shaders.ShaderType;
import com.mojang.blaze3d.systems.RenderSystem;
import me.cortex.voxy.client.VoxyClient;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.BiFunction;

//Thanks iris for making me need todo this ;-; _irritater_
@Mixin(RenderSystem.class)
public class MixinRenderSystem {
    //We need to inject before iris to initalize our systems
    // MC 1.21.1: initRenderer signature changed - ShaderSource parameter removed
    // TODO: Verify correct initRenderer signature for MC 1.21.1
    @Inject(method = "initRenderer", order = 900, remap = false, at = @At("RETURN"))
    private static void voxy$injectInit(long windowHandle, int debugVerbosity, boolean sync, boolean renderDebugLabels, CallbackInfo ci) {
        VoxyClient.initVoxyClient();
    }
}
