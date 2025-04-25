package com.grain.item.custom;

import net.minecraft.entity.TntEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ModTntRod extends Item {
    private static final int COOLDOWN_TICKS = 20 * 60; // 10 minutes

    public ModTntRod(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack stack = player.getStackInHand(hand);

        if (!world.isClient) {
            // Check if the item is on cooldown
            if (!player.getItemCooldownManager().isCoolingDown(this)) {
                // Throw TNT
                throwTNT(world, player);

                // Start cooldown
                player.getItemCooldownManager().set(this, COOLDOWN_TICKS);
            } else {
                // Display cooldown message
                player.sendMessage(Text.literal("This item is still on cooldown").formatted(Formatting.RED), true);
            }
        }

        return TypedActionResult.success(stack, world.isClient());
    }

    private void throwTNT(World world, PlayerEntity player) {
        // Create primed TNT entity and set its velocity
        TntEntity tnt = new TntEntity(world, player.getX(), player.getEyeY(), player.getZ(), player);
        Vec3d lookVec = player.getRotationVector();
        tnt.setVelocity(lookVec.x * 1.5, lookVec.y * 1.5, lookVec.z * 1.5);
        world.spawnEntity(tnt);

        // Optional: set fuse timer shorter if you want it to explode quicker
        tnt.setFuse(40); // 2 seconds (default is 80 ticks = 4 seconds)
    }
}