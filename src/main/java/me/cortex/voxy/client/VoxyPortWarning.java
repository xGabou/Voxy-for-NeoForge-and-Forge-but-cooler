package me.cortex.voxy.client;

import net.minecraft.client.Minecraft;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public final class VoxyPortWarning {
    private static final String ACK_FILE_NAME = "voxy-port-warning-accepted.txt";
    private static final URI DISCORD_URI = URI.create("https://discord.gg/2jRhTJgYz4");
    private static final URI GITHUB_URI = URI.create("https://github.com/xGabou/Voxy-but-cooler/issues");

    private VoxyPortWarning() {
    }

    public static boolean isAcknowledged(Minecraft minecraft) {
        return Files.exists(getAckFile(minecraft));
    }

    public static void acknowledge(Minecraft minecraft) {
        Path file = getAckFile(minecraft);
        try {
            Files.createDirectories(file.getParent());
            Files.writeString(file, "I UNDERSTAND", StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Failed to persist Voxy warning acknowledgement", e);
        }
    }

    public static URI discordUri() {
        return DISCORD_URI;
    }

    public static URI githubUri() {
        return GITHUB_URI;
    }

    private static Path getAckFile(Minecraft minecraft) {
        return minecraft.gameDirectory.toPath().resolve(".voxy").resolve(ACK_FILE_NAME);
    }
}
