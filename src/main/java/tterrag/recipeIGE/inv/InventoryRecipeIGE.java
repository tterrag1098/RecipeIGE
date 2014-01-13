package tterrag.recipeIGE.inv;

import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.lang3.ArrayUtils;

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
				List<ItemStack> shapelessRecipeList = null;
				loop: while (iterator.hasNext())
				{
					IRecipe recipe = iterator.next();
					if (recipe instanceof ShapedRecipes)
						input = ((ShapedRecipes) recipe).recipeItems;
					else if (recipe instanceof ShapelessRecipes)
						shapelessRecipeList = ((ShapelessRecipes) recipe).recipeItems;
					else if (recipe instanceof ShapedOreRecipe)
					{
						input = new ItemStack[9];
						int idx = 0;
						Object[] objs = ((ShapedOreRecipe)recipe).getInput();
						for (Object o : objs)
						{
							if (o instanceof ItemStack)
							{
								input[idx] = (ItemStack) o;
								idx++;
							}
							else if (o instanceof List<?> && ((List<ItemStack>)o).size() > 0)							
							{
								input[idx] = ((List<ItemStack>)o).get(0);
								idx++;
							}
							else if (o == null)
							{
								input[idx] = null;
								idx++;
							}
						}
					}

					if (inv[0] == null || recipe.getRecipeOutput() == null)
						continue loop;

					if (recipe.getRecipeOutput().getItem() != inv[0].getItem() || recipe.getRecipeOutput().getItemDamage() != inv[0].getItemDamage())
						continue loop;
					
					if (recipe instanceof ShapedOreRecipe)
					System.out.println(recipe.getRecipeOutput() + " : " + Arrays.deepToString(input));
					
					if (input != null)
						for (int i = 0; i < 9; i++)
						{
							if (i < input.length)
							{
								container.craftMatrix.setInventorySlotContents(i, input[i]);
							}
						}
					else if (shapelessRecipeList != null)
						for (ItemStack item : shapelessRecipeList)
						{
							container.craftMatrix.setInventorySlotContents(shapelessRecipeList.indexOf(item), new ItemStack(item.getItem(), 1, item.getItemDamage()));
						}

					input = null;
					shapelessRecipeList = null;
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
}
