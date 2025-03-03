package unitWise.unitWise_backend.dto.contractor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractorRequestDto {

    private Long id;
    private String name;
    private String short_name;
    private Long project_id;
}
