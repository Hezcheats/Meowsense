package dev.hez.meowsense.module.modules.movement.clicktp;

import dev.hez.meowsense.event.bus.Listener;
import dev.hez.meowsense.event.bus.annotations.EventLink;
import dev.hez.meowsense.event.impl.player.EventMotionPre;
import dev.hez.meowsense.module.modules.movement.ClickTP;
import dev.hez.meowsense.module.setting.impl.newmodesetting.SubMode;
import dev.hez.meowsense.utils.mc.ChatUtils;
import dev.hez.meowsense.utils.mc.MoveUtils;
import dev.hez.meowsense.utils.mc.RayTraceUtils;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.Vec3d;

public class MospixelClickTp extends SubMode<ClickTP> {
    public MospixelClickTp(String name, ClickTP parentModule) {
        super(name, parentModule);
    }

    private boolean pressed;

    @Override
    public void onEnable() {
        pressed = false;
        super.onEnable();
    }

    @EventLink
    public final Listener<EventMotionPre> eventMotionPreListener = event -> {
        if (isNull()) {
            return;
        }

        if (mc.options.pickItemKey.isPressed()) {
            if (!pressed) {
                pressed = true;
                final BlockHitResult blockHitResult = RayTraceUtils.rayTrace(70, mc.player.getYaw(), mc.player.getPitch());

                if (blockHitResult != null) {
                    if (blockHitResult.getBlockPos() != null) {
                        event.setCancelled(true);
                        if (blockHitResult.getBlockPos().getY() > mc.player.getY()) {
                            ChatUtils.addMessageToChat("Bad block, you can only tp if the block is below you");
                            return;
                        }
                        Vec3d endPos = new Vec3d(blockHitResult.getBlockPos().getX(), mc.player.getY(), blockHitResult.getBlockPos().getZ());
                        MoveUtils.hypixelTeleport(endPos);
                        event.setCancelled(false);
                    }
                }
            }
        } else {
            if (pressed) {
                pressed = false;
            }
        }
    };
}
