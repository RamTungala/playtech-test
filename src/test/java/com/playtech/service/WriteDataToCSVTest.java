package com.playtech.service;

import com.playtech.dto.PlayerGame;
import com.playtech.dto.PlayerSession;
import com.playtech.dto.PlayerSessionGame;
import com.playtech.dto.WageredWon;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class WriteDataToCSVTest {

    private WriteDataToCSV writeDataToCSV;

    @BeforeEach
    void setUp() {
        writeDataToCSV = new WriteDataToCSV();

    }

    @Test
    void shouldWritePlayerSessionToCSV() {
        PlayerSession playerSession = new PlayerSession();
        playerSession.setSessionId(324234L);
        playerSession.setPlayerId(1256L);
        playerSession.setAmountWagered(10.3);
        playerSession.setAmountWon(90.2);
        Set<PlayerSession> playerSessionSet= Set.of(playerSession);
        boolean actual = writeDataToCSV.writePlayerSessionToCSV(playerSessionSet);
        assertTrue(actual);
    }


    @Test
    void shouldWritePlayerGameToCSV() {
        PlayerGame playerGame = new PlayerGame();
        playerGame.setGameName("Poker");
        playerGame.setPlayerId(1256L);
        playerGame.setAmountWagered(10.3);
        playerGame.setAmountWon(90.2);
        Set<PlayerGame> playerGameSet= Set.of(playerGame);
        boolean actual = writeDataToCSV.writePlayerGameToCSV(playerGameSet);
        assertTrue(actual);
    }

    @Test
    void shouldWritePlayerSessionGameToCSV() {
        PlayerSessionGame playerSessionGame = new PlayerSessionGame();
        playerSessionGame.setGameName("Poker");
        playerSessionGame.setSessionId(324234L);
        playerSessionGame.setPlayerId(1256L);
        playerSessionGame.setAmountWagered(10.3);
        playerSessionGame.setAmountWon(90.2);
        Set<PlayerSessionGame> playerSessionGames= Set.of(playerSessionGame);
        boolean actual = writeDataToCSV.writePlayerSessionGameToCSV(playerSessionGames);
        assertTrue(actual);
    }

    @Test
    void shouldWriteWageredWonToCSV() {
        WageredWon wageredWon = new WageredWon();
        wageredWon.setPlayerId(1256L);
        wageredWon.setAmountWagered(10.3);
        wageredWon.setAmountWon(90.2);
        Set<WageredWon> wageredWonSet= Set.of(wageredWon);
        boolean actual = writeDataToCSV.writeWageredWonToCSV(wageredWonSet);
        assertTrue(actual);
    }

}