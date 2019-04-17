package ru.kerporation.tranfermoney.request;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class ChangeBalanceRequest {
    @NotNull
    private Long accountId;
    @NotNull
    private BigDecimal amount;
}
