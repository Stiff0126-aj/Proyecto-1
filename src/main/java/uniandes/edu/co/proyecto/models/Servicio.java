package uniandes.edu.co.proyecto.models;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document(collection = "servicios")
public class Servicio {

    @Id
    private String id;

    private String usuarioId;
    private String conductorAsignadoId;
    private String vehiculoId;

    private String tipo;

    private Punto puntoPartida;

    private List<Punto> puntosLlegada;

    private Double distanciaKm;
    private Double tarifaFinal;

    private String estado;

    private Date horaInicio;
    private Date horaFin;



    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getUsuarioId() { return usuarioId; }
    public void setUsuarioId(String usuarioId) { this.usuarioId = usuarioId; }

    public String getConductorAsignadoId() { return conductorAsignadoId; }
    public void setConductorAsignadoId(String conductorAsignadoId) { this.conductorAsignadoId = conductorAsignadoId; }

    public String getVehiculoId() { return vehiculoId; }
    public void setVehiculoId(String vehiculoId) { this.vehiculoId = vehiculoId; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public Punto getPuntoPartida() { return puntoPartida; }
    public void setPuntoPartida(Punto puntoPartida) { this.puntoPartida = puntoPartida; }

    public List<Punto> getPuntosLlegada() { return puntosLlegada; }
    public void setPuntosLlegada(List<Punto> puntosLlegada) { this.puntosLlegada = puntosLlegada; }

    public Double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(Double distanciaKm) { this.distanciaKm = distanciaKm; }

    public Double getTarifaFinal() { return tarifaFinal; }
    public void setTarifaFinal(Double tarifaFinal) { this.tarifaFinal = tarifaFinal; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public Date getHoraInicio() { return horaInicio; }
    public void setHoraInicio(Date horaInicio) { this.horaInicio = horaInicio; }

    public Date getHoraFin() { return horaFin; }
    public void setHoraFin(Date horaFin) { this.horaFin = horaFin; }



    public static class Punto {
        private String direccion;
        private String ciudad;
        private Double lat;
        private Double lng;

        public String getDireccion() { return direccion; }
        public void setDireccion(String direccion) { this.direccion = direccion; }

        public String getCiudad() { return ciudad; }
        public void setCiudad(String ciudad) { this.ciudad = ciudad; }

        public Double getLat() { return lat; }
        public void setLat(Double lat) { this.lat = lat; }

        public Double getLng() { return lng; }
        public void setLng(Double lng) { this.lng = lng; }
    }
}
