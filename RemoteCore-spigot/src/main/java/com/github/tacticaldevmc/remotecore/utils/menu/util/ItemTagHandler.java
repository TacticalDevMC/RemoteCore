package com.github.tacticaldevmc.remotecore.utils.menu.util;

import net.minecraft.server.v1_15_R1.ItemStack;
import net.minecraft.server.v1_15_R1.NBTTagCompound;
import org.bukkit.craftbukkit.v1_15_R1.inventory.CraftItemStack;

public class ItemTagHandler {

    private static NBTTagCompound getTag(org.bukkit.inventory.ItemStack item) {

        ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag;
        if (itemNms.hasTag()) {
            tag = itemNms.getTag();
        } else {
            tag = new NBTTagCompound();
        }

        return tag;
    }

    private static org.bukkit.inventory.ItemStack setTag(org.bukkit.inventory.ItemStack item, NBTTagCompound tag) {

        ItemStack itemNms = CraftItemStack.asNMSCopy(item);
        itemNms.setTag(tag);
        return CraftItemStack.asBukkitCopy(itemNms);
    }

    public static org.bukkit.inventory.ItemStack addString(org.bukkit.inventory.ItemStack item, String name, String value) {

        NBTTagCompound tag = getTag(item);
        tag.setString(name, value);
        return setTag(item, tag);
    }

    public static boolean hasString(org.bukkit.inventory.ItemStack item, String name) {

        NBTTagCompound tag = getTag(item);
        return tag.hasKey(name);
    }

    public static String getString(org.bukkit.inventory.ItemStack item, String name) {

        NBTTagCompound tag = getTag(item);
        return tag.getString(name);
    }

    public static org.bukkit.inventory.ItemStack removeString(org.bukkit.inventory.ItemStack itemStack, String name) {

        NBTTagCompound tag = getTag(itemStack);
        tag.remove(name);
        return setTag(itemStack, tag);
    }
}