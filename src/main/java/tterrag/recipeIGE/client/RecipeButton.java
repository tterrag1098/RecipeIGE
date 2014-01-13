package tterrag.recipeIGE.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tterrag.recipeIGE.lib.Reference;
import cpw.mods.fml.common.network.PacketDispatcher;

public class RecipeButton extends GuiButton
{
	private ResourceLocation myButtons = new ResourceLocation("recipeige", "textures/gui/buttons.png");

	public RecipeButton(int x, int y)
	{
		super(10, x + 195 - 19, y + 5, 10, 10, "");
	}

	@Override
	public void drawButton(Minecraft par1Minecraft, int par2, int par3)
	{
		if (this.drawButton)
		{
			FontRenderer fontrenderer = par1Minecraft.fontRenderer;
			par1Minecraft.getTextureManager().bindTexture(myButtons);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = par2 >= this.xPosition && par3 >= this.yPosition && par2 < this.xPosition + this.width && par3 < this.yPosition + this.height;
			int k = this.getHoverState(this.field_82253_i);
			this.drawTexturedModalRect(this.xPosition, this.yPosition, k == 0 ? 0 : -10 + 10 * k, k == 0 ? 10 : 0, this.width, this.height);
			this.mouseDragged(par1Minecraft, par2, par3);
			int l = 14737632;

			if (!this.enabled)
			{
				l = -6250336;
			}
			else if (this.field_82253_i)
			{
				l = 16777120;
			}

			this.drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, l);
		}
	}

	@Override
	public boolean mousePressed(Minecraft minecraft, int par2, int par3)
	{
		this.enabled = !super.mousePressed(minecraft, par2, par3);
		if (!this.enabled)
		{
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.length = 1;
			packet.data = new byte[] { 0 };
			packet.channel = Reference.CHANNEL;
			PacketDispatcher.sendPacketToServer(packet);
		}
		return !this.enabled;
	}
}
