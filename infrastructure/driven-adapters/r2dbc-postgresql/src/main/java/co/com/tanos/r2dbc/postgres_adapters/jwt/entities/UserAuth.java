package co.com.tanos.r2dbc.postgres_adapters.jwt.entities;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table("usuarios_autenticacion")
public class UserAuth {
    @Id
    private Long id;
    private String email;
    private String password;
    private String role;
}
