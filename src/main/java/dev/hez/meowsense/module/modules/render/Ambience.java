package dev.hez.meowsense.module.modules.render;

import dev.hez.meowsense.event.bus.Listener;
import dev.hez.meowsense.event.bus.annotations.EventLink;
import dev.hez.meowsense.event.impl.network.EventPacket;
import dev.hez.meowsense.event.impl.player.EventTickPre;
import dev.hez.meowsense.event.types.TransferOrder;
import dev.hez.meowsense.mixin.accesors.WorldTimeUpdateS2CPacketAccesor;
import dev.hez.meowsense.module.Module;
import dev.hez.meowsense.module.ModuleCategory;
import dev.hez.meowsense.module.setting.impl.ModeSetting;
import dev.hez.meowsense.module.setting.impl.NumberSetting;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.world.World;

public class Ambience extends Module {
    public static final NumberSetting timeOfDay = new NumberSetting("Time", World.field_30969 / 2, World.field_30969, 0, 1);

    public Ambience() {
        super("Ambience", "Changes the game", 0, ModuleCategory.RENDER);
        this.addSettings(timeOfDay);
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (isNull()) {
            return;
        }
        mc.world.setTimeOfDay(timeOfDay.getValueInt());
    };

    @EventLink
    public final Listener<EventPacket> eventPacketListener = event -> {
        if (isNull()) {
            return;
        }
        if (event.getOrder() == TransferOrder.SEND) {
            return;
        }
        if (event.getPacket() instanceof WorldTimeUpdateS2CPacket a) {
            ((WorldTimeUpdateS2CPacketAccesor) a).setTimeOfDay(timeOfDay.getValueInt());
        }
    };
}
