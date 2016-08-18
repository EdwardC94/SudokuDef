package sudokudef;

import java.util.ArrayList;
import java.util.Iterator;


public class ConjuntoArreglo<T> implements ConjuntoADT<T> {
    
    private ArrayList<T> colec;
    
    public ConjuntoArreglo(){
        colec=new ArrayList<>();
        
    }    

    @Override
    public boolean agrega(T otro) {
        boolean resp=!colec.contains(otro);
        if(resp && otro!=null){
            colec.add(otro);
        }
        return resp;
    }
    @Override
    public T remove(T elemento) {
        T x=null;
        if(colec.contains(elemento))
            x=colec.remove(colec.lastIndexOf(elemento)); 
        return x;
    }
    public T removeLast(){
        T x=null;
        if(colec.size()>0)
            x=colec.remove(colec.size()-1);
        return x;
    }

    @Override
    public boolean estaVacio() {
        return colec.isEmpty();
    }
   
    @Override
    public boolean contiene(T elemento) {        
        return colec.contains(elemento);        
    }

    @Override
    public ConjuntoArreglo<T> union(ConjuntoArreglo<T> otro) {
        ConjuntoArreglo<T> nuevo=new ConjuntoArreglo<>();
        Iterator it1=iterator();
        Iterator it2=otro.iterator();
        while(it2.hasNext())            
            nuevo.agrega((T)it2.next());           
        while(it1.hasNext())
            nuevo.agrega((T)it1.next());            
        return nuevo;
    }

    @Override
    public ConjuntoArreglo<T> interseccion(ConjuntoArreglo<T> otro) {
        ConjuntoArreglo<T> nuevo=new ConjuntoArreglo<>();
        Iterator it=iterator();
        while(it.hasNext()){
            T x=(T)it.next();
            if(otro.contiene(x))
                nuevo.agrega(x);            
        }
        return nuevo;
    }

    @Override
    public ConjuntoArreglo<T> diferencia(ConjuntoArreglo<T> otro) {
        ConjuntoArreglo<T> nuevo=new ConjuntoArreglo<>();        
        Iterator it=iterator();
        while(it.hasNext()){
            T x=(T)(it.next());
            if(!(otro.contiene(x)))
                nuevo.agrega(x);
        }
    return nuevo;
    }

    @Override
    public boolean equals(ConjuntoArreglo<T> otro) {
        boolean resp=true;
        if(colec.size()==otro.colec.size()){
           Iterator it=iterator(), it2=otro.iterator();
           while(it.hasNext() && resp){
               try{
               if(!(it.next().equals(it2.next())))
                   resp=false;}
               catch(NullPointerException e){
                   return resp;
               }
           }
        }else
            resp=false;
        return resp;
    }

    @Override
    public Iterator<T> iterator() {
        return colec.iterator();
    }
    
    @Override
    public String toString(){
        StringBuilder cad=new StringBuilder();
        Iterator<T> it=iterator();
        while(it.hasNext()){
            String s=it.next()+"";
            if(!(s.equals("null")))
                cad.append(s).append(" ");
        }
        return cad.toString();
    }
    
    @Override
    public int getCardinalidad(){
        return colec.size();
    }
    public T removeFirst(){
        T x=null;
        if(colec.size()>0){
            x=colec.get(0);
        }
        return x;
    }
    public static void main(String[] args){
        
        ConjuntoArreglo<Integer> a=new ConjuntoArreglo<>(), b=new ConjuntoArreglo<>(), c;
        for(int i=0; i<5; i++){
            a.agrega(i);
            b.agrega(i+3);
        }
        c=a.union(b);
        System.out.println(c.diferencia(a).toString());
    }
}

