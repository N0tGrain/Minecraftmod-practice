package com.grain.mixin.client;

import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerListEntry.class)
public class CustomSkinMixin {

    @Inject(method="getSkinTextures", at=@At("HEAD"), cancellable = true)
    public void overrideSkinTexture(CallbackInfoReturnable<SkinTextures> cir) {
        PlayerListEntry entry = (PlayerListEntry) (Object) this;
        if(entry.getProfile().getName().equals("N0tBaguette")) {
            SkinTextures customSkin = new SkinTextures(
                    new Identifier("practice", "textures/entity/my_skin.png"),
                    null,
                    null,
                    null,
                    null,
                    false
            );
            cir.setReturnValue(customSkin);
        }
    }
}
