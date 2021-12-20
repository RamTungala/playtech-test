package com.playtech.util;

import com.playtech.dto.PlayerInfo;

import java.util.function.Predicate;

public class PlayerUtil {

    public static Predicate<PlayerInfo> isPlayerWin() {
        return playerInfo -> playerInfo.getChapter().equals("Win");
    }

    public static Predicate<PlayerInfo> isPlayerBet() {
        return playerInfo -> playerInfo.getChapter().equals("Bet");
    }
}
