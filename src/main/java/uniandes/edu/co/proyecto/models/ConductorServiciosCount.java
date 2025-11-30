package uniandes.edu.co.proyecto.models;

import org.springframework.data.mongodb.core.mapping.Field;

public class ConductorServiciosCount {

    @Field("_id")
    private String conductorId;

    private long totalServicios;

    public String getConductorId() {
        return conductorId;
    }

    public void setConductorId(String conductorId) {
        this.conductorId = conductorId;
    }

    public long getTotalServicios() {
        return totalServicios;
    }

    public void setTotalServicios(long totalServicios) {
        this.totalServicios = totalServicios;
    }
}
