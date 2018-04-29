package asistente;

import calculo.Calculo;

public class Asistente {

	public String nombre;
	private final static String USUARIO = "delucas";
	
	public Asistente(String nombre) {
		this.nombre = nombre;
	}
	
	public String escuchar(String mensaje) {
		String respuesta = "@" + USUARIO + " ";
		
		if(mensaje.contains("cuánto") || mensaje.contains("cuanto")) {
			// RF06: realizar cálculos
			Calculo calc = new Calculo();
			Double resultado = calc.calcular(mensaje);
			if(resultado % 1 == 0) // Si es entero devolver redondeado
				return respuesta + Math.round(resultado);
			else // Si tiene decimales devolverlo como real
				return respuesta + resultado;
		}
		
		return respuesta + "Próximamente. Estamos agregando código para que puedas realizar otras acciones";
	}
	
	
}
