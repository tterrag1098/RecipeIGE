package tterrag.recipeIGE.util;

import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import tterrag.recipeIGE.client.SmallButton;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.IScheduledTickHandler;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.TickType;

public class RIGETickHandler implements IScheduledTickHandler
{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData)
	{
		if (FMLCommonHandler.instance().getEffectiveSide().isClient() && Minecraft.getMinecraft().currentScreen instanceof GuiContainerCreative)
		{
			GuiContainerCreative gui = (GuiContainerCreative) Minecraft.getMinecraft().currentScreen;
			List<GuiButton> buttons = ObfuscationReflectionHelper.<List<GuiButton>, GuiScreen> getPrivateValue(GuiScreen.class, Minecraft.getMinecraft().currentScreen, "buttonList", "field_73887_h");
			if (buttons.size() > 2)
				buttons.remove(2);
			buttons.add(new SmallButton(10, 0, (Integer) ObfuscationReflectionHelper.getPrivateValue(GuiContainer.class, gui, "guiLeft", "field_74198_m"), (Integer) ObfuscationReflectionHelper
					.getPrivateValue(GuiContainer.class, gui, "guiTop", "field_74198_n")));
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData)
	{
		// Do Nothing
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
	}

	@Override
	public String getLabel()
	{
		return "RIGE Button Adder";
	}

	@Override
	public int nextTickSpacing()
	{
		return 5;
	}

}
