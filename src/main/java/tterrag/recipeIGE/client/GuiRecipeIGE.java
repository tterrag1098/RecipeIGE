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
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("recipeige", "textures/gui/recipeGUI.png"));

		//TODO make this dynamic
		this.drawTexturedModalRect(k - 20, l - 5, 0, 0, this.xSize + 80, this.ySize + 68);
	}
}
