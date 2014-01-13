package tterrag.recipeIGE.inv;

import java.util.List;
import java.util.ListIterator;

import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.ShapedOreRecipe;
import tterrag.recipeIGE.client.ContainerRecipeIGE;

public class InventoryRecipeIGE extends InventoryCrafting
{
	ContainerRecipeIGE container;

	public InventoryRecipeIGE(Container par1Container, int size, int height)
	{
		super(par1Container, size, height);
		container = (ContainerRecipeIGE) par1Container;
	}

	public class InventoryRecipeIGEResult extends InventoryCraftResult implements IInventory
	{
		private ContainerRecipeIGE container;
		private InventoryRecipeIGE matrix;
		public ItemStack[] inv;

		public InventoryRecipeIGEResult(Container container, InventoryRecipeIGE crafter)
		{
			this.container = (ContainerRecipeIGE) container;
			this.inv = new ItemStack[1];
			this.matrix = crafter;
		}

		@Override
		public void setInventorySlotContents(int slot, ItemStack itemstack)
		{
			inv[0] = itemstack;
		}

		@Override
		public ItemStack getStackInSlot(int par1)
		{
			return this.inv[0];
		}

		@SuppressWarnings({ "unchecked" })
		@Override
		public void onInventoryChanged()
		{
			super.onInventoryChanged();
			if (matrix.isEmpty())
			{
				ListIterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().listIterator();
				ItemStack[] input = null;
				loop: while (iterator.hasNext())
				{
					IRecipe recipe = iterator.next();
					input = getInputArray(recipe);
					
					if (inv[0] == null || recipe.getRecipeOutput() == null)
						continue loop;

					if (recipe.getRecipeOutput().getItem() != inv[0].getItem() || recipe.getRecipeOutput().getItemDamage() != inv[0].getItemDamage())
						continue loop;
										
					if (input != null)
						for (int i = 0; i < 9; i++)
						{
							if (i < input.length && input[i] != null)
							{
								input[i].stackSize++;
								container.craftMatrix.setInventorySlotContents(i, input[i]);
							}
						}

					input = null;
				}
			}
		}
	}

	public boolean isEmpty()
	{
		for (int i = 0; i < 9; i++)
		{
			if (container.craftMatrix.getStackInSlot(i) != null)
				return false;
		}
		return true;
	}
	
	@SuppressWarnings("unchecked")
	private ItemStack[] getInputArray(IRecipe recipe)
	{
		ItemStack[] stacks = new ItemStack[9];
		if (recipe instanceof ShapedRecipes)
			stacks = ((ShapedRecipes) recipe).recipeItems;
		else if (recipe instanceof ShapelessRecipes)
			stacks = (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray(new ItemStack[]{});
		else if (recipe instanceof ShapedOreRecipe)
		{
			int idx = 0;
			Object[] objs = ((ShapedOreRecipe)recipe).getInput();
			for (Object o : objs)
			{
				if (o instanceof ItemStack)
				{
					stacks[idx] = (ItemStack) o;
					idx++;
				}
				else if (o instanceof List<?> && ((List<ItemStack>)o).size() > 0)							
				{
					stacks[idx] = ((List<ItemStack>)o).get(0);
					idx++;
				}
				else if (o == null)
				{
					stacks[idx] = null;
					idx++;
				}
			}
		}
		
		return stacks;
	}
	
	public ItemStack[] getStacks()
	{
		ItemStack[] stacks = new ItemStack[9];
		for (int i = 0; i < 9; i++)
		{
			stacks[i] = this.getStackInSlot(i);
		}
		
		return stacks;
	}
}
