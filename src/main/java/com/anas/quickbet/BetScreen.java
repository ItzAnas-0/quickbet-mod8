package com.anas.quickbet;

import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;

import java.util.List;

public class BetScreen extends Screen {
    // Edit this list to add, remove, or reorder preset amounts (lowest to highest)
    private static final List<Long> PRESETS = List.of(
        100_000L, 500_000L, 1_000_000L, 5_000_000L
    );

    public BetScreen() {
        super(Component.literal("Quick Bet"));
    }

    @Override
    protected void init() {
        int startY = this.height / 2 - (PRESETS.size() * 24) / 2;
        int i = 0;
        for (Long amount : PRESETS) {
            final long amt = amount;
            this.addRenderableWidget(Button.builder(
                Component.literal(formatAmount(amt)),
                button -> sendBet(amt)
            ).bounds(this.width / 2 - 75, startY + i * 24, 150, 20).build());
            i++;
        }

        this.addRenderableWidget(Button.builder(
            Component.literal("Cancel"),
            button -> this.onClose()
        ).bounds(this.width / 2 - 75, startY + i * 24 + 10, 150, 20).build());
    }

    private void sendBet(long amount) {
        LocalPlayer player = this.minecraft.player;
        if (player != null) {
            player.connection.sendCommand("ch create " + amount);
        }
        this.onClose();
    }

    private String formatAmount(long amount) {
        if (amount >= 1_000_000) return (amount / 1_000_000) + "M";
        if (amount >= 1_000) return (amount / 1_000) + "K";
        return String.valueOf(amount);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
