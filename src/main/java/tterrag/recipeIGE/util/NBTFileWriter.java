package tterrag.recipeIGE.util;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;

public class NBTFileWriter
{
	private static File cfg;

	public enum RecipeTypes
	{
		Shapeless, Shaped, OreShapeless, OreShaped, ;
	}

	public static void init(File sFile)
	{
		cfg = sFile;
	}

	public static void writeRecipeAddition(NBTBase tag, RecipeTypes r) throws IOException
	{
		DataOutputStream dataOut = null;
		File file = new File(cfg.getParent() + "/recipeConf.txt");

		if (!file.exists())
		{
			file.createNewFile();
		}

		dataOut = new DataOutputStream(new FileOutputStream(file, true));

		dataOut.writeUTF("a:" + r.name());

		NBTBase.writeNamedTag(tag, dataOut);

		dataOut.close();
	}

	public static void writeRecipeDeletion(NBTBase tag, RecipeTypes r) throws IOException
	{
		DataOutputStream dataOut = null;
		File file = new File(cfg.getParent() + "/recipeConf.txt");

		if (!file.exists())
		{
			file.createNewFile();
		}

		dataOut = new DataOutputStream(new FileOutputStream(file, true));
		
		dataOut.writeUTF("d:" + r.name());
		
		NBTBase.writeNamedTag(tag, dataOut);
		
		dataOut.close();
	}
}
