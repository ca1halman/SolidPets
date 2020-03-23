//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package me.grsan.solidpets.util.pathfinder;

import net.minecraft.server.v1_15_R1.*;

import java.util.EnumSet;
import java.util.Random;
import java.util.function.Predicate;

//This class is a modified version of PathfinderGoalFollowEntity
public class PathfinderGoalFollowPlayer extends PathfinderGoal {
    private final EntityInsentient a;
    private EntityLiving c;
    private final NavigationAbstract navigation;
    private int f;
    private final float minDistance;
    private float h;
    private final float maxDistance;
    private final float speed;

    private boolean shouldMove = true;

    public PathfinderGoalFollowPlayer(EntityInsentient thisEntity, EntityLiving follow, float speed, float minDistance, float maxDistance) {
        this.a = thisEntity;
        this.navigation = thisEntity.getNavigation();
        this.minDistance = minDistance;
        this.maxDistance = maxDistance;
        this.speed = speed;

        this.c = follow;

        this.a(EnumSet.of(Type.MOVE, Type.LOOK));
        if (!(thisEntity.getNavigation() instanceof Navigation) && !(thisEntity.getNavigation() instanceof NavigationFlying)) {
            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
        }
    }

    public boolean a() {

        shouldMove = (this.c.getPositionVector().f(this.a.getPositionVector()) > maxDistance - Math.random());
        shouldMove = shouldMove && (this.c.getPositionVector().f(this.a.getPositionVector()) > minDistance + Math.random());

        return c != null && shouldMove;
    }

    public boolean b() {
        return this.c != null && shouldMove;
    }

    public void c() {
        this.h = this.a.a(PathType.WATER);
        this.a.a(PathType.WATER, 0.0F);
    }

    public void d() {
        this.navigation.o();
        this.a.a(PathType.WATER, this.h);
    }

    public void e() {
        if (this.c != null) {
            if (shouldMove) {
                PathEntity path = this.navigation.a(this.c, 1);
                this.navigation.a(path, speed);

                this.a.getControllerLook().a(this.c.getPositionVector().add(0,this.c.getHeadHeight(),0));
            } else {
                this.d();
            }
        }
    }
}
