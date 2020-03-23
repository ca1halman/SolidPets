//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.grsan.solidpets.util.pathfinder;

import net.minecraft.server.v1_15_R1.*;

import java.util.EnumSet;
import java.util.function.Predicate;

//This class is a modified version of PathfinderGoalFollowEntity
public class PathfinderGoalFollowPlayer extends PathfinderGoal {
    private final EntityInsentient a;
    private final Predicate<EntityInsentient> b;
    private EntityLiving c;
    private final double d;
    private final NavigationAbstract navigation;
    private int f;
    private final float minDistance;
    private float h;
    private final float maxDistance;

    public PathfinderGoalFollowPlayer(EntityInsentient thisEntity, EntityLiving follow, double speed, float minDistance, float maxDistance) {
        this.a = thisEntity;
        this.b = (var1x) -> {
            return var1x != null && thisEntity.getClass() != var1x.getClass();
        };
        this.d = speed;
        this.navigation = thisEntity.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;

        this.c = follow;

        this.a(EnumSet.of(Type.MOVE, Type.LOOK));
        if (!(thisEntity.getNavigation() instanceof Navigation) && !(thisEntity.getNavigation() instanceof NavigationFlying)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    public boolean a() {
        return true;
    }

    public boolean b() {
        return this.c != null;
    }

    public void c() {
        this.f = 0;
        this.h = this.a.a(PathType.WATER);
        this.a.a(PathType.WATER, 0.0F);
    }

    public void d() {
        this.c = null;
        this.navigation.o();
        this.a.a(PathType.WATER, this.h);
    }

    public void e() {
        if (this.c != null) {
            if (this.c.getPositionVector().f(this.a.getPositionVector()) > maxDistance) {
                PathEntity path = this.navigation.a(this.c, 1);
                this.navigation.a(path, 1);
            }
        }
    }
}
