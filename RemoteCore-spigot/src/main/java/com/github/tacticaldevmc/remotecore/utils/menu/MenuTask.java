package com.github.tacticaldevmc.remotecore.utils.menu;

import com.github.tacticaldevmc.remotecore.utils.menu.interfaces.Menu;
import com.google.common.collect.Lists;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.util.Iterator;
import java.util.List;

public class MenuTask implements Runnable {
    private static final List<Menu> autoUpdateInventories = Lists.newCopyOnWriteArrayList();

    public MenuTask() {
    }

    @SuppressWarnings("deprecation")
	public void run() {
        Iterator<Menu> var1 = autoUpdateInventories.iterator();

        while (true) {
            Menu inv;
            do {
                if (!var1.hasNext()) {
                    return;
                }

                inv = var1.next();
            } while (inv.getInventory().getViewers().isEmpty());

            inv.getInventory().clear();
            inv.update();
            Iterator<?> var3 = inv.getInventory().getViewers().iterator();

            while (var3.hasNext()) {
                HumanEntity entity = (HumanEntity) var3.next();
                Player p = (Player) entity;
                p.updateInventory();
            }
        }
    }

    public static List<Menu> getAutoUpdateInventories() {
        return autoUpdateInventories;
    }
}