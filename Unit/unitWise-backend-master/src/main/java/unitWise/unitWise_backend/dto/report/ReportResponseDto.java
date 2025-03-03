package unitWise.unitWise_backend.dto.report;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportResponseDto {
    private Long id;
    private String accrualDate;
    private String transactionDate;
    private Integer businessUnitId;
    private Integer contractId;
    private Integer bankAccountId;
    private Double debitAmount;
    private Double creditAmount;
    private Integer typePaymentId;
    private String comment;
}
