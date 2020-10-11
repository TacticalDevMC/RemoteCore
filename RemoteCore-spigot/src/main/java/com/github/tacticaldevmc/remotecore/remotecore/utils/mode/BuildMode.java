package com.github.tacticaldevmc.remotecore.remotecore.utils.mode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BuildMode {

    SNAPSHOT("SNAPSHOT", false), DEV("DEV", true), OUTDATED("OUTDATED", false), BETA("BETA", false);

    private String mode;
    private boolean active;

}
