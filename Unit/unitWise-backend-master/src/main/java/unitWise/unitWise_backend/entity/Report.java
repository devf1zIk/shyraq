package unitWise.unitWise_backend.entity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "report_data")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String accrualDate;

    @Column(nullable = false)
    private String transactionDate;

    @Column
    private Integer businessUnitId;

    @Column
    private Integer contractId;

    @Column
    private Integer bankAccountId;

    @Column
    private Double debitAmount;

    @Column
    private Double creditAmount;

    @Column
    private Integer typePaymentId;

    @Column(columnDefinition = "TEXT")
    private String comment;

}
