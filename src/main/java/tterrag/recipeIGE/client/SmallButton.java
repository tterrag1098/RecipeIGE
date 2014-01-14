package tterrag.recipeIGE.client;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import tterrag.recipeIGE.lib.Reference;
import cpw.mods.fml.common.network.PacketDispatcher;

public class SmallButton extends GuiButton
{
	private ResourceLocation myButtons = new ResourceLocation("recipeige", "textures/gui/buttons.png");
	private int renderID;

	private int hoverTime = 0;
	private long prevSystemTime = 0;
	private int tooltipWidth = -1;
	
	private String[] tooltips = {"Shapeless Mode", "Clear Grid"};

	public final static int LINE_HEIGHT = 11;

	public SmallButton(int id, int drawID, int x, int y)
	{
		super(id, drawID == 0 ? x + 195 - 19 : x, drawID == 0 ? y + 5 : y, 10, 10, "");
		renderID = drawID;
	}

	@Override
	public void drawButton(Minecraft minecraft, int i, int j)
	{
		if (this.drawButton)
		{
			minecraft.getTextureManager().bindTexture(myButtons);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			this.field_82253_i = i >= this.xPosition && j >= this.yPosition && i < this.xPosition + this.width && j < this.yPosition + this.height;
			int k = this.getHoverState(this.field_82253_i);

			switch (this.renderID)
			{
			case 0:
				this.drawTexturedModalRect(this.xPosition, this.yPosition, k == 0 ? 0 : -10 + 10 * k, k == 0 ? 10 : 0, this.width, this.height);
				this.mouseDragged(minecraft, i, j);
				break;
			case 1:
				this.drawTexturedModalRect(this.xPosition, this.yPosition, (k == 0 ? 0 : -10 + 10 * k) + 20, k == 0 ? 10 : 0, this.width, this.height);
				this.mouseDragged(minecraft, i, j);
				break;
			case 2:
				this.drawTexturedModalRect(this.xPosition, this.yPosition, (k == 0 ? 0 : -10 + 10 * k) + 40, k == 0 ? 10 : 0, this.width, this.height);
				this.mouseDragged(minecraft, i, j);
				break;
			}

			if (this.renderID > 0)
			{
				// Compute hover time
				if (isMouseOverButton(i, j))
				{
					String line = tooltips[renderID - 1];
					FontRenderer fontRenderer = minecraft.fontRenderer;

					// Compute tooltip params
					int x = i + 12, y = j - LINE_HEIGHT;

					tooltipWidth = fontRenderer.getStringWidth(line);
					
					if (x + tooltipWidth > minecraft.currentScreen.width)
					{
						x = minecraft.currentScreen.width - tooltipWidth;
					}

					// Draw background
					drawGradientRect(x - 3, y - 3, x + tooltipWidth + 3, y + LINE_HEIGHT, 0xc0000000, 0xc0000000);

					int j1 = y;
					int l = -1;
					
					// Draw lines
					int lineCount = 0;
						fontRenderer.drawStringWithShadow(line, x, j1, l);
				}
			}
		}
	}

	protected boolean isMouseOverButton(int i, int j)
	{
		return i >= xPosition && j >= yPosition && i < (xPosition + width) && j < (yPosition + height);
	}

	@Override
	public boolean mousePressed(Minecraft minecraft, int par2, int par3)
	{
		if (renderID != 0)
			return super.mousePressed(minecraft, par2, par3);

		this.enabled = !super.mousePressed(minecraft, par2, par3);
		if (!this.enabled && this.renderID == 0)
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
