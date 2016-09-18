/*
 * ReservaDTO
 * Objeto de transferencia de datos de Reservas.
 * Los DTO especifican los mensajes que se envían entre el cliente y el servidor.
 */
package co.edu.uniandes.rest.dtos;

import java.util.Date;

/**
 * Objeto de transferencia de datos de Reservas.
 * @author fa.lopez10
 */
public class ReservaDTO {
    private Long id;
    private Date fecha;
    private String estado;
    
    public final static String RESERVA_CANCELADA = "Reserva Cancelada";
    public final static String RESERVA_APROBADA = "Reserva Aprobada";

    /**
     * Constructor por defecto
     */
    public ReservaDTO() {
	}

    /**
     * Constructor con parámetros.
     * @param id identificador de la reserva
     * @param estado estado de la reserva
     * @param fecha fecha en que se hace la reserva
     */
    public ReservaDTO(Long id, Date fecha, String estado) {
		super();
		this.id = id;
		this.fecha = fecha;
                this.estado = estado;
	}

	/**
     * @return el id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return el estado
     */
    public String getEstado() {
        return estado;
    }

    /**
     * @param estado el estado a cambiar
     */
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    /**
     * @return la fecha
     */
    public Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha la fecha a cambiar
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    /**
     * Convierte el objeto a una cadena
     * @return atributos del objeto como caracteres
     */
    @Override
    public String toString() {
    	return "{ id : " + getId() + ", fecha : \"" + getFecha()+ ", estado : \"" + getEstado()+ "\" }" ;  
    }
}
