package frsf.isi.died.guia08.problema01.modelo;

import org.junit.Before;
import org.junit.Test;

import frsf.isi.died.guia08.problema01.AppRRHH;
import frsf.isi.died.guia08.problema01.modelo.exception.NoSePuedeAsignarTareaException;

public class TareaTest {
	
	AppRRHH app;
	
	@Before
	public void init() {
		app = new AppRRHH();
		app.cargarEmpleadosContratadosCSV("testEmpleadosC.csv");
		app.cargarTareasCSV("TestTareas.csv");
	}
	
	@Test(expected = NoSePuedeAsignarTareaException.class)
	public void testAsigarTareaDeOtroEmpleado() throws NoSePuedeAsignarTareaException{
		Empleado e1 = app.getEmpleados().get(0);
		Empleado e2 = app.getEmpleados().get(1);
		Tarea t = e1.getTareasAsignadas().get(0);
		t.asignarEmpleado(e2);
	}

}
