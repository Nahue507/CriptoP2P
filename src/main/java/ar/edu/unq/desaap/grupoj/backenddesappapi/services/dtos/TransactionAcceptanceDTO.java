package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import javax.validation.constraints.NotBlank;

public class TransactionAcceptanceDTO {
    @NotBlank(message = "transactionId is mandatory")
    private Integer transactionId;

    @NotBlank(message = "userId is mandatory")
    private Integer userId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}