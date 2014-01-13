package tterrag.recipeIGE.inv;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import tterrag.recipeIGE.client.ContainerRecipeIGE;

public class InventoryRecipeIGE extends InventoryCrafting
{
	public InventoryRecipeIGE(Container par1Container, int size, int height)
	{
		super(par1Container, size, height);
	}
	
	public class InventoryRecipeIGEResult extends InventoryCraftResult implements IInventory
	{
		@SuppressWarnings("unused")
		private ContainerRecipeIGE container;
		public ItemStack[] inv;
		
		public InventoryRecipeIGEResult(Container container)
		{
			this.container = (ContainerRecipeIGE) container;
			this.inv = new ItemStack[1];
		}
		
		@Override
		public void setInventorySlotContents (int slot, ItemStack itemstack)
	    {
			System.out.println(slot);
	        inv[0] = itemstack;
	    }
		
	    @Override
		public ItemStack getStackInSlot(int par1)
	    {
	        return this.inv[0];
	    }
	}
}
