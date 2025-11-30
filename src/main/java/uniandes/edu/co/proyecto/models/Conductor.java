package uniandes.edu.co.proyecto.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "conductores")
public class Conductor {

    @Id
    private String id;

    private String nombre;
    private String correo;
    private String telefono;
    private String cedula;

    private Double calificacionPromedio = 0.0;
    private Integer numCalificaciones = 0;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCorreo() { return correo; }
    public void setCorreo(String correo) { this.correo = correo; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public Double getCalificacionPromedio() { return calificacionPromedio; }
    public void setCalificacionPromedio(Double calificacionPromedio) { this.calificacionPromedio = calificacionPromedio; }

    public Integer getNumCalificaciones() { return numCalificaciones; }
    public void setNumCalificaciones(Integer numCalificaciones) { this.numCalificaciones = numCalificaciones; }
}
