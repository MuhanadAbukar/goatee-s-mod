package com.goatee.tutorial;

import com.goatee.tutorial.proxy.CommonProxy;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;




@Mod(modid = "dbccnpcutils", name = "test", version = "1.0")
public class Main {
	@SidedProxy(clientSide = "com.goatee.tutorial.proxy.ClientProxy", serverSide = "com.goatee.tutorial.proxy.CommonProxy")
	public static CommonProxy proxy;



    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event){
    	proxy.preInit(event);

    }
    @Mod.EventHandler
    public void init(FMLInitializationEvent event){
    

    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event){
    	

    }
}
