package unitWise.unitWise_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity{

    @JsonProperty("date_accrual")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateAccrual;

    @JsonProperty("date_transfer")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateTransfer;

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("contract_id")
    private Long contractId;

    @JsonProperty("amountdt")
    private BigDecimal amountDt;

    @JsonProperty("amountkt")
    private BigDecimal amountKt;

    @JsonProperty("cf_item_id")
    private Long articleId;

    @JsonProperty("version_id")
    private Long versionId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("project_id")
    private Long projectId;

    private String comment;

    private Long siblingId;
}
