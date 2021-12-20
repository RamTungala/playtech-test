package com.playtech.service;

import com.playtech.dto.PlayerGame;
import com.playtech.dto.PlayerInfo;
import com.playtech.dto.PlayerSessionGame;
import com.playtech.util.PlayerUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionGameWageredWonService implements CSVGeneration {

    private final ReadPlayerInfoFromService readPlayerInfoFromService;
    private final WriteDataToCSV writeDataToCSV;

    public SessionGameWageredWonService(ReadPlayerInfoFromService readPlayerInfoFromService, WriteDataToCSV writeDataToCSV) {
        this.readPlayerInfoFromService = readPlayerInfoFromService;
        this.writeDataToCSV = writeDataToCSV;
    }

    @Override
    public boolean generateCSV() {
        try {
            List<PlayerInfo> playerInfoList = readPlayerInfoFromService.getPlayerInfo(GAME_EVENT_DATA_CSV);
            Set<PlayerSessionGame> sessionWageredAmountWonSet = getSessionWageredAmountWon(playerInfoList);
            return writeDataToCSV.writePlayerSessionGameToCSV(sessionWageredAmountWonSet);
        } catch (Exception e ) {
            System.out.printf("Exception occurred %s \n", e.getMessage());
        }
        return false;
    }

    private Set<PlayerSessionGame> getSessionWageredAmountWon(List<PlayerInfo> playerInfoList) {
        Set<PlayerSessionGame> playerSessionGameSet = new HashSet<>();
        Map<List<? extends Serializable>, Double> winMap = playerInfoList.stream()
            .filter(PlayerUtil.isPlayerWin())
            .collect(Collectors.groupingBy(playerInfo ->
                    Arrays.asList(playerInfo.getPlayerId(), playerInfo.getSessionId(), playerInfo.getGameName()),
                Collectors.summingDouble(PlayerInfo::getAmount)));
        winMap.forEach((key, value) -> {
            PlayerSessionGame playerSession = new PlayerSessionGame();
            playerSession.setPlayerId((Long)key.get(0));
            playerSession.setSessionId((Long)key.get(1));
            playerSession.setGameName((String)key.get(2));
            playerSession.setAmountWon(value);
            playerSessionGameSet.add(playerSession);

        });

        Map<List<? extends Serializable>, Double> betMap = playerInfoList.stream()
            .filter(PlayerUtil.isPlayerBet())
            .collect(Collectors.groupingBy(playerInfo ->
                    Arrays.asList(playerInfo.getPlayerId(), playerInfo.getSessionId(), playerInfo.getGameName()),
                Collectors.summingDouble(PlayerInfo::getAmount)));
        betMap.forEach((key, value) -> {
            final Long playerId = (Long) key.get(0);
            final Long sessionId = (Long) key.get(1);
            final String gameName = (String) key.get(2);
            Optional<PlayerSessionGame> playerSessionOptional = getPlayerSession(playerSessionGameSet,
                playerId,
                sessionId,
                gameName);
            PlayerSessionGame playerSessionGame;
            if(playerSessionOptional.isPresent()) {
                playerSessionGame = playerSessionOptional.get();
                playerSessionGame.setAmountWagered(value);
            } else {
                playerSessionGame = new PlayerSessionGame();
                playerSessionGame.setAmountWagered(value);
                playerSessionGame.setPlayerId(playerId);
                playerSessionGame.setSessionId(sessionId);
                playerSessionGame.setGameName(gameName);
            }
            playerSessionGameSet.add(playerSessionGame);

        });

        return playerSessionGameSet;
    }

    private Optional<PlayerSessionGame> getPlayerSession(final Set<PlayerSessionGame> playerSessionSet,
                                                        final Long playerId,
                                                        final Long sessionId,
                                                        final String gameName) {
        return playerSessionSet.stream()
            .filter(playerSession -> playerSession.getSessionId().equals(sessionId)
                && playerSession.getPlayerId().equals(playerId)
                && playerSession.getGameName().equals(gameName))
            .findFirst();
    }
}
