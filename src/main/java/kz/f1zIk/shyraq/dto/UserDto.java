package kz.f1zIk.shyraq.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {

    public String email;
    public String firstName;
    public String lastName;
    public String username;
}
