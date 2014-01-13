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
	public ItemStack[] inventory = new ItemStack[10];

	public InventoryRecipeIGE craftMatrix;
	private InventoryRecipeIGE.InventoryRecipeIGEResult craftResult;

	public ContainerRecipeIGE(EntityPlayer player)
	{
		craftMatrix = new InventoryRecipeIGE(this, 3, 3);
		craftResult = craftMatrix.new InventoryRecipeIGEResult(this, craftMatrix);

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
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex)
	{
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotIndex);
		if (slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (slotIndex > 35)
			{
				if (!this.mergeItemStack(itemstack1, 27, 36, false))
				{		System.out.println(slotIndex);

					if (!this.mergeItemStack(itemstack1, 0, 27, false))
						return null;
				}
				slot.onSlotChange(itemstack1, itemstack);
			}
			else if (slotIndex < 36)
			{
				if (!this.mergeItemStack(itemstack1, 36, 45, false))
					return null;
			}

			if (itemstack1.stackSize == 0)
			{
				slot.putStack((ItemStack) null);
			}
			else
			{
				slot.onSlotChanged();
			}
			if (itemstack1.stackSize == itemstack.stackSize)
			{
				return null;
			}
			slot.onPickupFromSlot(player, itemstack1);
			if (itemstack1.stackSize == 0)
			{
				slot.putStack(null);
				return null;
			}
		}
		return itemstack;
	}
}
