package co.edu.javeriana.Proyecto_Web.model;

import java.util.HashMap;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private int height;

    private int width;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true, fetch = jakarta.persistence.FetchType.EAGER)
    private List<Cell> cell;

    public Board(int height, int width, List<Cell> cell) {
        this.height = height;
        this.width = width;
        this.cell = cell;

    }
}
