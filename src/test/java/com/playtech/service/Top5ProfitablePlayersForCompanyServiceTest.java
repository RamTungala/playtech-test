package com.playtech.service;

import com.playtech.builder.PlayerInfoTestDataBuilder;
import com.playtech.dto.PlayerInfo;
import com.playtech.dto.WageredWon;
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

public class Top5ProfitablePlayersForCompanyServiceTest {

    @Mock
    ReadPlayerInfoFromService readPlayerInfoFromService;

    @Mock
    WriteDataToCSV writeDataToCSV;

    Top5ProfitablePlayersForCompanyService top5ProfitablePlayersForCompanyService;

    @Captor
    ArgumentCaptor<Set<WageredWon>> argumentCaptor;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        top5ProfitablePlayersForCompanyService = new Top5ProfitablePlayersForCompanyService(readPlayerInfoFromService, writeDataToCSV);
    }

    @Test
    void shouldSendCorrectValuesToWriteIntoCSVFile() {
        List<PlayerInfo> playerInfoList = PlayerInfoTestDataBuilder.build();
        when(readPlayerInfoFromService.getPlayerInfo(anyString())).thenReturn(playerInfoList);
        when(writeDataToCSV.writeWageredWonToCSV(anySet())).thenReturn(true);

        boolean actualResult = top5ProfitablePlayersForCompanyService.generateCSV();

        verify(writeDataToCSV).writeWageredWonToCSV(argumentCaptor.capture());
        Set<WageredWon> wageredWins = argumentCaptor.getValue();

        Set<WageredWon> wageredWonSet = getWageredWons();

        assertThat(wageredWins).hasSameElementsAs(wageredWonSet);
        assertTrue(actualResult);
    }

    @Test
    void shouldReturnTrueWhenCSVGeneratedSuccessfully() {
        when(readPlayerInfoFromService.getPlayerInfo(anyString()))
            .thenThrow(NullPointerException.class);

        boolean actualResult = top5ProfitablePlayersForCompanyService.generateCSV();

        verify(writeDataToCSV, never()).writeWageredWonToCSV(anySet());

        assertFalse(actualResult);
    }

    private Set<WageredWon> getWageredWons() {
        WageredWon wageredWon1 = new WageredWon();
        wageredWon1.setPlayerId(14345196L);
        wageredWon1.setAmountWagered(40.0);
        wageredWon1.setAmountWon(null);

        return Set.of(wageredWon1);
    }


}