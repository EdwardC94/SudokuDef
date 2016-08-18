package sudokudef;

import java.util.Iterator;

public interface ConjuntoADT<T> extends Iterable<T> {
    
    public boolean agrega(T otro);
    //public T quitaRandom();
    public T remove(T elemento);
    public boolean estaVacio();
    //cuantos elementos tiene un conjunto   
    public int getCardinalidad();
    public boolean contiene(T elemento);
    public ConjuntoArreglo<T> union(ConjuntoArreglo<T> otro);
    public ConjuntoArreglo<T> interseccion(ConjuntoArreglo<T> otro);
    public ConjuntoArreglo<T> diferencia(ConjuntoArreglo<T> otro);
    public boolean equals(ConjuntoArreglo<T> otro);
    public Iterator<T> iterator();
    
}