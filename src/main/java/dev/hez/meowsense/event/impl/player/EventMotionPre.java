package dev.hez.meowsense.event.impl.player;

import dev.hez.meowsense.event.types.CancellableEvent;

public class EventMotionPre extends CancellableEvent {
    public double x, y, z;

    public boolean onGround;

    public EventMotionPre(double x, double y, double z, boolean onGround) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.onGround = onGround;
    }
}
