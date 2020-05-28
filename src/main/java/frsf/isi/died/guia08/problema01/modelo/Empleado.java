package frsf.isi.died.guia08.problema01.modelo;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import frsf.isi.died.guia08.problema01.modelo.exception.NoExisteLaTareaException;
import frsf.isi.died.guia08.problema01.modelo.exception.NoSePuedeAsignarTareaException;
import frsf.isi.died.guia08.problema01.modelo.exception.TareaFinalizadaException;
import frsf.isi.died.guia08.problema01.modelo.exception.TareaPreviamenteAsignadaException;

public class Empleado {

	public enum Tipo { CONTRATADO,EFECTIVO}; 
	
	private Integer cuil;
	private String nombre;
	private Tipo tipo;
	private Double costoHora;
	private List<Tarea> tareasAsignadas;
	
	private Function<Tarea, Double> calculoPagoPorTarea;		
	private Predicate<Empleado> puedeAsignarTarea;

	public Empleado(Integer cuil) {
		tareasAsignadas = new ArrayList<Tarea>();	
		this.setCuil(cuil);
		this.costoHora=0.0;
	}
			
	public Empleado(Integer cuil, String nombre, Double costoHora, Tipo tipo) {
		this(cuil);
		this.setNombre(nombre);
		this.setTipo(tipo);
		this.setCostoHora(costoHora);
	}

	public Double salario() {
		// cargar todas las tareas no facturadas
		// calcular el costo
		// marcarlas como facturadas.
		if(this.getCostoHora() != null) {
			Double salario=0.0;
			
			for(Tarea tarea: this.tareasAsignadas) {
				if(!tarea.getFacturada()) {
					tarea.setFacturada(true);
					salario = salario + this.calculoPagoPorTarea.apply(tarea) * tarea.getDuracionEstimada();
				}
			}		
			return salario;
		}
		else {
			System.out.println("Debe asignar un valor al costo por hora de este empleado");
			return 0.0;
		}

	}

	/**
	 * Si la tarea ya fue terminada nos indica cuaal es el monto según el algoritmo de calculoPagoPorTarea
	 * Si la tarea no fue terminada simplemente calcula el costo en base a lo estimado.
	 * @param t
	 * @return
	 */
	public Double costoTarea(Tarea t) {
		return 0.0;
	}
		
	public Boolean asignarTarea(Tarea t) throws NoSePuedeAsignarTareaException{
		if(this.puedeAsignarTarea.test(this)) {
			//Se asigna a la lista de tareas de este empleado la nueva tarea
			this.tareasAsignadas.add(t);
			try {
				//Se asigna a la tarea este empleado
				t.asignarEmpleado(this);
			} catch (TareaFinalizadaException e) {
				System.out.println(e.getMessage());
			} catch (TareaPreviamenteAsignadaException e) {
				System.out.println(e.getMessage());
			}
		}
		return false;
	}

	public void comenzar(Integer idTarea) throws NoExisteLaTareaException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de inicio la fecha y hora actual
		Optional<Tarea> optTarea = this.tareasAsignadas.stream().filter(t -> t.getId() == idTarea).findFirst();
		if(optTarea.isEmpty()) throw new NoExisteLaTareaException();
		else {
			optTarea.get().setFechaInicio(LocalDateTime.now());
		}
	}

	public void finalizar(Integer idTarea) throws NoExisteLaTareaException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		Optional<Tarea> optTarea = this.tareasAsignadas.stream().filter(t -> t.getId() == idTarea).findFirst();
		if(optTarea.isEmpty()) throw new NoExisteLaTareaException();
		else {
			optTarea.get().setFechaFin(LocalDateTime.now());
		}
	}

	public void comenzar(Integer idTarea,String fecha) throws NoExisteLaTareaException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual “DD-MM-YYYY HH:MM”
		Optional<Tarea> optTarea = this.tareasAsignadas.stream().filter(t -> t.getId() == idTarea).findFirst();
		if(optTarea.isEmpty()) throw new NoExisteLaTareaException();
		else {
			optTarea.get().setFechaInicio(LocalDateTime.parse(fecha));
		}
	}
	
	public void finalizar(Integer idTarea,String fecha) throws NoExisteLaTareaException {
		// busca la tarea en la lista de tareas asignadas 
		// si la tarea no existe lanza una excepción
		// si la tarea existe indica como fecha de finalizacion la fecha y hora actual
		Optional<Tarea> optTarea = this.tareasAsignadas.stream().filter(t -> t.getId() == idTarea).findFirst();
		if(optTarea.isEmpty()) throw new NoExisteLaTareaException();
		else {
			optTarea.get().setFechaFin(LocalDateTime.parse(fecha));
		}
	}
	
	public boolean equals(Empleado e) {
		if (e.getCuil() == this.getCuil()) return true;
		return false;
	}

	public Integer getCuil() {
		return cuil;
	}

	public void setCuil(Integer cuil) {
		this.cuil = cuil;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		if(tipo.equals(Tipo.CONTRATADO)){
			this.puedeAsignarTarea = empleado -> empleado.getTareasSinFinalizar() < 5;
			this.calculoPagoPorTarea = tarea -> {
												if(Duration.between(tarea.getFechaInicio(), tarea.getFechaFin()).compareTo(Duration.ofDays(tarea.getDuracionEstimada())) < 0 ) 
													return this.getCostoHora()* 1.3;
												if(Duration.between(tarea.getFechaInicio(), tarea.getFechaFin()).compareTo(Duration.ofDays(tarea.getDuracionEstimada())) > 0 ) 
													return this.getCostoHora()* 0.75;
												return this.getCostoHora();
												};
			this.tipo = tipo;
		}
		if(tipo.equals(Tipo.EFECTIVO)) {
			this.puedeAsignarTarea = empleado -> empleado.getHorasAcumuladas() < 15;
			this.calculoPagoPorTarea = tarea -> {
												if(Duration.between(tarea.getFechaInicio(), tarea.getFechaFin()).compareTo(Duration.ofDays(tarea.getDuracionEstimada())) < 0 ) 
													return this.getCostoHora()* 1.2;
												else 
													return this.getCostoHora();
												};
			this.tipo = tipo;
		}	
	}
	
	private int getHorasAcumuladas() {
		int horas = 0;
		if(this.tareasAsignadas != null) {
			for(Tarea tarea : this.tareasAsignadas){
				if(!tarea.getFacturada())
					horas = horas +  tarea.getDuracionEstimada();
			}	
		}
		return horas;
	}
	
	private int getTareasSinFinalizar() {
		int cantidadDeTareas = 0;
		if(this.tareasAsignadas != null) {
			for(Tarea tarea : this.tareasAsignadas){
				if(!tarea.getFacturada())
					cantidadDeTareas = cantidadDeTareas + 1;
			}
		}
		return cantidadDeTareas;
	}

	public Double getCostoHora() {
		return costoHora;
	}

	public void setCostoHora(Double costoHora) {
		this.costoHora = costoHora;
	}
	
	@Override
	public String toString() {
		return cuil.toString() +" - "+ nombre + "-" + costoHora.toString() + "-" + tipo.toString();
	}

	public List<Tarea> getTareasAsignadas() {
		return tareasAsignadas;
	}
	
	
}
