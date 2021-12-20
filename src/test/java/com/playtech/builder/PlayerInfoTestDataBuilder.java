package com.playtech.builder;

import com.playtech.dto.PlayerInfo;

import java.time.LocalDateTime;
import java.util.List;

public class PlayerInfoTestDataBuilder {
    public static List<PlayerInfo> build() {

        PlayerInfo playerInfo1 = new PlayerInfo("11400557", 14345194L,13.5, LocalDateTime.now(),
            "End session",11, "Poker" , 46628L);
        PlayerInfo playerInfo2 = new PlayerInfo( "11400556",14345194L,1.35,LocalDateTime.now(),
            "Loyalty",11,"Poker",46628L);
        PlayerInfo playerInfo3 = new PlayerInfo("11400555", 14345194L,13.5, LocalDateTime.now(),
            "Win",11, "Poker" , 46628L);
        PlayerInfo playerInfo4 = new PlayerInfo( "11400556",14345194L,1.35,LocalDateTime.now(),
            "Loyalty",11,"Poker",46628L);
        PlayerInfo playerInfo5 = new PlayerInfo("11400558", 14345194L,13.5, LocalDateTime.now(),
            "Win",11, "Poker" , 46628L);
        PlayerInfo playerInfo6 = new PlayerInfo( "11400568",14345196L,10.0,LocalDateTime.now(),
            "Bet",11,"Poker",46630L);
        PlayerInfo playerInfo7 = new PlayerInfo("11400569", 14345196L,10.0, LocalDateTime.now(),
            "Bet",11, "Poker" , 46630L);
        PlayerInfo playerInfo8 = new PlayerInfo( "11400570",14345196L,10.0,LocalDateTime.now(),
            "Bet",11,"Poker",46630L);
        PlayerInfo playerInfo9 = new PlayerInfo("11400571", 14345196L,10.0, LocalDateTime.now(),
            "Bet",11, "Poker" , 46630L);
        PlayerInfo playerInfo10 = new PlayerInfo( "11400572",14345196L,10.0,LocalDateTime.now(),
            "Win",11,"Poker",46630L);

        return List.of(playerInfo1, playerInfo2, playerInfo3, playerInfo4, playerInfo5,
            playerInfo6, playerInfo7, playerInfo8, playerInfo9, playerInfo10);

    }
}
