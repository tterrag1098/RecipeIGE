package tterrag.recipeIGE.proxy;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import tterrag.recipeIGE.client.ContainerRecipeIGE;
import tterrag.recipeIGE.client.GuiRecipeIGE;
import cpw.mods.fml.common.network.IGuiHandler;

public class CommonProxy implements IGuiHandler
{
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return ID == 0 ? new ContainerRecipeIGE(player) : null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
	{
		return ID == 0 ? new GuiRecipeIGE(player) : null;
	}
}
