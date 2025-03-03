package unitWise.unitWise_backend.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")

public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accrualDate;
    private String transactionDate;

    @Column(name = "business_unit_id")
    private Long businessUnitId;
    private String businessUnitName;

    @Column(name = "article_id")
    private Long articleId;
    private String articleName;

    @Column(name = "contract_id")
    private Long contractId;
    private String contractName;

    @Column(name = "bank_account_id")
    private Long bankAccountId;
    private String bankAccountName;

    private String debitAmount;
    private String creditAmount;

    @Column(name = "type_payment_id")
    private Long typePaymentId;
    private String typePaymentName;

    private String comment;
}
