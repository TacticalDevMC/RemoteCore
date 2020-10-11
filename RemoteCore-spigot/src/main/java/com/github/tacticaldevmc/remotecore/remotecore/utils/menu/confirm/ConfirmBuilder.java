package com.github.tacticaldevmc.remotecore.remotecore.utils.menu.confirm;

import com.github.tacticaldevmc.remotecore.remotecore.utils.inventory.ItemBuilder;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.MenuCore;
import com.github.tacticaldevmc.remotecore.remotecore.utils.menu.interfaces.MenuClick;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public abstract class ConfirmBuilder {

    public abstract ConfirmMiddleItem getMiddleItem();

    public abstract ConfirmItem getConfirm();

    public abstract ConfirmItem getDeny();

    public void openConfirmMenu(Player player) {

        MenuCore menu = new MenuCore("Weet je het zeker?", 3);

        menu.addMenuClick(new ItemBuilder(Material.LEGACY_STAINED_GLASS_PANE, 1, (byte) 5).setName("&a&lJa!").setLore(getConfirm().getLore()).toItemStack(), getConfirm().getOnClick(), 11);
        menu.addMenuClick(getMiddleItem().itemBuilder.setLore(getMiddleItem().getLore()).toItemStack(), getMiddleItem().getOnClick(), 13);
        menu.addMenuClick(new ItemBuilder(Material.LEGACY_STAINED_GLASS_PANE, 1, (byte) 14).setName("&c&lNee!").setLore(getDeny().getLore()).toItemStack(), getDeny().getOnClick(), 15);

        menu.openMenu(player);
    }

    public ConfirmBuilder apply() {

        return this;
    }

    public static class ConfirmItem {

        protected String[] lore;
        protected MenuClick onClick;

        public String[] getLore() {
            return lore;
        }

        public void setLore(String... lore) {
            this.lore = lore;
        }

        public void setOnClick(MenuClick onClick) {
            this.onClick = onClick;
        }

        public MenuClick getOnClick() {
            return onClick;
        }
    }

    public static class ConfirmMiddleItem {

        protected ItemBuilder itemBuilder;
        protected String[] lore;
        protected MenuClick onClick;

        public ConfirmMiddleItem(ItemBuilder itemBuilder) {
            this.itemBuilder = itemBuilder;
        }

        public ItemBuilder getItemBuilder() {
            return itemBuilder;
        }

        public String[] getLore() {
            return lore;
        }

        public void setLore(String... lore) {
            this.lore = lore;
        }

        public void setOnClick(MenuClick onClick) {
            this.onClick = onClick;
        }

        public MenuClick getOnClick() {
            return onClick;
        }
    }

}