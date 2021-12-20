package com.playtech.service;

import com.playtech.dto.PlayerInfo;
import com.playtech.dto.PlayerSession;
import com.playtech.util.PlayerUtil;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class SessionWageredWonService implements CSVGeneration {

    private final ReadPlayerInfoFromService readPlayerInfoFromService;
    private final WriteDataToCSV writeDataToCSV;

    public SessionWageredWonService(ReadPlayerInfoFromService readPlayerInfoFromService, WriteDataToCSV writeDataToCSV) {
        this.readPlayerInfoFromService = readPlayerInfoFromService;
        this.writeDataToCSV = writeDataToCSV;
    }

    @Override
    public boolean generateCSV() {
        try {
            List<PlayerInfo> playerInfoList = readPlayerInfoFromService.getPlayerInfo(GAME_EVENT_DATA_CSV);
            Set<PlayerSession> sessionWageredAmountWonSet = getSessionWageredAmountWon(playerInfoList);
            return writeDataToCSV.writePlayerSessionToCSV(sessionWageredAmountWonSet);
        } catch (Exception e ) {
            System.out.printf("Exception occurred %s \n", e.getMessage());
        }
        return false;
    }

    private Set<PlayerSession> getSessionWageredAmountWon(List<PlayerInfo> playerInfoList) {
        Set<PlayerSession> playerSessionSet = new HashSet<>();
        Map<List<Long>, Double> winMap = playerInfoList.stream()
            .filter(PlayerUtil.isPlayerWin())
            .collect(Collectors.groupingBy(playerInfo ->
                    Arrays.asList(playerInfo.getPlayerId(), playerInfo.getSessionId()),
                Collectors.summingDouble(PlayerInfo::getAmount)));
        winMap.forEach((key, value) -> {
            PlayerSession playerSession = new PlayerSession();
            playerSession.setPlayerId(key.get(0));
            playerSession.setSessionId(key.get(1));
            playerSession.setAmountWon(value);
            playerSessionSet.add(playerSession);

        });

        Map<List<Long>, Double> betMap = playerInfoList.stream()
            .filter(PlayerUtil.isPlayerBet())
            .collect(Collectors.groupingBy(playerInfo ->
                    Arrays.asList(playerInfo.getPlayerId(), playerInfo.getSessionId()),
                Collectors.summingDouble(PlayerInfo::getAmount)));
        betMap.forEach((key, value) -> {
            Optional<PlayerSession> playerSessionOptional = getPlayerSession(playerSessionSet, key.get(0), key.get(1));
            PlayerSession playerSession;
            if(playerSessionOptional.isPresent()) {
                playerSession = playerSessionOptional.get();
                playerSession.setAmountWagered(value);
            } else {
                playerSession = new PlayerSession();
                playerSession.setAmountWagered(value);
                playerSession.setPlayerId(key.get(0));
                playerSession.setSessionId(key.get(1));
            }
            playerSessionSet.add(playerSession);

        });

        return playerSessionSet;
    }

    private Optional<PlayerSession> getPlayerSession(final Set<PlayerSession> playerSessionSet,
                                                    final Long playerId,
                                                    final Long sessionId){
        return playerSessionSet.stream()
            .filter(playerSession -> playerSession.getSessionId().equals(sessionId) && playerSession.getPlayerId().equals(playerId))
            .findFirst();

    }
}
