package calculo;


public class NodoArbol {
	private Object valor;           // Valor almacenado en el nodo
	private NodoArbol izquierda;    // Nodo de la izquierda
	private NodoArbol derecha;      // Nodo de la derecha

	// Constructor por omisi�n. Inicializa con null el nodo creado.
	public NodoArbol () {
		this (null, null, null);
	}

	// Constructor que inicializa el nodo con valor como par�metro
	public NodoArbol (Object valor) { 
		this (null, valor, null);
	}

	// Constructor que inicializa el nodo con los par�metros
	public NodoArbol (NodoArbol iz, Object v, NodoArbol der) {
		valor = v;
		izquierda = iz;
		derecha = der;
	}
	
	public Object obtenerValor() {
		return valor;
	}
	
	public NodoArbol nodoIzquierdo() {
		return izquierda;
	}
	
	public NodoArbol nodoDerecho() {
		return derecha;
	}
}