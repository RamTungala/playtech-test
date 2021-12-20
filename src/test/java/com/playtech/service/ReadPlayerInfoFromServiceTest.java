package com.playtech.service;

import com.playtech.builder.PlayerInfoTestDataBuilder;
import com.playtech.dto.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

public class ReadPlayerInfoFromServiceTest {

    ReadPlayerInfoFromService readPlayerInfoFromService;

    @BeforeEach
    void setUp() {
        readPlayerInfoFromService = new ReadPlayerInfoFromService();
    }

    @Test
    void shouldThrowIllegalArgumentExceptionIfFileNotFound() {
        assertThrows(IllegalArgumentException.class, () -> {
            readPlayerInfoFromService.getPlayerInfo("fileNotFound.csv");
        });
    }

    @Test
    void shouldConvertCSVDataToPlayerInfo() {
        List<PlayerInfo> playerInfoList = readPlayerInfoFromService.getPlayerInfo("Game_event_data.csv");

        String str = "2019-02-26 09:34:00";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);


        PlayerInfo playerInfo1 = new PlayerInfo("11400557", 14345194L,13.5, dateTime,
            "End session",11, "Poker" , 46628L);
        PlayerInfo playerInfo2 = new PlayerInfo( "11400556",14345194L,1.35, dateTime,
            "Loyalty",11,"Poker",46628L);


        assertThat(playerInfoList).hasSameElementsAs(Set.of(playerInfo1, playerInfo2));
    }


    @Test
    void shouldThrowExceptionIfDataInCSVCorrupted() {

        assertThrows(IllegalArgumentException.class, () -> {
            readPlayerInfoFromService.getPlayerInfo("Game_event_data_corrupted_data");
        });
    }
}