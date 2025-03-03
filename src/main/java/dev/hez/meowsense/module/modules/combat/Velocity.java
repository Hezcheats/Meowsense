package dev.hez.meowsense.module.modules.combat;

import dev.hez.meowsense.Client;
import dev.hez.meowsense.event.bus.Listener;
import dev.hez.meowsense.event.bus.annotations.EventLink;
import dev.hez.meowsense.event.impl.network.EventPacket;
import dev.hez.meowsense.event.impl.player.EventTickPre;
import dev.hez.meowsense.module.Module;
import dev.hez.meowsense.module.ModuleCategory;
import dev.hez.meowsense.module.modules.combat.velocity.*;
import dev.hez.meowsense.module.modules.movement.Speed;
import dev.hez.meowsense.module.modules.other.Disabler;
import dev.hez.meowsense.module.modules.player.BedAura;
import dev.hez.meowsense.module.setting.impl.NumberSetting;
import dev.hez.meowsense.module.setting.impl.newmodesetting.NewModeSetting;
import dev.hez.meowsense.utils.mc.MoveUtils;

public class Velocity extends Module {
    public final NewModeSetting velocityMode = new NewModeSetting("Velocity Mode", "Simple",
            new SimpleVelocity("Simple", this),
            new WatchdogAirVelocity("Watchdog Air", this),
            new IntaveVelocity("Intave", this),
            new BlocksMCVelocity("BlocksMC", this),
            new LegitVelocity("Legit", this));

    public final NumberSetting verticalSimple = new NumberSetting("Vertical", -100, 100, 85, 1);
    public final NumberSetting horizontalSimple = new NumberSetting("Horizontal", -100, 100, 85, 1);

    public final NumberSetting verticalBlocksMC = new NumberSetting("Vertical BMC", -100, 100, 85, 1);
    public final NumberSetting horizontalBlocksMC = new NumberSetting("Horizontal BMC", -100, 100, 85, 1);

    public final NumberSetting airTicks = new NumberSetting("Air Ticks", 0, 10, 5, 1);

    public Velocity() {
        super("Velocity", "Changes knockback", 0, ModuleCategory.COMBAT);
        addSettings(velocityMode, verticalSimple, horizontalSimple, verticalBlocksMC, horizontalBlocksMC, airTicks);
        verticalSimple.addDependency(velocityMode, "Simple");
        horizontalSimple.addDependency(velocityMode, "Simple");

        verticalBlocksMC.addDependency(velocityMode, "BlocksMC");
        horizontalBlocksMC.addDependency(velocityMode, "BlocksMC");

        airTicks.addDependency(velocityMode, "Watchdog Air");
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (velocityMode.isMode("Simple")) {
            this.setSuffix(verticalSimple.getValue() + "%" + " " + horizontalSimple.getValue() + "%");
        } else if (velocityMode.isMode("BlocksMC")) {
            this.setSuffix(velocityMode.getCurrentMode().getName() + " " + verticalBlocksMC.getValue() + "%" + " " + horizontalBlocksMC.getValue() + "%");
        } else {
            this.setSuffix(velocityMode.getCurrentMode().getName());
        }
    };

    public boolean canWork(EventPacket event) {
        if (Client.INSTANCE.getModuleManager().getModule(BedAura.class).shouldCancelVelocity()) {
            event.cancel();
            return false;
        }

        if (Client.INSTANCE.getModuleManager().getModule(Speed.class).isEnabled()) {
            Speed speed = Client.INSTANCE.getModuleManager().getModule(Speed.class);
            if (speed.speedMode.isMode("Watchdog")) {
                if (speed.watchdogLowHop.getValue() && speed.watchdogShouldCancelVelocity.getValue()) {
                    boolean canLowHop = Client.INSTANCE.getModuleManager().getModule(Disabler.class).isEnabled() && Client.INSTANCE.getModuleManager().getModule(Disabler.class).watchdogMotion.getValue() && Client.INSTANCE.getModuleManager().getModule(Disabler.class).canLowHop;
                    if (canLowHop) {
                        if (MoveUtils.isMoving2()) {
                            event.cancel();
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}