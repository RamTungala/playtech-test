package com.playtech.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class PlayerInfo {

    private String code;
    private Long playerId;
    private Double amount;
    private LocalDateTime eventDate;
    private String chapter;
    private Integer partnerId;
    private String gameName;
    private Long sessionId;

    public PlayerInfo(String code,
                      Long playerId,
                      Double amount,
                      LocalDateTime eventDate,
                      String chapter,
                      Integer partnerId,
                      String gameName,
                      Long sessionId) {
        this.code = code;
        this.playerId = playerId;
        this.amount = amount;
        this.eventDate = eventDate;
        this.chapter = chapter;
        this.partnerId = partnerId;
        this.gameName = gameName;
        this.sessionId = sessionId;
    }

    public String getCode() {
        return code;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Double getAmount() {
        return amount;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public String getChapter() {
        return chapter;
    }

    public Integer getPartnerId() {
        return partnerId;
    }

    public String getGameName() {
        return gameName;
    }

    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerInfo that = (PlayerInfo) o;
        return code.equals(that.code) &&
            playerId.equals(that.playerId) &&
            amount.equals(that.amount) &&
            eventDate.equals(that.eventDate) &&
            chapter.equals(that.chapter) &&
            partnerId.equals(that.partnerId) &&
            gameName.equals(that.gameName) &&
            sessionId.equals(that.sessionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, playerId, amount, eventDate, chapter, partnerId, gameName, sessionId);
    }
}
