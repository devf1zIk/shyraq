package unitWise.unitWise_backend.dto.vypiska;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDTO {
    @JsonProperty("date_accrual")
    private LocalDate dateAccrual;

    @JsonProperty("date_transfer")
    private LocalDate dateTransfer;

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("contract_id")
    private Long contractId;

    @JsonProperty("amountdt")
    private Double amountDt;

    @JsonProperty("cf_item_id")
    private Double articleId;

    @JsonProperty("amountkt")
    private Double amountKt;

    @JsonProperty("version_id")
    private Long versionId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("project_id")
    private Long projectId;
    private String comment;
}
