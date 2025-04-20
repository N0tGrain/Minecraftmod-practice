package com.grain;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class PracticeClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.


		CommandRegistrationCallback.EVENT.register((dispatcher, registryAcces, environment) -> {
			dispatcher.register(CommandManager.literal("test").executes(context -> {
				if (context.getSource().getName().equals("N0tBaguette")) {
					context.getSource().sendFeedback(() -> Text.literal("Called the test command!"), false);
				} else {
					context.getSource().sendFeedback(() -> Text.literal("Buh"), false);
				}
				return 1;
			}));
		});

	}
}