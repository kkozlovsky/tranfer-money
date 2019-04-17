package ru.kerporation.tranfermoney.request;

import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class TransferMoneyRequest {
    @NotNull
    private Long fromAccountId;
    @NotNull
    private Long toAccountId;
    @NotNull
    @DecimalMin("0.01")
    private BigDecimal amount;
}
