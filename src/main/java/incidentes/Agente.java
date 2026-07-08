package incidentes;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "agentes")

public class Agente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "rol", nullable = false)
    private String rol;

    @Column(name = "carga_trabajo")
    private int cargaTrabajo;

    //relación inversa
    @OneToMany(mappedBy = "agenteAsignado", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Ticket> tickets = new ArrayList<>();

    public Agente() {} //constructor vacio requerido por el ORM para instanciar objetos

    public Agente(String nombre, String rol, int cargaTrabajo) {

        this.nombre = nombre;
        this.rol = rol;
        this.cargaTrabajo = cargaTrabajo;
    }

    //getters y setters - escritos manual xd

    public Long getId() {return id;}
    public String getNombre() {return nombre;}
    public String getRol() {return rol;}
    public int getCargaTrabajo() {return cargaTrabajo;}

    public void setCargaTrabajo(int cargaTrabajo) {
        this.cargaTrabajo = cargaTrabajo;
    }
}
