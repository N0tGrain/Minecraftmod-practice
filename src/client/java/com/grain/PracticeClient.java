package com.grain;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class PracticeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.

		CommandRegistrationCallback.EVENT.register((dispatcher, registryAcces, environment) -> {
			dispatcher.register(CommandManager.literal("test").executes(context -> {

				ServerPlayerEntity commandPlayer = context.getSource().getPlayer();
                assert commandPlayer != null;

                if (commandPlayer.getName().equals("N0tBaguette")) {
					context.getSource().sendFeedback(() -> Text.literal("Called the test command!"), false);
					commandPlayer.setStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200), context.getSource().getPlayer());
					// spawn a lit tnt beneath the player's position
				} else {
					context.getSource().sendFeedback(() -> Text.literal("Buh"), false);
				}
				return 1;
			}));
		});

	}
}