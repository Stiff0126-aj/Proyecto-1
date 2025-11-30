package uniandes.edu.co.proyecto.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "vehiculos")
public class Vehiculo {

    @Id
    private String id;

    private String conductorId;
    private String tipo;
    private String marca;
    private String modelo;
    private String color;
    private String placa;
    private String ciudadExpedicion;
    private Integer capacidad;

    private String nivel;

    private List<Disponibilidad> disponibilidades;


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getConductorId() { return conductorId; }
    public void setConductorId(String conductorId) { this.conductorId = conductorId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }

    public String getPlaca() { return placa; }
    public void setPlaca(String placa) { this.placa = placa; }

    public String getCiudadExpedicion() { return ciudadExpedicion; }
    public void setCiudadExpedicion(String ciudadExpedicion) { this.ciudadExpedicion = ciudadExpedicion; }

    public Integer getCapacidad() { return capacidad; }
    public void setCapacidad(Integer capacidad) { this.capacidad = capacidad; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public List<Disponibilidad> getDisponibilidades() { return disponibilidades; }
    public void setDisponibilidades(List<Disponibilidad> disponibilidades) { this.disponibilidades = disponibilidades; }


    public static class Disponibilidad {
        private String dia;
        private Date inicio;
        private Date fin;

        public String getDia() { return dia; }
        public void setDia(String dia) { this.dia = dia; }

        public Date getInicio() { return inicio; }
        public void setInicio(Date inicio) { this.inicio = inicio; }

        public Date getFin() { return fin; }
        public void setFin(Date fin) { this.fin = fin; }
    }
}
