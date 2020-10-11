package com.github.tacticaldevmc.remotecore.remotecore.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FlagUtil {

    /**
     * Check if a flag exists in the command arguments and remove it from the original array.
     *
     * @param args the array of arguments.
     * @param flag the flag to check for.
     * @return true if the flag exists.
     */
    public static boolean hasFlag(String[] args, char flag) {

        List<String> list = new ArrayList(Arrays.asList(args));

        for (Iterator<String> it = list.iterator(); it.hasNext(); ) {
            if (it.next().equalsIgnoreCase("-" + flag)) {
                it.remove();
                args = list.toArray(args);
                return true;
            }
        }

        return false;
    }

}