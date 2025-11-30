package uniandes.edu.co.proyecto.models;

import org.springframework.data.mongodb.core.mapping.Field;

public class TipoServicioStats {

    @Field("_id")
    private String tipo;  // pasajeros, comida, carga

    private long totalServicios;

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public long getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(long totalServicios) {
        this.totalServicios = totalServicios;
    }
}
