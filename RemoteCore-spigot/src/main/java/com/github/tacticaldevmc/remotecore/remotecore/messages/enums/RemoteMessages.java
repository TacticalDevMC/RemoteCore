package com.github.tacticaldevmc.remotecore.remotecore.messages.enums;

import com.github.tacticaldevmc.remotecore.remotecore.ICore;
import com.github.tacticaldevmc.remotecore.remotecore.RemoteCoreSpigot;
import com.github.tacticaldevmc.remotecore.remotecore.messages.MessagesHandler;
import com.github.tacticaldevmc.remotecore.remotecore.settings.interfaces.ISettings;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.command.CommandSender;

import static com.github.tacticaldevmc.remotecore.remotecore.utils.Colors.format;

/**
 * @AUTHOR: TacticalDev
 * Copyright © 2020, Joran Huibers, All rights reserved.
 */

@AllArgsConstructor
@Getter
public enum RemoteMessages {

    // GENERAL
    PLAYER_NOT_IN_DATABASE("commands.playerNotInDatabase"),

    // MAIN
    MAIN_USER_ARGS("commands.main.user.args"),
    MAIN_USER_RANK_SET("commands.main.user.rank.rankSet"),

    // RANK
    RANK_ALREADY_EXISTS("commands.rank.rankAlreadyExists"),
    RANK_NOT_EXISTS("commands.rank.rankNotExists"),
    RANK_CREATE_ARGS("commands.rank.create.args"),
    RANK_DELETE_ARGS("commands.rank.delete.args"),
    RANK_EDIT_ARGS("commands.rank.edit.args"),
    RANK_INFO_ARGS("commands.rank.info.args"),
    RANK_LIST_NO_RANKS("commands.rank.list.noRanks"),
    RANK_EDIT_MENU_OPENED("commands.rank.edit.editMenuOpened"),
    RANK_EDIT_MENU_CHANGE_NAME_TEXT("commands.rank.edit.changeName.text"),
    RANK_EDIT_MENU_CHANGE_PREFIX_TEXT("commands.rank.edit.changePrefix.text"),
    RANK_EDIT_MENU_CHANGE_COLOR_TEXT("commands.rank.edit.changeColor.text"),
    RANK_EDIT_MENU_CHOOSE_SECTION("commands.rank.edit.chooseSectionsToContinue"),
    RANK_EDIT_MENU_STOPPED("commands.rank.edit.stopped"),
    RANK_EDIT_STOPPED("commands.rank.edit.editCanceled"),
    RANK_EDIT_EDITTED("commands.rank.edit.editted"),
    RANK_CREATE_COLOR_MUST_START_WITH("commands.rank.create.colorMustStartWith"),
    RANK_CREATE_CREATED("commands.rank.create.rankCreated"),
    RANK_DELETE_DELETED("commands.rank.delete.rankDeleted");

    private final String path;

    @Override
    public String toString() {
        ICore core = RemoteCoreSpigot.getInstance();
        ISettings settings = core.getSettings();

        MessagesHandler messageHandler = core.getMessageHandler();

        return format(messageHandler.getConfig().getConfiguration().getString(path)).replace("%prefix%", settings.getPrefix());
    }

    public void send(CommandSender sender) {
        sender.sendMessage(toString());
    }

    public void send(CommandSender sender, String replacement) {
        ICore core = RemoteCoreSpigot.getInstance();
        ISettings settings = core.getSettings();
        sender.sendMessage(toString().replace("%s", format(replacement)).replace("%prefix%", settings.getPrefix()));
    }

    public void send(CommandSender sender, Object... object) {
        sender.sendMessage(String.format(toString(), object));
    }


}
