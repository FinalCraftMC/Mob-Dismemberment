package me.ichun.mods.mobdismemberment.client.util;

import me.ichun.mods.mobdismemberment.common.MobDismemberment;
import net.minecraft.entity.Entity;
import noppes.npcs.entity.EntityCustomNpc;
import noppes.npcs.scripted.ScriptNpc;

public class CustomNPCsUtil {

    public static boolean canDismember(Entity entity) {
        if (MobDismemberment.hasCustomNPCs() && entity instanceof EntityCustomNpc) {
            EntityCustomNpc npc = (EntityCustomNpc)entity;
            ScriptNpc scriptNpc = new ScriptNpc(npc);
            return scriptNpc.getFaction().getName().equalsIgnoreCase("Zombie");
        }
        return false;
    }
}
