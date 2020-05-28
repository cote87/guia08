package frsf.isi.died.guia08.problema01;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import frsf.isi.died.guia08.problema01.modelo.Empleado;
import frsf.isi.died.guia08.problema01.modelo.Tarea;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.exception.NoExisteLaTareaException;
import frsf.isi.died.guia08.problema01.modelo.exception.NoSePuedeAsignarTareaException;

public class AppRRHH {

	private List<Empleado> empleados;
	
	public List<Empleado> getEmpleados() {
		return empleados;
	}

	public AppRRHH() {
		super();
		empleados = new ArrayList<Empleado>();
	}

	public void agregarEmpleadoContratado(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista
		Empleado empleado = new Empleado(cuil,nombre,costoHora,Tipo.CONTRATADO);
		empleados.add(empleado);
	}
	
	public void agregarEmpleadoEfectivo(Integer cuil,String nombre,Double costoHora) {
		// crear un empleado
		// agregarlo a la lista		
		Empleado empleado = new Empleado(cuil,nombre,costoHora,Tipo.EFECTIVO);
		empleados.add(empleado);
	}
	
	public void asignarTarea(Integer cuil,Integer idTarea,String descripcion,Integer duracionEstimada) throws NoSePuedeAsignarTareaException {
		// crear un empleado
		// con el método buscarEmpleado() de esta clase
		// agregarlo a la lista		
		Optional<Empleado> optEmpleado = this.buscarEmpleado(e -> e.getCuil() == cuil);
		if(!optEmpleado.isEmpty())
			optEmpleado.get().asignarTarea(new Tarea(idTarea,descripcion,duracionEstimada));
	}
	
	public void empezarTarea(Integer cuil,Integer idTarea) {
		// busca el empleado por cuil en la lista de empleados
		// con el método buscarEmpleado() actual de esta clase
		// e invoca al método comenzar tarea
		Optional<Empleado> optEmpleado = this.buscarEmpleado(e -> e.getCuil() == cuil);
		if(!optEmpleado.isEmpty()) {
			try {
				optEmpleado.get().comenzar(idTarea);
			} catch (NoExisteLaTareaException e1) {
				System.out.println(e1.getMessage());
			}
			
		}		
	}
	
	public void terminarTarea(Integer cuil,Integer idTarea) {
		// crear un empleado
		// agregarlo a la lista	
		Optional<Empleado> optEmpleado = this.buscarEmpleado(e -> e.getCuil() == cuil);
		if(!optEmpleado.isEmpty()) {
			try {
				optEmpleado.get().finalizar(idTarea);
			} catch (NoExisteLaTareaException e1) {
				System.out.println(e1.getMessage());
			}
			
		}	
	}

	public void cargarEmpleadosContratadosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado
		try (BufferedReader rd = new BufferedReader(new FileReader(nombreArchivo)))	{
			
			Function<String,Empleado> mapper = s -> {
				String[] linea = s.split(";");
				//0-cuil 1-nombre 2-costohora 3-tipo
				if(linea.length == 3)
					return new Empleado(Integer.parseInt(linea[0]),linea[1],Double.parseDouble(linea[2]),Tipo.CONTRATADO);
				else return null;
			};
			rd.lines().map(mapper).forEach(
					e ->{
						if(e != null)
						empleados.add(e);
						}
					);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarEmpleadosEfectivosCSV(String nombreArchivo) {
		// leer datos del archivo
		// por cada fila invocar a agregarEmpleadoContratado	
		try (BufferedReader rd = new BufferedReader(new FileReader(nombreArchivo)))	{
			
			Function<String,Empleado> mapper = s -> {
				String[] linea = s.split(";");
				//0-cuil 1-nombre 2-costohora 3-tipo
				if(linea.length == 3)
					return new Empleado(Integer.parseInt(linea[0]),linea[1],Double.parseDouble(linea[2]),Tipo.EFECTIVO);
				else return null;
			};
			rd.lines().map(mapper).forEach(
					e ->{
						if(e != null)
						empleados.add(e);
						}
					);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void cargarTareasCSV(String nombreArchivo) {
		// leer datos del archivo
		// cada fila del archivo tendrá:
		// cuil del empleado asignado, numero de la taera, descripcion y duración estimada en horas.
		
		try (BufferedReader rd = new BufferedReader(new FileReader(nombreArchivo)))	{
			
			Function<String,Tarea> mapper = t -> {
				String[] linea = t.split(";");
				//0-id 1-descripcion 2-duracion 3-cuil de empleado
				if(linea.length == 4) {
					System.out.println(linea[3]);
					Tarea tarea = new Tarea(Integer.parseInt(linea[0]),linea[1],Integer.parseInt(linea[2]));
					
					Optional<Empleado> optEmpleado = empleados.stream().filter(e -> e.getCuil().equals(Integer.parseInt(linea[3]))).findAny();		
					
					if(!optEmpleado.isEmpty()) {
						try {
							optEmpleado.get().asignarTarea(tarea);
						} catch (NoSePuedeAsignarTareaException e1) {
							System.out.println(e1.getMessage());
						}	
					}
					return tarea;
				}
				else return null;
			};
			rd.lines().map(mapper);
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void guardarTareasTerminadasCSV() {
		// guarda una lista con los datos de la tarea que fueron terminadas
		// y todavía no fueron facturadas
		// y el nombre y cuil del empleado que la finalizó en formato CSV
		try {
			try(Writer fileWriter= new FileWriter("tareasTerminadas.csv",true)) {
				try(BufferedWriter out = new BufferedWriter(fileWriter)){
					
					this.empleados
					.stream()
					.map(e -> e.getTareasAsignadas())
					.flatMap(t -> t.stream())
					.filter(t -> !t.getFacturada())
					.forEach(t -> {
						try {
							out.write(t.asCsv()+ System.getProperty("line.separator"));
						} catch (IOException e1) {
							System.out.println("Error al intentar escribir el archivo");
						}
					});
				}
			}
		}
		catch(IOException e) {
			System.out.println("Error al intentar crear/abrir el archivo");
		}
				
	}
	
	private Optional<Empleado> buscarEmpleado(Predicate<Empleado> p){
		return this.empleados.stream().filter(p).findFirst();
	}

	public Double facturar() {
		this.guardarTareasTerminadasCSV();
		return this.empleados.stream()				
				.mapToDouble(e -> e.salario())
				.sum();
	}
}
