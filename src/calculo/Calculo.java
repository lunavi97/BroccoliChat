package calculo;


import java.util.StringTokenizer;

// Clase que calcula el resultado de una expresi�n recibida por string a trav�s de un �rbol
public class Calculo {

	private Pila pOperandos;              	// Pila de operandos
	private Pila pOperadores;             	// Pila de operadores
	private final String espacioEnBlanco; 	// Cadena de espacios en blanco
	private final String operadores;		// Cadena con operadores para expresiones
	private String[][] reemplazarPorNegativo = {{"+-", "+N"}, {"*-", "*N"}, {"--", "-N"}, {"/-", "/N"}, {"^-", "^N"}, {"(-", "(N"}};

	public Calculo() {
		pOperandos = new Pila();
		pOperadores = new Pila();
		espacioEnBlanco = " \t";
		operadores = ")+-*/N^%(";  // Acomodados por precedencia
	}

	public double calcular(String expresion) {
		expresion = normalizar(expresion);
		NodoArbol n = construirArbol(expresion);
		return operar(n);
	}

	private String normalizar(String expresion) { 
		// Diferenciar el operador resta (binario) con los n�meros negativos (unario)
		if(expresion.startsWith("-")) // Si el primer caracter es - reemplazar por N
			expresion = "N" + expresion.substring(1);
		for(String[] reemplazo : reemplazarPorNegativo) { // Hacer los reemplazos correspondientes al operador unario
			expresion = expresion.replace(reemplazo[0], reemplazo[1]);
		}
		
		return expresion;
	}

	// Construir un �rbol a partir de una expresi�n aritm�tica
	private NodoArbol construirArbol(String expresion) {
		StringTokenizer tokenizer;
		String token;
		NodoArbol raiz = null;

		tokenizer = new StringTokenizer(expresion, espacioEnBlanco + operadores, true);

		while (tokenizer.hasMoreTokens()) {

			token = tokenizer.nextToken();

			if (espacioEnBlanco.indexOf(token) >= 0) 
				// Si es un espacio en blanco: ignorar
				;

			else if (operadores.indexOf(token) < 0) {
				// Si es un operando: guardar como nodo del �rbol
				pOperandos.poner(new NodoArbol(token));

			} else if(token.equals(")")) {
				// Sacar elementos hasta encontrar (
				while (!pOperadores.estaVacia() && !pOperadores.verTope().equals("(")) {
					guardarSubArbol();
				}
				pOperadores.sacar();  // Sacar el par�ntesis izquierdo

			} else {
				if (!token.equals("(") && !pOperadores.estaVacia()) {
					// Operador diferente de cualquier par�ntesis
					String op = (String) pOperadores.verTope();
					while (!op.equals("(") && !pOperadores.estaVacia()
							&& operadores.indexOf(op) >= operadores.indexOf(token)) {
						guardarSubArbol();
						if (!pOperadores.estaVacia()) 
							op = (String) pOperadores.verTope();
					}
				}

				pOperadores.poner(token);  // Guardar el operador
			} // else

		} // while (tokenizer.hasMoreTokens())

		// Sacar todo lo que queda
		raiz = (NodoArbol)pOperandos.verTope();

		while (!pOperadores.estaVacia()) {
			if (pOperadores.verTope().equals("(")) {
				pOperadores.sacar();
			} else {
				guardarSubArbol();
				raiz = (NodoArbol) pOperandos.verTope();
			}
		}

		return raiz;
	}

	// Almacenar un sub�rbol en la pila
	private void guardarSubArbol() {
		NodoArbol op2 = (NodoArbol) pOperandos.sacar();
		NodoArbol op1 = (NodoArbol) pOperandos.sacar();

		if(pOperadores.verTope().equals("%") || pOperadores.verTope().equals("N")) {
			pOperandos.poner(op1);
			pOperandos.poner(new NodoArbol(op2, pOperadores.sacar(), null));
		} else {
			pOperandos.poner(new NodoArbol(op1, pOperadores.sacar(), op2));
		}
	}

	private boolean esOperador(Object valor) {
		return valor.equals("+") || valor.equals("-") || valor.equals("*")
				|| valor.equals("/") || valor.equals("^") || valor.equals("%") || valor.equals("N");
	}

	private double operar(NodoArbol n) {

		if(esOperador(n.obtenerValor())) {
			// Si es operador: hacer operaci�n
			return operacion(n);
		}

		else {
			// Si es n�mero: devolverlo
			return Double.parseDouble((String) n.obtenerValor());
		}
	}

	@SuppressWarnings("null")
	private double operacion(NodoArbol operador) {
		if(operador.obtenerValor().equals("+")) {
			return operar(operador.nodoIzquierdo()) + operar(operador.nodoDerecho());
		}

		else if(operador.obtenerValor().equals("-")) {
			return operar(operador.nodoIzquierdo()) - operar(operador.nodoDerecho());

		}
		else if(operador.obtenerValor().equals("*"))
			return operar(operador.nodoIzquierdo()) * operar(operador.nodoDerecho());

		else if(operador.obtenerValor().equals("/"))
			return operar(operador.nodoIzquierdo()) / operar(operador.nodoDerecho());

		else if(operador.obtenerValor().equals("^"))
			return Math.pow(operar(operador.nodoIzquierdo()), operar(operador.nodoDerecho()));

		else if(operador.obtenerValor().equals("%"))
			return operar(operador.nodoIzquierdo()) / 100.0;

		else if(operador.obtenerValor().equals("N"))
			return 0.0 - operar(operador.nodoIzquierdo());

		else
			return (Double) null;
	}

}
