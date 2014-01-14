package tterrag.recipeIGE.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class GuiRecipeIGE extends GuiContainer
{
	@SuppressWarnings("unused")
	private EntityPlayer player;
	private ContainerRecipeIGE container;
	private char[] chars = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i' };

	public GuiRecipeIGE(EntityPlayer player)
	{
		this(new ContainerRecipeIGE(player));
	}

	public GuiRecipeIGE(ContainerRecipeIGE container)
	{
		super(container);
		this.container = container;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void initGui()
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;

		this.buttonList.add(new GuiButton(0, k + 80, l + 5, 70, 20, "Add Recipe"));
		this.buttonList.add(new GuiButton(1, k + 80, l + 28, 70, 20, "Del Recipe"));
		this.buttonList.add(new GuiButton(2, k + 80, l + 51, 70, 20, "Next Recipe"));

		this.buttonList.add(new SmallButton(4, 2, k + 65, l + 64));
		this.buttonList.add(new SmallButton(3, 1, k + 50, l + 64));

		super.initGui();
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		int k = (this.width - this.xSize) / 2;
		int l = (this.height - this.ySize) / 2;
		this.mc.getTextureManager().bindTexture(new ResourceLocation("recipeige", "textures/gui/recipeGUI.png"));

		this.drawTexturedModalRect(k - 20, l - 5, 0, 0, this.xSize + 80, this.ySize + 68);
	}

	@Override
	protected void actionPerformed(GuiButton button)
	{
		switch (button.id)
		{
		case 0:
			addRecipe(this.container.craftMatrix.getStacks(), this.container.craftResult.inv[0]);
			break;
		case 1:
			removeRecipe(this.container.craftResult.inv[0]);
			container.craftMatrix.removeAll();
			break;
		case 2:
		}
	}

	/******* Adding Recipes *******/

	/**
	 * Adds recipe to the registry, based on an array of ItemStacks
	 * 
	 * @param input
	 *            - Array of itemstacks (crafting matrix)
	 * @param output
	 *            - Output of the recipe
	 */
	private void addRecipe(ItemStack[] input, ItemStack output)
	{
		if (output == null)
			return;

		Map<String, Character> charToItemMap = new HashMap<String, Character>();
		ItemStack[] differentItems = new ItemStack[9];
		int idx = 0;
		for (ItemStack i : input)
		{
			if (notEqualToAny(i, differentItems) && i != null)
			{
				charToItemMap.put(i.itemID + "##" + i.getItemDamage(), chars[idx]);
				differentItems[idx] = i;
				idx++;
			}
		}

		idx = 0;

		int numItems = charToItemMap.size();

		Object[] recipe = new Object[3 + (numItems == 0 ? 1 : numItems) * 2];
		String s1 = "" + getChar(input[0], charToItemMap) + getChar(input[1], charToItemMap) + getChar(input[2], charToItemMap);
		String s2 = "" + getChar(input[3], charToItemMap) + getChar(input[4], charToItemMap) + getChar(input[5], charToItemMap);
		String s3 = "" + getChar(input[6], charToItemMap) + getChar(input[7], charToItemMap) + getChar(input[8], charToItemMap);

		recipe[0] = s1;
		recipe[1] = s2;
		recipe[2] = s3;

		char[] recipeChars = ArrayUtils.addAll(s1.toCharArray(), s2.toCharArray());
		recipeChars = ArrayUtils.addAll(recipeChars, s3.toCharArray());

		char[] differentChars = getDifferentChars(recipeChars);

		for (int i = 3; i < (numItems * 2) + 3; i += 2)
		{
			recipe[i] = differentChars[idx];
			recipe[i + 1] = (differentItems[idx]);
			idx++;
		}

		if (anyIsNull(recipe))
			return;

		System.out.println(Arrays.deepToString(recipe));
		GameRegistry.addRecipe(output, recipe);
	}

	private boolean anyIsNull(Object[] recipe)
	{
		for (Object o : recipe)
			if (o == null)
				return true;
		return false;
	}

	/**
	 * Removes duplicate chars from an array, preserving the order
	 * 
	 * @param recipeChars
	 *            - the array to process
	 * @return an array of chars, all unique
	 */
	private char[] getDifferentChars(char[] recipeChars)
	{
		ArrayList<Character> foundChars = new ArrayList<Character>();
		for (char c : chars)
		{
			if (ArrayUtils.contains(recipeChars, c))
				foundChars.add(c);
		}

		Character[] differentChars = foundChars.toArray(new Character[] {});
		char[] finalChars = new char[differentChars.length];
		for (int i = 0; i < differentChars.length; i++)
		{
			finalChars[i] = differentChars[i];
		}

		return finalChars;
	}

	/**
	 * Makes sure the passed item is not equal to any item in the array
	 * 
	 * @param item
	 *            - ItemStack to check equality with
	 * @param differentItems
	 *            - Array of ItemStacks to compare to
	 * @return whether item is not equal to any itemstack in differentItems
	 */
	private boolean notEqualToAny(ItemStack item, ItemStack[] differentItems)
	{
		for (int i = 0; i < 9; i++)
		{
			if (differentItems[i] != null && item != null && item.getItem() == differentItems[i].getItem())
				return false;
		}

		return true;
	}

	/**
	 * Gets the character associated with this ItemStack in the recipe
	 * 
	 * @param stack
	 *            - ItemStack to find the character for
	 * @param map
	 *            - HashMap object to search in
	 * @return the character associated
	 */
	private char getChar(ItemStack stack, Map<String, Character> map)
	{
		if (stack == null)
			return ' ';
		return map.get(stack.itemID + "##" + stack.getItemDamage());
	}

	// Unused as of yet
	@SuppressWarnings("unused")
	private String toStackString(ItemStack stack)
	{
		return stack.itemID + "##" + stack.getItemDamage();
	}

	/****** Removing Recipes *******/

	/**
	 * Removes a recipe from the registry
	 * 
	 * @param stack
	 *            - the stack that is the result of the recipe
	 */
	@SuppressWarnings("unchecked")
	public void removeRecipe(ItemStack stack)
	{
		Iterator<IRecipe> recipes = CraftingManager.getInstance().getRecipeList().iterator();
		IRecipe recipe;
		while (recipes.hasNext())
		{
			recipe = recipes.next();
			if (recipe != null && recipe.getRecipeOutput() != null && stack != null && recipe.getRecipeOutput().itemID == stack.itemID && recipe.getRecipeOutput().getItemDamage() == stack.getItemDamage())
			{
				if (recipeEquals(recipe, container.craftMatrix.getStacks()))
				{
					recipes.remove();
				}
			}
		}
	}

	/**
	 * Finds if the matrix of a crafting recipe is equal to an array of
	 * ItemStacks
	 * 
	 * @param recipe
	 *            - The IRecipe to be compared with
	 * @param stacks2
	 *            - the ItemStack array for the IRecipe to be compared to
	 * @return if the matrix of the two recipes are exactly equal
	 */
	@SuppressWarnings("unchecked")
	private boolean recipeEquals(IRecipe recipe, ItemStack[] stacks2)
	{
		ItemStack[] stacks1 = new ItemStack[9];
		if (recipe instanceof ShapedRecipes)
			ArrayUtils.addAll(stacks1, ((ShapedRecipes) recipe).recipeItems);
		else if (recipe instanceof ShapelessRecipes)
			ArrayUtils.addAll(stacks1, (ItemStack[]) ((ShapelessRecipes) recipe).recipeItems.toArray(new ItemStack[] {}));
		else if (recipe instanceof ShapedOreRecipe)
		{
			int idx = 0;
			Object[] objs = ((ShapedOreRecipe) recipe).getInput();
			for (Object o : objs)
			{
				if (o instanceof ItemStack)
				{
					stacks1[idx] = (ItemStack) o;
					idx++;
				}
				else if (o instanceof List<?> && ((List<ItemStack>) o).size() > 0)
				{
					stacks1[idx] = ((List<ItemStack>) o).get(0);
					idx++;
				}
				else if (o == null)
				{
					stacks1[idx] = null;
					idx++;
				}
			}
		}

		boolean foundNotEquals = false;

		for (int i = 0; i < 9; i++)
		{
			if (!((stacks1[i] == null && stacks2[i] == null) || (stacks1[i] != null && stacks2[i] != null && stacks1[i].getItem() == stacks2[i].getItem() && stacks1[i].getItemDamage() == stacks2[i]
					.getItemDamage())))
				foundNotEquals = true;
		}

		return !foundNotEquals;
	}
}
