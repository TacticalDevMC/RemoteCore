package com.github.tacticaldevmc.remotecore.menus;

import com.github.tacticaldevmc.remotecore.player.ranks.RankData;
import com.github.tacticaldevmc.remotecore.utils.Randomizer;
import com.github.tacticaldevmc.remotecore.utils.inventory.ItemBuilder;
import com.github.tacticaldevmc.remotecore.utils.inventory.SkullBuilder;
import com.github.tacticaldevmc.remotecore.utils.menu.MenuCore;
import com.github.tacticaldevmc.remotecore.utils.Colors;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

public class RankListMenu {

    private final String INVENTORY_NAME = Colors.format("&bRanks List");
    private final Player player;
    private final MenuCore menu;

    public RankListMenu(Player player) {
        this.player = player;
        this.menu = new MenuCore(INVENTORY_NAME, 5);
    }

    private final Random random = new Random();
    private final String[] rankSkulls = new String[]{"http://textures.minecraft.net/texture/817da2736624d4a20cf2c731570f27c9ccf1a047cabe195aac5e8adcb026b1e0"};
    Randomizer<String> randomizer = new Randomizer<String>(Collections.singletonList(rankSkulls[rankSkulls.length]));

    public void open() {
        for (String rankName : RankData.getRanks()) {
            RankData rank = new RankData(rankName);

            ItemStack stack = new ItemBuilder(new SkullBuilder(1).setSkullSkin(randomizer.result())).setName("&3&l" + rank.getName().join())
                    .addLoreLines(
                            "",
                            "&7Prefix: &f" + rank.getPrefix().join(),
                            "&7Color: &f" + rank.getCreatedAt().join(),
                            "&7DiscordID: &f" + rank.getDiscordID().join(),
                            "&7Created At: &f" + rank.getCreatedAt().join(),
                            ""
                    ).toItemStack();
            List<ItemStack> stacks = new ArrayList<>();
            stacks.add(stack);
            menu.addItem(stacks);
        }

        menu.openMenu(player);
    }

}
