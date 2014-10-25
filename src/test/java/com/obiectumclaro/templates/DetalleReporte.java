package com.obiectumclaro.templates;
/**
 * 
 */


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author fausto
 * 
 */
public class DetalleReporte {

	private Date fecha;
	private String nombre;
	private Integer cantidad;
	private Float precio;

	public DetalleReporte(Date fecha, String nombre, Integer cantidad,
			Float precio) {
		super();
		this.fecha = fecha;
		this.nombre = nombre;
		this.cantidad = cantidad;
		this.precio = precio;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCantidad() {
		return cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}

	public Float getPrecio() {
		return precio;
	}

	public void setPrecio(Float precio) {
		this.precio = precio;
	}

	public String getFechaString() {
		return fecha == null ? "sin fecha" : new SimpleDateFormat("dd-MM-yy")
				.format(fecha);
	}

}
