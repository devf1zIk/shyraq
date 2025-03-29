package unitWise.unitWise_backend.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty("date_accrual")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateAccrual;

    @JsonProperty("date_transfer")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date dateTransfer;

    @JsonProperty("company_id")
    private Long companyId;

    @JsonProperty("account_id")
    private Long accountId;

    @JsonProperty("contract_id")
    private Long contractId;

    @JsonProperty("amountdt")
    private Double amountDt;

    @JsonProperty("amountkt")
    private Double amountKt;

    @JsonProperty("cf_item_id")
    private Long articleId;

    @JsonProperty("version_id")
    private Long versionId;

    @JsonProperty("user_id")
    private Long userId;

    @JsonProperty("project_id")
    private Long projectId;

    private String comment;

    @JsonProperty("created_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "Asia/Aqtobe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;

    @JsonProperty("updated_at")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'", timezone = "Asia/Aqtobe")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;


    @PrePersist
    protected void onCreate() {
        createdAt = new Date();
        updatedAt = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = new Date();
    }
}
