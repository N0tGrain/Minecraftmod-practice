package com.grain.item;

import com.grain.Practice;
import com.grain.item.custom.ModTntRod;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {

    public static final Item TNT_ROD = registerItem("tnt_rod", new ModTntRod(new FabricItemSettings()));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Practice.MOD_ID, name), item);
    }

    public static void registerModItems() {
//        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup); // example
    }

}
