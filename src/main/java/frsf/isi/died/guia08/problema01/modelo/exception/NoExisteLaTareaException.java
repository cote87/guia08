package frsf.isi.died.guia08.problema01.modelo.exception;

public class NoExisteLaTareaException extends Exception {
	public NoExisteLaTareaException() {
		super("La tarea no se encuentra asignada al trabajador");
	}
}
