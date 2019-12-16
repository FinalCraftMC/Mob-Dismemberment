package me.ichun.mods.mobdismemberment.common.core;

import ichun.common.core.config.Config;
import ichun.common.core.config.ConfigHandler;
import ichun.common.core.config.IConfigUser;
import me.ichun.mods.mobdismemberment.common.MobDismemberment;
import net.minecraftforge.common.config.Property;
import org.apache.logging.log4j.LogManager;

import java.io.File;

public class MDConfig implements IConfigUser {

    public Config config;

    public MDConfig() {
        config = ConfigHandler.createConfig(new File("config"), "mobDis", "Mob Dismemberment", LogManager.getLogger("MobDis"), this);

        config.setCurrentCategory("clientOnly", "mobdis.config.cat.clientOnly.name", "mobdis.config.cat.clientOnly.comment");
        gibTime = config.createIntProperty("gibTime", "mobdis.config.prop.gibTime.name", "mobdis.config.prop.gibTime.comment", true, false, 1000, 0, 2147483647);
        gibGroundTime = config.createIntProperty("gibGroundTime", "mobdis.config.prop.gibGroundTime.name", "mobdis.config.prop.gibGroundTime.comment", true, false, 100, 0, 2147483647);
        blood = config.createIntBoolProperty("blood", "mobdis.config.prop.blood.name", "mobdis.config.prop.blood.comment", true, false, true);
        bloodCount = config.createIntProperty("bloodCount", "mobdis.config.prop.bloodCount.name", "mobdis.config.prop.bloodCount.comment", true, false, 100, 1, 1000);
        greenBlood = config.createIntBoolProperty("greenBlood", "mobdis.config.prop.greenBlood.name", "mobdis.config.prop.greenBlood.comment", true, false, false);
        gibPushing = config.createIntBoolProperty("gibPushing", "mobdis.config.prop.gibPushing.name", "mobdis.config.prop.gibPushing.comment", true, false, true);
        gibTimeOut = config.createIntProperty("bloodCount", "mobdis.config.prop.gibTimeOut.name", "mobdis.config.prop.gibTimeOut.comment", true, false, 2, 1, 30);
    }

    public int gibTime = 1000;

    public int gibGroundTime = 100;

    public int blood = 1;

    public int bloodCount = 100;

    public int greenBlood = 0;

    public int gibPushing = 1;

    public int gibTimeOut = 2;

    public String getModId(){
        return MobDismemberment.MOD_ID;
    }

    public String getModName(){
        return "Mob Dismemberment";
    }

    @Override
    public boolean onConfigChange(Config config, Property property) {
        return false;
    }
}
