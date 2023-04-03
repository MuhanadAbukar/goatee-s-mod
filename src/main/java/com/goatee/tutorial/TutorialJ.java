package com.goatee.tutorial;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;




@Mod(modid = "dbccnpcutils", name = "placeholder", version = "1.0")
public class TutorialJ {



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	FMLCommonHandler.instance().bus().register(new TestingEvent());

    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){

    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	

    }
}
