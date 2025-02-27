package net.backslot.network;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class BackSlotClientPacket {

    public static void init() {
        ClientPlayNetworking.registerGlobalReceiver(VisibilityPacket.PACKET_ID, (payload, context) -> {
            int entityId = payload.entityId();
            int slotId = payload.slotId();
            ItemStack itemStack = payload.itemStack();
            context.client().execute(() -> {
                if (context.player().getWorld().getEntityById(entityId) != null && context.player().getWorld().getEntityById(entityId) instanceof PlayerEntity playerEntity) {
                    playerEntity.getInventory().setStack(slotId, itemStack.copy());
                }
            });
        });
    }
}
