package dev.hez.meowsense.module.modules.combat.criticals;

import dev.hez.meowsense.event.bus.Listener;
import dev.hez.meowsense.event.bus.annotations.EventLink;
import dev.hez.meowsense.event.impl.player.EventAttack;
import dev.hez.meowsense.module.modules.combat.Criticals;
import dev.hez.meowsense.module.setting.impl.newmodesetting.SubMode;

public class WatchdogCriticals extends SubMode<Criticals> {
    public WatchdogCriticals(String name, Criticals parentModule) {
        super(name, parentModule);
    }

    @EventLink
    public final Listener<EventAttack> eventAttackListener = event -> {
        if (isNull()) {
            return;
        }
        if (mc.player.isOnGround()) {
            mc.player.setPosition(mc.player.getX(), mc.player.getY() + 0.001D, mc.player.getZ());
        }
    };
}
