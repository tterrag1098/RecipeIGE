package tterrag.recipeIGE.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import org.apache.commons.lang3.ArrayUtils;

import cpw.mods.fml.common.registry.GameRegistry;

public class GuiRecipeIGE extends GuiContainer
{
	@SuppressWarnings("unused")
	private EntityPlayer player;
	private ContainerRecipeIGE container;
	private char[] chars = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i'};
	
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
		this.buttonList.add(new GuiButton(1, k + 80, l + 28, 70, 20, "Remove"));
		this.buttonList.add(new GuiButton(2, k + 80, l + 51, 70, 20, "Next Recipe"));

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
		switch(button.id)
		{
		case 0: 
			addRecipe(this.container.craftMatrix.getStacks(), this.container.craftResult.inv[0]);
			break;
		case 1:
		case 2:
		}
	}

	
	/**
	 * Adds recipe to the registry, based on an array of ItemStacks
	 * @param input
	 * - Array of itemstacks (crafting matrix)
	 * @param output
	 * - Output of the recipe
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
		
		for (int i = 3; i < (numItems * 2) + 3; i+=2)
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
	 * @param recipeChars - the array to process
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
		
		Character[] differentChars = foundChars.toArray(new Character[]{});
		char[] finalChars = new char[differentChars.length];
		for (int i = 0; i < differentChars.length; i++)
		{
			finalChars[i] = differentChars[i];
		}
		
		return finalChars;
	}

	/**
	 * Makes sure the passed item is not equal to any item in the array
	 * @param item - ItemStack to check equality with
	 * @param differentItems - Array of ItemStacks to compare to
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
	 * @param stack - ItemStack to find the character for
	 * @param map - HashMap object to search in
	 * @return the character associated
	 */
	private char getChar(ItemStack stack, Map<String, Character> map)
	{
		if (stack == null) return ' ';
		return map.get(stack.itemID + "##" + stack.getItemDamage());
	}
	
	//Unused as of yet
	@SuppressWarnings("unused")
	private String toStackString (ItemStack stack)
	{
		return stack.itemID + "##" + stack.getItemDamage();
	}
}
