package frsf.isi.died.guia08.problema01.modelo;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import frsf.isi.died.guia08.problema01.AppRRHH;
import frsf.isi.died.guia08.problema01.modelo.exception.NoExisteLaTareaException;
import frsf.isi.died.guia08.problema01.modelo.exception.NoSePuedeAsignarTareaException;

public class EmpleadoTest {
	
	AppRRHH app;

	@Before
	public void init() {
		app = new AppRRHH();
		app.agregarEmpleadoContratado(1, "Juan", 100.0);
		app.agregarEmpleadoContratado(2, "Jose", 110.0);
		app.agregarEmpleadoEfectivo(3, "Maria", 105.0);
		app.agregarEmpleadoEfectivo(4, "Sofia", 90.0);
		
		try {
			app.asignarTarea(1, 1, "Tarea 01", 10);
			app.asignarTarea(1, 2, "Tarea 02", 10);
			app.asignarTarea(1, 3, "Tarea 03", 10);
			app.asignarTarea(1, 4, "Tarea 04", 10);
			app.asignarTarea(2, 5, "Tarea 05", 10);
			app.asignarTarea(2, 6, "Tarea 06", 10);
			app.asignarTarea(2, 7, "Tarea 07", 10);
			app.asignarTarea(2, 8, "Tarea 08", 10);
			app.asignarTarea(2, 9, "Tarea 09", 10);
			app.asignarTarea(3, 10, "Tarea 10", 18);
			app.asignarTarea(4, 11, "Tarea 11", 5);
		} catch (NoSePuedeAsignarTareaException e) {
		}

		
		//Primero lo hice cargando csv, pero por las dudas lo cargue a mano, debido a que no puedo controlar lo que les pase a los archivos
		//app.cargarEmpleadosContratadosCSV("testEmpleadosC.csv");
		//app.cargarEmpleadosEfectivosCSV("testEmpleadosE.csv");
		//app.cargarTareasCSV("TestTareas.csv");
	}
	
	
	//SALARIOS
	@Test
	public void testSalarioContratadosConTareasEnTermino() throws NoExisteLaTareaException{		
		//Jose tiene asignado 5 tareas de 10 hrs cada una, lo que equivale a 2 dias de trabajo, su costoHora = 110
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(2)).findFirst().get();
		for(Tarea tarea : empleado.getTareasAsignadas()) {
			//Example: 2007-12-03T10:15:30
			String fechaInicio = "2020-01-01T00:00:00";
			String fechaFin = "2020-01-04T00:00:00";
			empleado.comenzar(tarea.getId(),fechaInicio);
			empleado.finalizar(tarea.getId(),fechaFin);
		}
		//110 * 5 * 10 = 5500
		assertTrue(empleado.salario().equals(5500d));
	}
	
	@Test
	public void testSalarioContratadoConTareasRetrasadas() throws NoExisteLaTareaException{		
		//Jose tiene asignado 5 tareas de 10 hrs cada una, lo que equivale a 2 dias de trabajo, su costoHora = 110
		//Como realizó todas sus tareas con retraso su costoHora pasa a ser de costoHora * 0.75 = 82.5
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(2)).findFirst().get();
		for(Tarea tarea : empleado.getTareasAsignadas()) {
			//Example: 2007-12-03T10:15:30
			String fechaInicio = "2020-01-01T00:00:00";
			String fechaFin = "2020-01-07T00:00:00";
			empleado.comenzar(tarea.getId(),fechaInicio);
			empleado.finalizar(tarea.getId(),fechaFin);
		}
		//82.5 * 5 * 10 = 4125
		assertTrue(empleado.salario().equals(4125d));
	}
	
	@Test
	public void testSalarioContratadoConTareasTerminadasAntesDeFecha() throws NoExisteLaTareaException{		
		
		//Jose tiene asignado 5 tareas de 10 hrs cada una, lo que equivale a 2 dias de trabajo, su costoHora = 110
		//Como realizó todas sus tareas antes de tiempo su costo hora aumentara a costoHora*1.3 = 143
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(2)).findFirst().get();
		for(Tarea tarea : empleado.getTareasAsignadas()) {
			//Example: 2007-12-03T10:15:30
			String fechaInicio = "2020-01-01T00:00:00";
			String fechaFin = "2020-01-02T00:00:00";
			empleado.comenzar(tarea.getId(),fechaInicio);
			empleado.finalizar(tarea.getId(),fechaFin);
		}
		//143* 5 * 10 = 7150
		assertTrue(empleado.salario().equals(7150d));
	}
	
	@Test
	public void testSalarioEfectivoConTareasEnTermino() throws NoExisteLaTareaException{		
		//Maria tiene asignado 1 tarea de 18 hrs, lo que equivale a 5 dias de trabajo, su costoHora = 105
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(3)).findFirst().get();
		for(Tarea tarea : empleado.getTareasAsignadas()) {
			//Example: 2007-12-03T10:15:30
			String fechaInicio = "2020-01-01T00:00:00";
			String fechaFin = "2020-01-06T00:00:00";
			empleado.comenzar(tarea.getId(),fechaInicio);
			empleado.finalizar(tarea.getId(),fechaFin);
		}
		//105 * 1 * 18 = 1890
		assertTrue(empleado.salario().equals(1890d));
	}
	
	@Test
	public void testSalarioEfectivoConTareasRetrasadas() throws NoExisteLaTareaException{		
		//Maria tiene asignado 1 tarea de 18 hrs, lo que equivale a 5 dias de trabajo, su costoHora = 105
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(3)).findFirst().get();
		for(Tarea tarea : empleado.getTareasAsignadas()) {
			//Example: 2007-12-03T10:15:30
			String fechaInicio = "2020-01-01T00:00:00";
			String fechaFin = "2020-01-16T00:00:00";
			empleado.comenzar(tarea.getId(),fechaInicio);
			empleado.finalizar(tarea.getId(),fechaFin);
		}
		//105 * 1 * 18 = 1890
		assertTrue(empleado.salario().equals(1890d));
	}
	
	@Test
	public void testSalarioEfectivoConTareasTerminadasAntesDeFecha() throws NoExisteLaTareaException{		
		//Maria tiene asignado 1 tarea de 18 hrs, lo que equivale a 5 dias de trabajo, su costoHora = 105
		//Como terminó antes de fecha su costoHora pasa a ser 105*1.2 = 126
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(3)).findFirst().get();
		for(Tarea tarea : empleado.getTareasAsignadas()) {
			//Example: 2007-12-03T10:15:30
			String fechaInicio = "2020-01-01T00:00:00";
			String fechaFin = "2020-01-03T00:00:00";
			empleado.comenzar(tarea.getId(),fechaInicio);
			empleado.finalizar(tarea.getId(),fechaFin);
		}
		//126 * 1 * 18 = 2268
		assertTrue(empleado.salario().equals(2268d));
	}
	
	//ASIGNAR TAREAS
	
	@Test
	public void testAsignarTareaAContratadoConMasDe5Tareas() throws NoSePuedeAsignarTareaException {
		//Jose tiene 5 tareas asignadas, tiene cuil=2
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(2)).findFirst().get();
		Tarea t = new Tarea(1,"Tarea test",10);
		assertFalse (empleado.asignarTarea(t));
	}
	
	@Test
	public void testAsignarTareaAContratadoCon5OMenosTareas() throws NoSePuedeAsignarTareaException {
		//Juan tiene 4 tareas asignadas , tiene cuil = 2
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(1)).findFirst().get();
		Tarea t = new Tarea(1,"Tarea test",10);
		assertTrue (empleado.asignarTarea(t));
	}
	
	@Test
	public void testAsignarTareaAEfectivoConMasDe15Hs() throws NoSePuedeAsignarTareaException {
		//Maria tiene 1 tarea de 18hs asignada , tiene cuil = 3
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(3)).findFirst().get();
		Tarea t = new Tarea(1,"Tarea test",10);
		assertFalse (empleado.asignarTarea(t));
	}
	
	@Test
	public void testAsignarTareaAEfectivoConMenosDe15Hs() throws NoSePuedeAsignarTareaException {
		//Sofia tiene 1 tarea de 5hs asignada , tiene cuil = 3
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		Tarea t = new Tarea(1,"Tarea test",10);
		assertTrue (empleado.asignarTarea(t));
	}

	//COMENZAR TAREAS
	
	@Test
	public void testComenzarTareaPorId() throws NoExisteLaTareaException {
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		empleado.comenzar(11);
		assertTrue(empleado.getTareasAsignadas().stream().filter(t -> t.getId().equals(11)).findFirst().get().getFechaInicio() != null);
	}
	
	@Test(expected = NoExisteLaTareaException.class)
	public void testComenzarTareaNoExistentePorId() throws NoExisteLaTareaException {
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		empleado.comenzar(416865435);
	}
	
	@Test
	public void testComenzarTareaPorIdYFecha() throws NoExisteLaTareaException {
		//Example: 2007-12-03T10:15:30
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		empleado.comenzar(11,"2020-11-11T00:00:00");
		assertTrue(empleado.getTareasAsignadas().stream().filter(t -> t.getId().equals(11)).findFirst().get().getFechaInicio() != null);
	}

	
	//FINALIZAR TAREAS
	
	@Test
	public void testFinalizarTareaPorId() throws NoExisteLaTareaException {
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		empleado.comenzar(11);
		empleado.finalizar(11);
		assertTrue(empleado.getTareasAsignadas().stream().filter(t -> t.getId().equals(11)).findFirst().get().getFechaFin() != null);
	}

	@Test(expected = NoExisteLaTareaException.class)
	public void testFinalizarTareaNoExistentePorId() throws NoExisteLaTareaException {
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		empleado.finalizar(111);
	}

	@Test
	public void testFinalizarTareaPorIdYFecha() throws NoExisteLaTareaException{
		//Example: 2007-12-03T10:15:30
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil().equals(4)).findFirst().get();
		empleado.comenzar(11,"2020-11-11T00:00:00");
		empleado.finalizar(11,"2020-11-12T00:00:00");
		assertTrue(empleado.getTareasAsignadas().stream().filter(t -> t.getId().equals(11)).findFirst().get().getFechaFin() != null);
	}
}
