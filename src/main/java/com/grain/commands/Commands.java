package com.grain.commands;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Commands {

    public static final Map<UUID, Integer> tickingTNT = new HashMap<>();

    public void registerCommands() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAcces, environment) -> {


            dispatcher.register(CommandManager.literal("test").executes(context -> {

                ServerPlayerEntity commandPlayer = context.getSource().getPlayer();
                assert commandPlayer != null;

                if (commandPlayer.getGameProfile().getName().equals("N0tBaguette")) {
                    context.getSource().sendFeedback(() -> Text.literal("Called the test command!"), false);
//					commandPlayer.setStatusEffect(new StatusEffectInstance(StatusEffects.LEVITATION, 200), context.getSource().getPlayer());
                    // spawn a lit tnt beneath the player's position
                    World world = commandPlayer.getWorld();
                    Vec3d playerPos = commandPlayer.getPos();
                    BlockPos spawnPos = new BlockPos(
                            (int)playerPos.getX(),
                            (int)playerPos.getY(),
                            (int)playerPos.getZ());
                    TntEntity tnt = new TntEntity(world, spawnPos.getX() + 0.5, spawnPos.getY(), spawnPos.getZ() + 0.5, commandPlayer);
                    tnt.setFuse(80);
                    world.spawnEntity(tnt);
                } else {
                    context.getSource().sendFeedback(() -> Text.literal("Buh"), false);
                }
                return 1;
            }));


            dispatcher.register(CommandManager.literal("tnt").then(CommandManager.argument("target", StringArgumentType.word())
                    .executes(context -> {
                        ServerPlayerEntity commandPlayer = context.getSource().getPlayer();
                        String targetName = StringArgumentType.getString(context, "target");
                        assert commandPlayer != null;
                        if(!commandPlayer.getGameProfile().getName().equals("N0tBaguette")) {
                            context.getSource().sendFeedback(() -> Text.literal("You're unauthorized to execute this command, ksst!"), false);
                            return 1;
                        }
                        MinecraftServer server = commandPlayer.getServer();
                        assert server != null;
                        ServerPlayerEntity targetPlayer = server.getPlayerManager().getPlayer(targetName);
                        if(targetPlayer == null) {
                            context.getSource().sendFeedback(() -> Text.literal("Player not found!"), false);
                            return 1;
                        }

                        World playerWorld = targetPlayer.getWorld();
                        Vec3d position = targetPlayer.getPos();
                        TntEntity tntEntity = new TntEntity(playerWorld, position.getX(), position.getY(), position.getZ(), commandPlayer);
                        tntEntity.setFuse(40);
                        playerWorld.spawnEntity(tntEntity);
                        tickingTNT.put(tntEntity.getUuid(), 40);

                        try {
                            TimeUnit.SECONDS.sleep(2);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }

                        ServerTickEvents.END_WORLD_TICK.register(world -> {
                            if (world.isClient()) return;

                            Iterator<Map.Entry<UUID, Integer>> iterator = tickingTNT.entrySet().iterator();
                            while(iterator.hasNext()) {
                                Map.Entry<UUID, Integer> entry = iterator.next();
                                UUID uuid = entry.getKey();
                                int fuse = entry.getValue();
                                if(fuse <= 1) {
                                    Entity tnt = ((ServerWorld) world).getEntity(uuid);
                                    if(tnt != null) {
                                        Vec3d pos = tnt.getPos();
                                        ((ServerWorld) world).spawnParticles(
                                                new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Items.BREAD)),
                                                pos.x, pos.y, pos.z,
                                                1000,0.5,0.5,0.5,5
                                        );
                                    }
                                    iterator.remove();
                                } else {
                                    entry.setValue(fuse - 1);
                                }
                            }
                        });

                        context.getSource().sendFeedback(() -> Text.literal(targetName + " has now exploded, get rekt"), false);
                        return 1;
                    })
            ));

        });
    }

}
