package br.com.finalcraft.bettermobdismemberment.common.core;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

public class MDConfig{

    public Configuration config;

    private int createIntProperty(String propertyName, String description, int def){
        return this.config.get("clientOnly", propertyName, def, description).getInt(def);
    }

    public MDConfig(File file) {
        config          = new Configuration(file);
        gibTime         = createIntProperty("gibTime", "Length of time gibs can live (entirely)", gibTime);
        gibGroundTime   = createIntProperty("gibGroundTime", "Length of time gibs can once they hit the ground", gibGroundTime);
        blood           = createIntProperty("blood", "Should gibbing have blood? [1 == true, 0 == false]", blood);
        bloodCount      = createIntProperty("bloodCount", "Amount of blood particles spawned per gib", bloodCount);
        greenBlood      = createIntProperty("greenBlood", "Should blood be green instead of red?", greenBlood);
        gibPushing      = createIntProperty("gibPushing", "Do gibs actively push mobs in it's path?", gibPushing);
        config.save();
    }

    public int gibTime = 1000;

    public int gibGroundTime = 100;

    public int blood = 1;

    public int bloodCount = 100;

    public int greenBlood = 0;

    public int gibPushing = 1;
}
