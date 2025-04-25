package com.grain.item.custom;

import com.grain.item.ModArmorMaterials;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
public class ModSwiftnessBoots extends ArmorItem {
    public ModSwiftnessBoots(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if(!world.isClient()) {
            if(entity instanceof PlayerEntity player && hasBootsOn(ModArmorMaterials.SWIFTNESS, player)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 10, 5));
            }
        }
        super.inventoryTick(stack, world, entity, slot, selected);
    }

    private boolean hasBootsOn(ArmorMaterial material, PlayerEntity player) {

        ItemStack boots = player.getInventory().getArmorStack(0);
        if(boots.isEmpty()) {
            return false;
        } else {
            ArmorItem fullBoots = ((ArmorItem) player.getInventory().getArmorStack(0).getItem());
            return fullBoots.getMaterial() == material;
        }
    }

}
