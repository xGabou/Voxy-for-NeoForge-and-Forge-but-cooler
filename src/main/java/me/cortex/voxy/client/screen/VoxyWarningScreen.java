package me.cortex.voxy.client.screen;

import me.cortex.voxy.client.VoxyPortWarning;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.FormattedCharSequence;

import java.util.List;

public class VoxyWarningScreen extends Screen {
    private static final Component TITLE = Component.literal("Voxy Port Warning");
    private static final Component INTRO = Component.literal("You're using an unofficial build of Voxy. All credits for the mod go to MCRcortex. This port was made by Gabou.");
    private static final Component DISCORD = Component.literal("Join the Discord server for updates or issues: https://discord.gg/2jRhTJgYz4");
    private static final Component SUPPORT = Component.literal("Report issues, crashes, bug reports, and compatibility problems on the GitHub issues page or in the Discord server. Do not use the original Voxy server for port issues.");
    private static final Component WARNING = Component.literal("YOU ARE HEREBY WARNED.");
    private static final Component INPUT_PROMPT = Component.literal("Type I UNDERSTAND to continue.");
    private static final Component REQUIRED_TEXT = Component.literal("I UNDERSTAND");
    private static final int LOCK_TIME_MS = 5_000;

    private final long openedAt = Util.getMillis();
    private EditBox acknowledgementBox;
    private Button continueButton;
    private Component statusMessage = Component.empty();

    public VoxyWarningScreen() {
        super(TITLE);
    }

    @Override
    protected void init() {
        int contentWidth = Math.min(520, this.width - 48);
        int left = (this.width - contentWidth) / 2;
        int boxWidth = Math.min(360, contentWidth);
        int boxLeft = (this.width - boxWidth) / 2;
        int baseY = this.height - 74;

        this.acknowledgementBox = new EditBox(this.font, boxLeft, baseY - 28, boxWidth, 20, Component.literal("Acknowledgement"));
        this.acknowledgementBox.setMaxLength(32);
        this.acknowledgementBox.setValue("");
        this.acknowledgementBox.setResponder(value -> this.updateContinueState());
        this.acknowledgementBox.setEditable(this.isUnlocked());
        this.addRenderableWidget(this.acknowledgementBox);

        this.continueButton = this.addRenderableWidget(Button.builder(Component.literal("Continue"), button -> this.acceptWarning())
            .bounds((this.width - 160) / 2, baseY, 160, 20)
            .build());

        this.addRenderableWidget(Button.builder(Component.literal("Open Discord"), button -> {
            Util.getPlatform().openUri(VoxyPortWarning.discordUri());
            this.statusMessage = Component.literal("Opened Discord in your browser.");
        }).bounds(left, this.height - 44, 160, 20).build());

        this.addRenderableWidget(Button.builder(Component.literal("Open GitHub"), button -> {
            Util.getPlatform().openUri(VoxyPortWarning.githubUri());
            this.statusMessage = Component.literal("Opened GitHub issues in your browser.");
        }).bounds(left + contentWidth - 160, this.height - 44, 160, 20).build());

        this.updateContinueState();
        if (this.isUnlocked()) {
            this.setInitialFocus(this.acknowledgementBox);
        }
    }

    @Override
    public void tick() {
        super.tick();
        boolean unlocked = this.isUnlocked();
        if (this.acknowledgementBox != null) {
            this.acknowledgementBox.setEditable(unlocked);
        }
        this.updateContinueState();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }

    @Override
    public void onClose() {
        // Intentionally blocked until acknowledged.
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        guiGraphics.fillGradient(0, 0, this.width, this.height, 0xFF1B0B0E, 0xFF081018);

        int contentWidth = Math.min(520, this.width - 48);
        int contentLeft = (this.width - contentWidth) / 2;
        int panelTop = 24;
        int panelBottom = this.height - 16;

        guiGraphics.fill(contentLeft - 12, panelTop - 12, contentLeft + contentWidth + 12, panelBottom, 0xB0141C24);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, panelTop, 0xFFF4F4F4);

        int y = panelTop + 24;
        y = drawWrapped(guiGraphics, INTRO, contentLeft, y, contentWidth, 0xFFF1D7D7) + 8;
        y = drawWrapped(guiGraphics, DISCORD, contentLeft, y, contentWidth, 0xFFCDE7FF) + 8;
        y = drawWrapped(guiGraphics, SUPPORT, contentLeft, y, contentWidth, 0xFFF1D7D7) + 8;
        y = drawWrapped(guiGraphics, WARNING, contentLeft, y, contentWidth, 0xFFFFC5C5) + 10;

        int remainingMs = Math.max(0, LOCK_TIME_MS - (int) (Util.getMillis() - this.openedAt));
        int remainingSeconds = (remainingMs + 999) / 1000;
        Component countdown = remainingSeconds > 0
            ? Component.literal("You cannot close this screen for " + remainingSeconds + " second" + (remainingSeconds == 1 ? "" : "s") + ".")
            : Component.literal("The screen is unlocked. Type I UNDERSTAND to continue.");
        guiGraphics.drawCenteredString(this.font, countdown, this.width / 2, y, 0xFFF8E48A);
        guiGraphics.drawCenteredString(this.font, INPUT_PROMPT, this.width / 2, y + 12, 0xFFF4F4F4);

        if (!this.statusMessage.getString().isBlank()) {
            guiGraphics.drawCenteredString(this.font, this.statusMessage, this.width / 2, this.height - 60, 0xFFACF59B);
        }

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private boolean isUnlocked() {
        return Util.getMillis() - this.openedAt >= LOCK_TIME_MS;
    }

    private void updateContinueState() {
        boolean ready = this.isUnlocked() && this.acknowledgementBox != null && REQUIRED_TEXT.getString().equals(this.acknowledgementBox.getValue().trim());
        if (this.continueButton != null) {
            this.continueButton.active = ready;
        }
    }

    private void acceptWarning() {
        if (this.minecraft == null || this.acknowledgementBox == null) {
            return;
        }

        if (!this.isUnlocked()) {
            this.statusMessage = Component.literal("Wait until the timer ends.");
            return;
        }

        if (!REQUIRED_TEXT.getString().equals(this.acknowledgementBox.getValue().trim())) {
            this.statusMessage = Component.literal("Type I UNDERSTAND exactly.");
            return;
        }

        VoxyPortWarning.acknowledge(this.minecraft);
        this.minecraft.setScreen(new TitleScreen());
    }

    private int drawWrapped(GuiGraphics guiGraphics, Component text, int left, int top, int width, int color) {
        List<FormattedCharSequence> lines = this.font.split(text, width);
        int y = top;
        for (FormattedCharSequence line : lines) {
            guiGraphics.drawString(this.font, line, left, y, color, false);
            y += 10;
        }
        return y;
    }
}
