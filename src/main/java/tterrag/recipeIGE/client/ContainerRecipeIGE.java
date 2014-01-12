package tterrag.recipeIGE.client;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;

public class ContainerRecipeIGE extends Container
{
	public ContainerRecipeIGE(EntityPlayer player)
	{
		InventoryPlayer inventoryplayer = player.inventory;
		int i;

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inventoryplayer, i, 9 + i * 18, 112));
		}

	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return false;
	}
}
