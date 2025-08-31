package co.com.tanos.model.user;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class User {

    private Long id;
    private Long dni;
    private String name;
    private String lastName;
    private LocalDate birdDate;
    private String address;
    private String phoneNumber;
    private String email;
    private Long salary;

}
