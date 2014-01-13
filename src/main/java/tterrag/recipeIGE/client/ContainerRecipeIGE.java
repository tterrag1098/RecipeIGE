package tterrag.recipeIGE.client;

import tterrag.recipeIGE.inv.InventoryRecipeIGE;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;

public class ContainerRecipeIGE extends Container
{
	public ItemStack[] inventory;

	private InventoryRecipeIGE craftMatrix;
	private InventoryRecipeIGE.InventoryRecipeIGEResult craftResult;

	public ContainerRecipeIGE(EntityPlayer player)
	{
		inventory = new ItemStack[10];

		craftMatrix = new InventoryRecipeIGE(this, 3, 3);
		craftResult = craftMatrix.new InventoryRecipeIGEResult(this);

		InventoryPlayer inventoryplayer = player.inventory;

		bindPlayerInventory(inventoryplayer);
		addMainInv(inventoryplayer);
	}

	private void addMainInv(InventoryPlayer inventoryplayer)
	{
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 3; j++)
			{
				this.addSlotToContainer(new Slot(craftMatrix, j + i * 3, j * 18 - 8, i * 18 + 12));
			}
		}
		this.addSlotToContainer(new Slot(craftResult, 45, 56, 12));
	}

	public void bindPlayerInventory(InventoryPlayer inv)
	{
		int i;

		for (i = 0; i < 3; ++i)
		{
			for (int j = 0; j < 9; ++j)
			{
				this.addSlotToContainer(new Slot(inv, j + i * 9 + 9, -12 + j * 18, 79 + i * 18));
			}
		}

		for (i = 0; i < 9; ++i)
		{
			this.addSlotToContainer(new Slot(inv, i, i * 18 - 12, 137));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void onCraftMatrixChanged(IInventory par1iInventory)
	{
		this.craftResult.setInventorySlotContents(45, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, Minecraft.getMinecraft().theWorld));
	}
}
