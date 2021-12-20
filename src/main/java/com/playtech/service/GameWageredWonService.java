package com.playtech.service;

import com.playtech.dto.PlayerGame;
import com.playtech.dto.PlayerInfo;
import com.playtech.util.PlayerUtil;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class GameWageredWonService implements CSVGeneration {

    private final ReadPlayerInfoFromService readPlayerInfoFromService;
    private final WriteDataToCSV writeDataToCSV;

    public GameWageredWonService(ReadPlayerInfoFromService readPlayerInfoFromService, WriteDataToCSV writeDataToCSV) {
        this.readPlayerInfoFromService = readPlayerInfoFromService;
        this.writeDataToCSV = writeDataToCSV;
    }

    @Override
    public boolean generateCSV() {
        try {
            List<PlayerInfo> playerInfoList = readPlayerInfoFromService.getPlayerInfo(GAME_EVENT_DATA_CSV);
            Set<PlayerGame> sessionWageredAmountWonSet = getSessionWageredAmountWon(playerInfoList);
            return writeDataToCSV.writePlayerGameToCSV(sessionWageredAmountWonSet);
        } catch (Exception e ) {
            System.out.printf("Exception occurred %s \n", e.getMessage());
        }
        return false;
    }

    private Set<PlayerGame> getSessionWageredAmountWon(List<PlayerInfo> playerInfoSet) {
        Set<PlayerGame> playerGameSet = new HashSet<>();
        Map<List<? extends Serializable>, Double> winList = playerInfoSet.stream()
            .filter(PlayerUtil.isPlayerWin())
            .collect(Collectors.groupingBy(playerInfo ->
                    Arrays.asList(playerInfo.getPlayerId(), playerInfo.getGameName()),
                Collectors.summingDouble(PlayerInfo::getAmount)));
        winList.forEach((key, value) -> {
            PlayerGame playerGame = new PlayerGame();
            playerGame.setPlayerId((Long) key.get(0));
            playerGame.setGameName((String) key.get(1));
            playerGame.setAmountWon(value);
            playerGameSet.add(playerGame);

        });

        Map<List<? extends Serializable>, Double> betList = playerInfoSet.stream()
            .filter(PlayerUtil.isPlayerBet())
            .collect(Collectors.groupingBy(playerInfo ->
                    Arrays.asList(playerInfo.getPlayerId(), playerInfo.getGameName()),
                Collectors.summingDouble(PlayerInfo::getAmount)));
        betList.forEach((key, value) -> {
            final Long playerId =(Long) key.get(0);
            final String gameName = (String) key.get(1);
            Optional<PlayerGame> playerGameOptional = getPlayerSession(playerGameSet, playerId, gameName);
            PlayerGame playerGame;
            if(playerGameOptional.isPresent()) {
                playerGame = playerGameOptional.get();
                playerGame.setAmountWagered(value);
            } else {
                playerGame = new PlayerGame();
                playerGame.setAmountWagered(value);
                playerGame.setPlayerId(playerId);
                playerGame.setGameName(gameName);
            }
            playerGameSet.add(playerGame);

        });

        return playerGameSet;
    }

    private Optional<PlayerGame> getPlayerSession(final Set<PlayerGame> playerGameSet, final Long playerId, final String gameName){
        return playerGameSet.stream()
            .filter(playerSession -> playerSession.getGameName().equals(gameName) && playerSession.getPlayerId().equals(playerId))
            .findFirst();

    }
}
