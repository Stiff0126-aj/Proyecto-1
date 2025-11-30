package uniandes.edu.co.proyecto.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;

@Document(collection = "reviews")
public class Review {

    @Id
    private String id;

    private String servicioId;
    private String autorId;
    private String destinatarioId;

    private Double puntuacion;
    private String comentario;
    private Date fecha;



    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getServicioId() { return servicioId; }
    public void setServicioId(String servicioId) { this.servicioId = servicioId; }

    public String getAutorId() { return autorId; }
    public void setAutorId(String autorId) { this.autorId = autorId; }

    public String getDestinatarioId() { return destinatarioId; }
    public void setDestinatarioId(String destinatarioId) { this.destinatarioId = destinatarioId; }

    public Double getPuntuacion() { return puntuacion; }
    public void setPuntuacion(Double puntuacion) { this.puntuacion = puntuacion; }

    public String getComentario() { return comentario; }
    public void setComentario(String comentario) { this.comentario = comentario; }

    public Date getFecha() { return fecha; }
    public void setFecha(Date fecha) { this.fecha = fecha; }
}
