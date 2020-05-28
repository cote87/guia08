package frsf.isi.died.guia08.problema01.modelo;

import java.time.LocalDateTime;

import frsf.isi.died.guia08.problema01.modelo.exception.NoSePuedeAsignarTareaException;
import frsf.isi.died.guia08.problema01.modelo.exception.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.modelo.exception.TareaPreviamenteAsignadaException;

public class Tarea {

	private Integer id;
	private String descripcion;
	//Vamos a asumir que la duracion es en horas estimadas
	private Integer duracionEstimada;
	private Empleado empleadoAsignado;
	private LocalDateTime fechaInicio;
	private LocalDateTime fechaFin;
	private Boolean facturada;
	
	public Tarea(Integer id, String descripcion, Integer duracionEstimada) {
		super();
		this.id = id;
		this.descripcion = descripcion;
		this.duracionEstimada = duracionEstimada;
		this.setFacturada(false);
	}

	public void asignarEmpleado(Empleado e) throws NoSePuedeAsignarTareaException{
		// si la tarea ya tiene un empleado asignado
		// y tiene fecha de finalizado debe lanzar una excepcion
		if(getEmpleadoAsignado() != null)
			throw new TareaPreviamenteAsignadaException();
		if(getFechaFin()!=null)
			throw new TareaFinalizadaException();
		this.setEmpleadoAsignado(e);
	}

	private void setEmpleadoAsignado(Empleado e) {
		this.empleadoAsignado = e;	
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public Integer getDuracionEstimada() {
		return duracionEstimada;
	}

	public void setDuracionEstimada(Integer duracionEstimada) {
		this.duracionEstimada = duracionEstimada;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(LocalDateTime fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(LocalDateTime fechaFin) {
		this.fechaFin = fechaFin;
	}

	public Boolean getFacturada() {
		return facturada;
	}

	public Tarea setFacturada(Boolean facturada) {
		this.facturada = facturada;
		return this;
	}

	public Empleado getEmpleadoAsignado() {
		return empleadoAsignado;
	}

	public Integer getHorasEstimadas() {
		return this.getDuracionEstimada()*4;
	}
	
	@Override
	public String toString() {
		return 	this.getId() + " - " + 
				this.getDescripcion() + " - " + 
				this.getDuracionEstimada() + " - " + 
				this.getEmpleadoAsignado();
	}
	
	public String asCsv() {
		return 	this.getEmpleadoAsignado().getNombre()+ ";"+ 
				this.getEmpleadoAsignado().getCuil()+";"+
				this.getId() + ";" + 
				this.getDescripcion() + ";" + 
				this.getDuracionEstimada() + ";" + 
				this.getEmpleadoAsignado();
	}
	
}
