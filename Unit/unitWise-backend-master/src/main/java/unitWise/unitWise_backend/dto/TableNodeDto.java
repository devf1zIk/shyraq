package unitWise.unitWise_backend.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableNodeDto {

    private Long id;
    private String name;
    private MonthDataDto total;
    private MonthDataDto jan;
    private MonthDataDto feb;
    private MonthDataDto mar;
    private MonthDataDto apr;
    private MonthDataDto may;
    private MonthDataDto jun;
    private MonthDataDto jul;
    private MonthDataDto aug;
    private MonthDataDto sep;
    private MonthDataDto oct;
    private MonthDataDto nov;
    private MonthDataDto dec;
    private boolean expanded;
    private List<TableNodeDto> children;
}
