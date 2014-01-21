package tterrag.recipeIGE;

import tterrag.recipeIGE.lib.Reference;
import tterrag.recipeIGE.proxy.CommonProxy;
import tterrag.recipeIGE.proxy.PacketHandler;
import tterrag.recipeIGE.util.NBTFileWriter;
import tterrag.recipeIGE.util.RIGETickHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
@NetworkMod(serverSideRequired = true, clientSideRequired = true, channels = { Reference.CHANNEL }, packetHandler = PacketHandler.class)
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
		NBTFileWriter.init(event.getSuggestedConfigurationFile());
		NetworkRegistry.instance().registerGuiHandler(instance, proxy);
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
