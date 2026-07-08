package incidentes;

public class TicketRequestDTO {
    private String descripcion;
    private int impacto;
    private int gravedad;

    public TicketRequestDTO() {}

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public int getImpacto() { return impacto; }
    public void setImpacto(int impacto) { this.impacto = impacto; }

    public int getGravedad() { return gravedad; }
    public void setGravedad(int gravedad) { this.gravedad = gravedad; }
}
