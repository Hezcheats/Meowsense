package dev.hez.meowsense.module.modules.movement.fly;

import dev.hez.meowsense.event.bus.Listener;
import dev.hez.meowsense.event.bus.annotations.EventLink;
import dev.hez.meowsense.event.impl.world.EventBlockShape;
import dev.hez.meowsense.module.modules.movement.Fly;
import dev.hez.meowsense.module.setting.impl.newmodesetting.SubMode;
import net.minecraft.util.shape.VoxelShapes;

public class AirWalkFly extends SubMode<Fly> {
    public AirWalkFly(String name, Fly parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventBlockShape> eventBlockShapeListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getPos().getY() < mc.player.getBlockY()) {
            event.setShape(VoxelShapes.fullCube());
        }
    };
}
