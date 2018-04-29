package asistente;

public class Asistente {

	public String nombre;
	private final static String USUARIO = "delucas";
	
	public Asistente(String nombre) {
		this.nombre = nombre;
	}
	
	public String escuchar(String mensaje) {
		String respuesta = "@" + USUARIO + " ";
		
		return respuesta + "Próximamente. Estamos agregando código para que puedas realizar otras acciones";
	}
	
	
}
