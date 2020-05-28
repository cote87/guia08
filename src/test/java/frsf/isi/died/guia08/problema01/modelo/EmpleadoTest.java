package frsf.isi.died.guia08.problema01.modelo;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.AppRRHH;
import frsf.isi.died.guia08.problema01.modelo.Empleado.Tipo;
import frsf.isi.died.guia08.problema01.modelo.exception.NoSePuedeAsignarTareaException;

public class EmpleadoTest {

	// IMPORTANTE
	// ESTA CLASE ESTA ANOTADA COMO @IGNORE por lo que no ejecutará ningun test
	// hasta que no borre esa anotación.
	
	AppRRHH app;

	@BeforeClass
	public void init() {
		app.cargarEmpleadosContratadosCSV("empleadosContratados.csv");
		app.cargarEmpleadosEfectivosCSV("empleadosEfectivos");
		app.cargarTareasCSV("tareas.csv");
	}
	
	@Test
	public void testSalario() throws NoSePuedeAsignarTareaException {		
		
		//123464 = Ariel ; sueldo por hora = 
		
		Empleado empleado = app.getEmpleados().stream().filter(e -> e.getCuil() == 123464).findFirst().get();
		assertTrue(0.0 == empleado.salario());
	}

	@Test
	public void testCostoTarea() {
		fail("Not yet implemented");
	}

	@Test
	public void testAsignarTarea() {
		fail("Not yet implemented");
	}

	@Test
	public void testComenzarInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalizarInteger() {
		fail("Not yet implemented");
	}

	@Test
	public void testComenzarIntegerString() {
		fail("Not yet implemented");
	}

	@Test
	public void testFinalizarIntegerString() {
		fail("Not yet implemented");
	}

}
