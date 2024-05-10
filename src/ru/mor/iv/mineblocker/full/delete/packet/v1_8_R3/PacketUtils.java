package ru.mor.iv.mineblocker.full.delete.packet.v1_8_R3;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;

import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftInventoryCustom;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.inventory.Inventory;

import net.minecraft.server.v1_8_R3.NBTCompressedStreamTools;
import net.minecraft.server.v1_8_R3.NBTTagList;
import net.minecraft.server.v1_8_R3.Container;
import net.minecraft.server.v1_8_R3.EntityHuman;
import net.minecraft.server.v1_8_R3.IInventory;
import net.minecraft.server.v1_8_R3.ItemStack;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.TileEntity;
import ru.mor.iv.mineblocker.config.string.Strings;
import ru.mor.iv.mineblocker.full.delete.packet.PacketUtilsInterface;

public class PacketUtils implements PacketUtilsInterface{

	@Override
	public String getTags(org.bukkit.block.Block block) {
		String s = Strings.NULL_NBT;
		if(block != null && !block.getType().equals(Material.AIR)){
			CraftWorld w = (CraftWorld) block.getWorld();
			TileEntity e = w.getTileEntityAt(block.getX(), block.getY(), block.getZ());
			if(e != null){
				NBTTagCompound n = new NBTTagCompound();
				e.b(n);
				if(n != null){
					s = n.toString();
				}
			}
		}
		return s;
	}

	@Override
	public String getTags(org.bukkit.inventory.ItemStack item) {
		String s = Strings.NULL_NBT;
		if(item != null && !item.getType().equals(Material.AIR)){
			ItemStack i = CraftItemStack.asNMSCopy(item);
			if(i != null){
				NBTTagCompound n = i.getTag();
				if(n != null){
					s = n.toString();
				}
			}
		}
		return s;
	}
	
	@Override
	public String getOpenInventoryName(org.bukkit.entity.Player p) {
		return getPlayerContainer(p).getClass().getName();
	}
	
	private Container getPlayerContainer(org.bukkit.entity.Player p) {
		return getNMSPlayer(p).activeContainer;
	}

	private EntityHuman getNMSPlayer(org.bukkit.entity.Player p) {
		CraftPlayer cplayer = (CraftPlayer) p;
		EntityHuman nmshuman = cplayer.getHandle();
		return nmshuman;
	}
	
	@Override
	public boolean hasInventory(org.bukkit.block.Block b) {
		CraftWorld cworld = (CraftWorld) b.getWorld();
		TileEntity te = cworld.getTileEntityAt(b.getX(), b.getY(), b.getZ());
		return te instanceof IInventory;
	}

	@Override
	public boolean hasInventory(org.bukkit.entity.Entity e) {
		CraftEntity centity = (CraftEntity) e;
		return centity.getHandle() instanceof IInventory;
	}
	
	@Override
	public String toString(org.bukkit.inventory.ItemStack[] inventory) throws IOException {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        NBTTagList itemList = new NBTTagList();
        for (int i = 0; i < inventory.length; i++) {
            NBTTagCompound outputObject = new NBTTagCompound();
            boolean b = false;
            if(inventory[i] != null) {
            	b = inventory[i].getType() == Material.AIR;
            }
            if(b) {
            	inventory[i] = null;
            }
            CraftItemStack craft = getCraftVersion(inventory[i]);
            if (craft != null)
                CraftItemStack.asNMSCopy(craft).save(outputObject);

            itemList.add(outputObject);
        }

        NBTTagCompound whole = new NBTTagCompound();
        whole.set("Inventory", itemList);
		NBTCompressedStreamTools.a(whole, outputStream);
		
        return new BigInteger(1, outputStream.toByteArray()).toString(32);
    }
	
	@Override
    public Inventory fromString(String data) throws IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(new BigInteger(data, 32).toByteArray());
        NBTTagList itemList = NBTCompressedStreamTools.a(inputStream).getList("Inventory", 10);
        Inventory inventory = new CraftInventoryCustom(null, itemList.size());

        for (int i = 0; i < itemList.size(); i++) {
            NBTTagCompound inputObject = itemList.get(i);
            if (!inputObject.isEmpty()) {
                inventory.setItem(i, CraftItemStack.asBukkitCopy(ItemStack.createStack(inputObject)));
            }
        }
        return inventory;
    }

    private CraftItemStack getCraftVersion(org.bukkit.inventory.ItemStack stack) {
        if (stack instanceof CraftItemStack)
            return (CraftItemStack) stack;
        else if (stack != null)
            return CraftItemStack.asCraftCopy(stack);
        else
            return null;
    }
}
