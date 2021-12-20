package com.playtech.service;

import com.playtech.builder.PlayerInfoTestDataBuilder;
import com.playtech.dto.PlayerGame;
import com.playtech.dto.PlayerInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GameWageredWonServiceTest {

    @Mock
    ReadPlayerInfoFromService readPlayerInfoFromService;

    @Mock
    WriteDataToCSV writeDataToCSV;

    GameWageredWonService gameWageredWonService;

    @Captor
    ArgumentCaptor<Set<PlayerGame>> argumentCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        gameWageredWonService = new GameWageredWonService(readPlayerInfoFromService, writeDataToCSV);
    }

    @Test
    void shouldSendCorrectValuesToWriteIntoCSVFile() {
        List<PlayerInfo> playerInfoList = PlayerInfoTestDataBuilder.build();
        when(readPlayerInfoFromService.getPlayerInfo(anyString())).thenReturn(playerInfoList);
        when(writeDataToCSV.writePlayerGameToCSV(anySet())).thenReturn(true);

        boolean actualResult = gameWageredWonService.generateCSV();

        verify(writeDataToCSV).writePlayerGameToCSV(argumentCaptor.capture());
        Set<PlayerGame> playerGames = argumentCaptor.getValue();

        Set<PlayerGame> playerGameSet = getPlayerGames();

        assertThat(playerGames).hasSameElementsAs(playerGameSet);
        assertTrue(actualResult);
    }

    @Test
    void shouldReturnTrueWhenCSVGeneratedSuccessfully() {
        when(readPlayerInfoFromService.getPlayerInfo(anyString()))
            .thenThrow(NullPointerException.class);

        boolean actualResult = gameWageredWonService.generateCSV();

        verify(writeDataToCSV, never()).writePlayerGameToCSV(anySet());

        assertFalse(actualResult);
    }

    private Set<PlayerGame> getPlayerGames() {
        PlayerGame playerGame1 = new PlayerGame();
        playerGame1.setGameName("Poker");
        playerGame1.setPlayerId(14345196L);
        playerGame1.setAmountWagered(40.0);
        playerGame1.setAmountWon(10.0);

        PlayerGame playerGame2 = new PlayerGame();
        playerGame2.setGameName("Poker");
        playerGame2.setPlayerId(14345194L);
        playerGame2.setAmountWagered(null);
        playerGame2.setAmountWon(27.0);
        return Set.of(playerGame1, playerGame2);
    }

}