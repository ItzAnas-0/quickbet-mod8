package com.anas.quickbet;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.resources.Identifier;
import org.lwjgl.glfw.GLFW;

public class QuickbetMod implements ClientModInitializer {
    private static KeyMapping openMenuKey;

    @Override
    public void onInitializeClient() {
        KeyMapping.Category category = KeyMapping.Category.register(
            Identifier.fromNamespaceAndPath("quickbet", "main")
        );

        openMenuKey = KeyMappingHelper.registerKeyMapping(new KeyMapping(
            "key.quickbet.open",
            InputConstants.Type.KEYSYM,
            GLFW.GLFW_KEY_0,
            category
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openMenuKey.consumeClick()) {
                if (client.player != null && client.screen == null) {
                    client.setScreen(new BetScreen());
                }
            }
        });
    }
}
