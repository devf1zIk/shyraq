package unitWise.unitWise_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDto {

    private Long id;
    private String accrualDate;
    private String transactionDate;

    private Long businessUnitId;
    private String businessUnitName;

    private Long articleId;
    private String articleName;

    private Long contractId;
    private String contractName;

    private Long bankAccountId;
    private String bankAccountName;

    private String debitAmount;
    private String creditAmount;

    private Long typePaymentId;
    private String typePaymentName;

    private String comment;
}
