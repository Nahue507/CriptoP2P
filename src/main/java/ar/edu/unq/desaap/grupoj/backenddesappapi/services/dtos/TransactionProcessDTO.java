package ar.edu.unq.desaap.grupoj.backenddesappapi.services.dtos;

import javax.validation.constraints.NotBlank;

public class TransactionProcessDTO {
    @NotBlank(message = "transactionId is mandatory")
    public Integer transactionId;

    @NotBlank(message = "userId is mandatory")
    public Integer userId;
}