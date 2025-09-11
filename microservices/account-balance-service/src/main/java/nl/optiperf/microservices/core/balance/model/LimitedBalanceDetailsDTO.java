package nl.optiperf.microservices.core.balance.model;

import java.math.BigDecimal;

public class LimitedBalanceDetailsDTO {

    private BigDecimal currentBalance;
    private BigDecimal availableBalance;

    public LimitedBalanceDetailsDTO(BigDecimal currentBalance, BigDecimal availableBalance) {
        this.currentBalance = currentBalance;
        this.availableBalance = availableBalance;
    }

    public BigDecimal getCurrentBalance() {
        return currentBalance;
    }

    public void setCurrentBalance(BigDecimal currentBalance) {
        this.currentBalance = currentBalance;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }
}