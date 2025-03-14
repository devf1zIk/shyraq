package unitWise.unitWise_backend.dto.contractor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ContractorRequestDto {
    private String name;
    private String short_name;
    private Long project_id;
}
