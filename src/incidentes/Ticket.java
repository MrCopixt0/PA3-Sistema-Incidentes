package incidentes;
import javax.persistence.*;

@Entity
@Table(name = "tickets")

public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "impacto", nullable = false)
    private int impacto;

    @Column(name = "gravedad", nullable = false)
    private int gravedad;

    @Column(name = "prioridad", nullable = false)
    private double prioridad;

    //relacion de mapeo aplicada.

    @ManyToOne
    @JoinColumn(name = "agente_id", nullable = true) //esto debe crear la llave foranea automaticamente
    private Agente agenteAsignado;

    public Ticket() {} //constructor requerido

    public Ticket(String descripcion, int impacto, int gravedad, double prioridad) {

        this.descripcion = descripcion;
        this.impacto = impacto;
        this.gravedad = gravedad;
        this.prioridad = prioridad;
    }

    public Long getId() {return id;}
    public String getDescripcion() {return descripcion;}
    public int getImpacto() {return impacto;}
    public int getGravedad() {return gravedad;}
    public double getPrioridad() {return prioridad;}

    public Agente getAgenteAsignado() {return agenteAsignado;}
    public void setAgenteAsignado(Agente agenteAsignado) {
        this.agenteAsignado = agenteAsignado;
    }
}
