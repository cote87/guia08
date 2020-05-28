package frsf.isi.died.guia08.problema01.modelo.exception;

public class TareaPreviamenteAsignadaException extends NoSePuedeAsignarTareaException {

	public TareaPreviamenteAsignadaException() {
		super("La tarea que intentó asignar ya se encuentra asignada a otro empleado.");
	}

}
