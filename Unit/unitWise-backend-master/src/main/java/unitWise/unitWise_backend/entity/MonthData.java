package unitWise.unitWise_backend.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "month_data")
public class MonthData {

    private String plan;
    private String fact;
    private String difference;
}
