package frsf.isi.died.guia08.problema01.modelo.exception;

public class NoSePuedeAsignarTareaException extends Exception {
	
	public NoSePuedeAsignarTareaException() {
		super("No se pudo asignar la tarea.");
	}

	public NoSePuedeAsignarTareaException(String string) {
		super(string);
	}

}
