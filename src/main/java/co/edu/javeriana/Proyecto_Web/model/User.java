package co.edu.javeriana.Proyecto_Web.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String name;
    private String password;
    private String type;

    @OneToMany(mappedBy = "owner")
    private List<Ship> ship;

   

    public User(String name, String password, String type) {
        this.name = name;
        this.password = password;
        this.type = type;
    }

    

}
