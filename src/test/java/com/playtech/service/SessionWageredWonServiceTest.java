package com.playtech.service;

import com.playtech.builder.PlayerInfoTestDataBuilder;
import com.playtech.dto.PlayerInfo;
import com.playtech.dto.PlayerSession;
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

public class SessionWageredWonServiceTest {

    @Mock
    ReadPlayerInfoFromService readPlayerInfoFromService;

    @Mock
    WriteDataToCSV writeDataToCSV;

    SessionWageredWonService sessionWageredWonService;

    @Captor
    ArgumentCaptor<Set<PlayerSession>> argumentCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sessionWageredWonService = new SessionWageredWonService(readPlayerInfoFromService, writeDataToCSV);
    }

    @Test
    void shouldSendCorrectValuesToWriteIntoCSVFile() {
        List<PlayerInfo> playerInfoList = PlayerInfoTestDataBuilder.build();
        when(readPlayerInfoFromService.getPlayerInfo(anyString())).thenReturn(playerInfoList);
        when(writeDataToCSV.writePlayerSessionToCSV(anySet())).thenReturn(true);

        boolean actualResult = sessionWageredWonService.generateCSV();

        verify(writeDataToCSV).writePlayerSessionToCSV(argumentCaptor.capture());
        Set<PlayerSession> playerSessions = argumentCaptor.getValue();

        Set<PlayerSession> playerSessionSet = getPlayerSessions();

        assertThat(playerSessions).hasSameElementsAs(playerSessionSet);
        assertTrue(actualResult);
    }

    @Test
    void shouldReturnTrueWhenCSVGeneratedSuccessfully() {
        when(readPlayerInfoFromService.getPlayerInfo(anyString()))
            .thenThrow(NullPointerException.class);

        boolean actualResult = sessionWageredWonService.generateCSV();

        verify(writeDataToCSV, never()).writePlayerSessionToCSV(anySet());

        assertFalse(actualResult);
    }

    private Set<PlayerSession> getPlayerSessions() {
        PlayerSession playerSession1 = new PlayerSession();
        playerSession1.setSessionId(46630L);
        playerSession1.setPlayerId(14345196L);
        playerSession1.setAmountWagered(40.0);
        playerSession1.setAmountWon(10.0);

        PlayerSession playerSession2 = new PlayerSession();
        playerSession2.setSessionId(46628L);
        playerSession2.setPlayerId(14345194L);
        playerSession2.setAmountWagered(null);
        playerSession2.setAmountWon(27.0);
        return Set.of(playerSession1, playerSession2);
    }


}