package me.cortex.voxy.client.mixin.minecraft;

import me.cortex.voxy.client.VoxyPortWarning;
import me.cortex.voxy.client.VoxyClientInstance;
import me.cortex.voxy.commonImpl.VoxyCommon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import me.cortex.voxy.client.screen.VoxyWarningScreen;

@Mixin(Minecraft.class)
public class MixinMinecraft {
    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    private void voxy$gateTitleScreen(Screen screen, CallbackInfo ci) {
        Minecraft minecraft = (Minecraft) (Object) this;
        if (screen instanceof TitleScreen && !VoxyPortWarning.isAcknowledged(minecraft)) {
            minecraft.setScreen(new VoxyWarningScreen());
            ci.cancel();
        }
    }

    @Inject(method = "disconnect", at = @At("TAIL"))
    private void voxy$injectWorldClose(CallbackInfo ci) {
        if (VoxyCommon.isAvailable() && VoxyClientInstance.isInGame) {
            VoxyCommon.shutdownInstance();
            VoxyClientInstance.isInGame = false;
        }
    }

    /*
    @Inject(method = "joinWorld", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;setWorld(Lnet/minecraft/client/world/ClientWorld;)V", shift = At.Shift.BEFORE))
    private void voxy$injectInitialization(ClientWorld world, DownloadingTerrainScreen.WorldEntryReason worldEntryReason, CallbackInfo ci) {
        if (VoxyConfig.CONFIG.enabled) {
            VoxyCommon.createInstance();
        }
    }*/
}
