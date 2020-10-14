package com.github.tacticaldevmc.remotecore.utils.menu.interfaces;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

public interface MenuClick {

    void onItemClick(Player player, ClickType clickType);
}