package com.grain.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ToolItem;
import net.minecraft.item.ToolMaterial;

public class ModTntRod extends ToolItem {

    public ModTntRod(ToolMaterial material, Item.Settings settings) {
        super(material, settings);
    }

    // When right-clicking the tnt rod it throws a tnt in the direction the user is looking
    // Once the tnt has been placed, a 5-minute cooldown will start
    // The tnt rod cannot be used until this cooldown is over again

}
