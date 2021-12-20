package com.playtech.dto;

import java.util.Objects;

public class PlayerSession extends WageredWon{

    private Long sessionId;

    public void setSessionId(Long sessionId) {
        this.sessionId = sessionId;
    }

    public Long getSessionId() {
        return sessionId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerSession that = (PlayerSession) o;
        return Objects.equals(sessionId, that.sessionId) && Objects.equals(playerId, that.getPlayerId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(sessionId, playerId);
    }

    @Override
    public String toString() {
        return "PlayerSession{" +
            " sessionId=" + sessionId +
            " playerId=" + playerId +
            " Amount Wagered=" + amountWagered +
            " Amount Won=" + amountWon +
            " }";
    }
}
