package com.playtech.service;

import com.playtech.dto.PlayerInfo;

import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ReadPlayerInfoFromService {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<PlayerInfo> getPlayerInfo(String fileName) {
        final String DELIMITER = ",";

        List<PlayerInfo> playerInfoList = new ArrayList<>();
        Scanner scanner = new Scanner(getFileAsIOStream(fileName));

        scanner.useDelimiter(DELIMITER);

        scanner.nextLine();
        while (scanner.hasNext())
        {
            String x = scanner.nextLine();
            String[] attributes = x.split(DELIMITER);
            PlayerInfo playerInfo = createPlayerInfo(attributes);

            playerInfoList.add(playerInfo);
        }

        scanner.close();
        return playerInfoList;
    }

    private PlayerInfo createPlayerInfo(String[] metadata) {
        String code = metadata[0];
        Long playerId = Long.valueOf(metadata[1]);
        Double amount = Double.valueOf(metadata[2]);
        LocalDateTime eventDate = LocalDateTime.parse(metadata[3], formatter);
        String chapter = metadata[4];
        Integer partnerId = Integer.valueOf(metadata[5]);
        String gameName = metadata[6];
        Long sessionId = Long.valueOf(metadata[7]);

        return new PlayerInfo(code, playerId, amount, eventDate, chapter, partnerId, gameName, sessionId);
    }

    private InputStream getFileAsIOStream(final String fileName)
    {
        InputStream ioStream = this.getClass()
            .getClassLoader()
            .getResourceAsStream(fileName);

        if (ioStream == null) {
            throw new IllegalArgumentException(fileName + " is not found");
        }
        return ioStream;
    }
}
