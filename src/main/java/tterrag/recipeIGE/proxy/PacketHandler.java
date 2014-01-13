package tterrag.recipeIGE.proxy;

import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import tterrag.recipeIGE.RecipeIGE;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class PacketHandler implements IPacketHandler
{
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player)
	{
		switch (packet.data[0])
		{
		case 0:
			EntityPlayerMP playerMP = (EntityPlayerMP) player;
			playerMP.openGui(RecipeIGE.instance, 0, playerMP.worldObj, 0, 0, 0);
			break;
		}
	}
}
