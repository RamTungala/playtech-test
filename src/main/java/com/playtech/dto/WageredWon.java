package com.playtech.dto;

import java.util.Objects;

public class WageredWon {

    protected Long playerId;
    protected Double amountWagered;
    protected Double amountWon;

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public void setAmountWagered(Double amountWagered) {
        this.amountWagered = amountWagered;
    }

    public void setAmountWon(Double amountWon) {
        this.amountWon = amountWon;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public Double getAmountWagered() {
        return amountWagered;
    }

    public Double getAmountWon() {
        return amountWon;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WageredWon that = (WageredWon) o;
        return Objects.equals(playerId, that.playerId) &&
            Objects.equals(amountWagered, that.amountWagered) &&
            Objects.equals(amountWon, that.amountWon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(playerId, amountWagered, amountWon);
    }

    @Override
    public String toString() {
        return "WageredWon{" +
            "playerId=" + playerId +
            ", amountWagered=" + amountWagered +
            ", amountWon=" + amountWon +
            '}';
    }
}
