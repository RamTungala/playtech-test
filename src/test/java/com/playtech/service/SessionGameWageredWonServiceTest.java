package com.playtech.service;

import com.playtech.dto.PlayerInfo;
import com.playtech.dto.PlayerSessionGame;
import com.playtech.builder.PlayerInfoTestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SessionGameWageredWonServiceTest {

    @Mock
    ReadPlayerInfoFromService readPlayerInfoFromService;

    @Mock
    WriteDataToCSV writeDataToCSV;

    SessionGameWageredWonService sessionGameWageredWonService;

    @Captor
    ArgumentCaptor<Set<PlayerSessionGame>> argumentCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionGameWageredWonService = new SessionGameWageredWonService(readPlayerInfoFromService, writeDataToCSV);
    }

    @Test
    void shouldSendCorrectValuesToWriteIntoCSVFile() {
        List<PlayerInfo> playerInfoList = PlayerInfoTestDataBuilder.build();
        when(readPlayerInfoFromService.getPlayerInfo(anyString())).thenReturn(playerInfoList);
        when(writeDataToCSV.writePlayerSessionGameToCSV(anySet())).thenReturn(true);

        boolean actualResult = sessionGameWageredWonService.generateCSV();

        verify(writeDataToCSV).writePlayerSessionGameToCSV(argumentCaptor.capture());
        Set<PlayerSessionGame> playerGames = argumentCaptor.getValue();

        Set<PlayerSessionGame> playerGameSet = getPlayerSessionGames();

        assertThat(playerGames).hasSameElementsAs(playerGameSet);
        assertTrue(actualResult);
    }

    @Test
    void shouldReturnTrueWhenCSVGeneratedSuccessfully() {
        when(readPlayerInfoFromService.getPlayerInfo(anyString()))
            .thenThrow(NullPointerException.class);

        boolean actualResult = sessionGameWageredWonService.generateCSV();

        verify(writeDataToCSV, never()).writePlayerSessionGameToCSV(anySet());

        assertFalse(actualResult);
    }

    private Set<PlayerSessionGame> getPlayerSessionGames() {
        PlayerSessionGame playerSessionGame1 = new PlayerSessionGame();
        playerSessionGame1.setSessionId(46630L);
        playerSessionGame1.setGameName("Poker");
        playerSessionGame1.setPlayerId(14345196L);
        playerSessionGame1.setAmountWagered(40.0);
        playerSessionGame1.setAmountWon(10.0);

        PlayerSessionGame playerSessionGame2 = new PlayerSessionGame();
        playerSessionGame2.setSessionId(46628L);
        playerSessionGame2.setGameName("Poker");
        playerSessionGame2.setPlayerId(14345194L);
        playerSessionGame2.setAmountWagered(null);
        playerSessionGame2.setAmountWon(27.0);
        return Set.of(playerSessionGame1, playerSessionGame2);
    }

}