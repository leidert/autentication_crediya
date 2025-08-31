package co.com.tanos.r2dbc.postgres_adacters;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Table(name = "usuarios")
public class UserEntity {

    @Id
    private Long id;
    private Long dni;
    private String name;
    @Column("last_name")
    private String lastName;
    @Column("birth_date")
    private LocalDate birdDate;
    private String address;
    @Column("phone_number")
    private String phoneNumber;
    private String email;
    private Long salary;

}
