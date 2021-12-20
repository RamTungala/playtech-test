package com.playtech.service;

import com.playtech.dto.PlayerInfo;
import com.playtech.dto.WageredWon;
import com.playtech.util.PlayerUtil;

import java.io.Serializable;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Top5ProfitablePlayersForCompanyService implements CSVGeneration {

    private final ReadPlayerInfoFromService readPlayerInfoFromService;
    private final WriteDataToCSV writeDataToCSV;

    public Top5ProfitablePlayersForCompanyService(ReadPlayerInfoFromService readPlayerInfoFromService, WriteDataToCSV writeDataToCSV) {
        this.readPlayerInfoFromService = readPlayerInfoFromService;
        this.writeDataToCSV = writeDataToCSV;
    }

    @Override
    public boolean generateCSV() {
        try {
            List<PlayerInfo> playerInfoList = readPlayerInfoFromService.getPlayerInfo(GAME_EVENT_DATA_CSV);
            Set<WageredWon> sessionWageredAmountWonSet = getSessionWageredAmountWon(playerInfoList);
            return writeDataToCSV.writeWageredWonToCSV(sessionWageredAmountWonSet);
        } catch (Exception e ) {
            System.out.printf("Exception occurred %s \n", e.getMessage());
        }
        return false;
    }

    private Set<WageredWon> getSessionWageredAmountWon(List<PlayerInfo> playerInfoSet) {
        Set<WageredWon> wageredWonSet = new LinkedHashSet<>();
        Map<List<? extends Serializable>, Double> betList = playerInfoSet.stream()
            .filter(PlayerUtil.isPlayerBet())
            .collect(Collectors.groupingBy(playerInfo ->
                    Collections.singletonList(playerInfo.getPlayerId()),
                Collectors.summingDouble(PlayerInfo::getAmount)))
            // sort values
            .entrySet().stream()
            .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
            .limit(5)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                (oldValue, newValue) -> oldValue, LinkedHashMap::new));
        betList.forEach((key, value) -> {
            final Long playerId =(Long) key.get(0);
            WageredWon wageredWon = new WageredWon();
            wageredWon.setAmountWagered(value);
            wageredWon.setPlayerId(playerId);
            wageredWonSet.add(wageredWon);

        });

        return wageredWonSet;
    }
}
