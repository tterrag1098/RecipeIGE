package tterrag.recipeIGE;

import tterrag.recipeIGE.lib.Reference;
import tterrag.recipeIGE.proxy.CommonProxy;
import tterrag.recipeIGE.util.RIGETickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class RecipeIGE
{
	@Instance(Reference.MOD_ID)
	public static RecipeIGE instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static RIGETickHandler tickHandler;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// ConfigHandler.init(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		tickHandler = new RIGETickHandler();
		TickRegistry.registerScheduledTickHandler(tickHandler, Side.CLIENT);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
