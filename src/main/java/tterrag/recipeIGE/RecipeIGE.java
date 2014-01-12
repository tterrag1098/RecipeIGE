package tterrag.recipeIGE;

import net.minecraft.creativetab.CreativeTabs;
import tterrag.recipeIGE.creativeTab.CreativeTabRecipeIGE;
import tterrag.recipeIGE.lib.Reference;
import tterrag.recipeIGE.proxy.CommonProxy;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class RecipeIGE
{
	@Instance(Reference.MOD_ID)
	public static RecipeIGE instance;

	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.SERVER_PROXY_CLASS)
	public static CommonProxy proxy;

	public static CreativeTabs tabTahgMod = new CreativeTabRecipeIGE(CreativeTabs.getNextID());

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		// ConfigHandler.init(event.getSuggestedConfigurationFile());
		// proxy.initRenderers();
	}

	@EventHandler
	public void init(FMLInitializationEvent event)
	{
		LanguageRegistry.instance().addStringLocalization("itemGroup." + Reference.TAB_NAME, "en_US", Reference.TAB_LOC_NAME);
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{

	}
}
