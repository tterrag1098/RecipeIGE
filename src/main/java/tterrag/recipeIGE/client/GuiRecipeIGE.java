package tterrag.recipeIGE.client;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRecipeIGE extends GuiContainer
{
	public GuiRecipeIGE(EntityPlayer player)
	{
		super(new ContainerRecipeIGE(player));
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.mc.getTextureManager().bindTexture(new ResourceLocation("recipeige", "textures/gui/recipeGUI.png"));

		//TODO make this dynamic
		this.drawTexturedModalRect(this.xSize / 2 + 10, this.ySize / 2 - 55, 200, 200, 200, 180);
	}
}
