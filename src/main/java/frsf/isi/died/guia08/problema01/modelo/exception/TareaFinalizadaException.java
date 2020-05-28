package frsf.isi.died.guia08.problema01.modelo.exception;

public class TareaFinalizadaException extends NoSePuedeAsignarTareaException {

	public TareaFinalizadaException() {
		super("La tarea que quiere asignar ya se encuentra finalizada. Intente asignar una tarea distinta.");
	}

}
