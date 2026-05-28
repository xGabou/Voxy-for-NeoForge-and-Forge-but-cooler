package me.cortex.voxy.client.compat;

// TODO: Re-enable Flashback integration when NeoForge 1.21.1 version available
// import com.moulberry.flashback.Flashback;
// import com.moulberry.flashback.playback.ReplayServer;
// import com.moulberry.flashback.record.FlashbackMeta;
import me.cortex.voxy.common.Logger;
import me.cortex.voxy.common.config.section.SectionStorageConfig;
// import net.neoforged.fml.ModList;

import java.nio.file.Path;

public class FlashbackCompat {
    // Flashback integration is disabled in this build.
    public static final boolean FLASHBACK_INSTALLED = false;

    public static Path getReplayStoragePath() {
        // Stubbed out while Flashback integration is disabled.
        return null;
        /*
        if (!FLASHBACK_INSTALLED) {
            return null;
        }
        return getReplayStoragePath0();
        */
    }

    /*
    private static Path getReplayStoragePath0() {
        ReplayServer replayServer = Flashback.getReplayServer();
        if (replayServer != null) {
            FlashbackMeta meta = replayServer.getMetadata();
            if (meta != null) {
                var path = ((IFlashbackMeta)meta).getVoxyPath();
                if (path != null) {
                    Logger.info("Flashback replay server exists and meta exists");
                    if (path.exists()) {
                        Logger.info("Flashback voxy path exists in filesystem, using this as lod data source");
                        return path.toPath();
                    } else {
                        Logger.warn("Flashback meta had voxy path saved but path doesnt exist");
                    }
                }
            }
        }
        return null;
    }
    */
}
