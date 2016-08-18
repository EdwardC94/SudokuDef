package sudokudef;

import java.util.ArrayList;

public class Sudoku2 {
    
   
    private String mensaje;
    private boolean cambio, validacion;
    private int tam, llenoI, llenoIAux;
    private Integer[][] sudoku, original;
    private ArrayList<ConjuntoArreglo<Integer>> b;
    private ArrayList<ArrayList<ConjuntoArreglo<Integer>>> posibles; 
    private ConjuntoArreglo<Integer> c, cVal, dVal, eVal,lista, auxL2;
    
    
    /**
     *Crea un objeto de la clase "Sudoku". Mas concretamente crea una matriz inicial
     * que sera afectada por los demas metodos para llegar a una solucion si esta es 
     * posible.
     * 
     * @param matriz La matriz que se tiene que resolver, tiene que ser una matriz valida.
     * @param tamano La cantidad de celdas que tendra la matriz sera igual al tamano al cuadrado.
     */
    public Sudoku2(Integer[][] matriz, int tamano){
        this.posibles = new ArrayList<>();
        this.auxL2 = new ConjuntoArreglo<>();
        this.lista = new ConjuntoArreglo<>();
        this.eVal = new ConjuntoArreglo<>();
        this.dVal = new ConjuntoArreglo<>();
        this.cVal = new ConjuntoArreglo<>();
        this.c = new ConjuntoArreglo<>();
        this.b = new ArrayList<>();
        this.validacion = true;
        this.cambio = false;
        this.llenoI = 0;
        this.sudoku = new Integer[9][9];
        sudoku=matriz;
        original=sudoku;
        for(int i=1; i<10; i++)
            lista.agrega(i);
        tam=tamano;
    }
    public ArrayList<ArrayList<ConjuntoArreglo<Integer>>> getPosibles(){
        return posibles;
    }
    /**
     *Este metodo escanea todas las celdas de la matriz que se encuentra en esta clase
     * en busca de una celda cuya
     * cantidad de candidatos posibles sea igual a 1. En caso de no encontrar ninguno, la 
     * matriz no se ve afectada. Si si es capaz de encontrar uno, el valor en esa celda, que antes
     * necesariamente era null, es igual al unico candidato posible de esa celda, despuÃ©s se vuelve
     * a escanear la celda desde el principio.
     * 
     * @param m Numero de filas en la matriz    
     * @param n Numero de columnas en la matriz
     * @param fila Constante que se utiliza para regresar a m a su valor original
     * @param col Constante que se utiliza para regresar a n a su valor original
     * 
     * 
     */
    public void logica1(int m, int n, int fila, int col){
        if(m>0){
            if(n>0){
                if(sudoku[m-1][n-1] == null){
                    llenaPosibles(m, n, 9, 9);
                    c=lista.diferencia(c);
                    if(c.getCardinalidad()==1){
                        sudoku[m-1][n-1]=c.removeLast();
                        cambio=true;
                        c=new ConjuntoArreglo<>();
                        b=new ArrayList<>();
                        posibles=new ArrayList<>();
                        logica1(9,9,9,9);                        
                    }
                }                
                b.add(c);
                c=new ConjuntoArreglo<>();
                if(cambio){
                    c=new ConjuntoArreglo<>();
                    b=new ArrayList<>();
                    posibles=new ArrayList<>();
                    cambio=false;
                    m=9;
                    n=10;
                }
                logica1(m, n-1, fila, col);
            }else{
                posibles.add(b);
                b=new ArrayList<>();
                logica1(m-1, col, fila, col); 
            }
        }
    }
    public void logicaIn(int m, int n, int fila, int col){
        if(m>0){
            if(n>0){
                if(sudoku[m-1][n-1] == null){
                    llenaPosibles(m, n, 9, 9);
                    c=lista.diferencia(c);                    
                }                
                b.add(c);
                c=new ConjuntoArreglo<>();
                
                logicaIn(m, n-1, fila, col);
            }else{
                posibles.add(b);
                b=new ArrayList<>();
                logicaIn(m-1, col, fila, col); 
            }
        }
    }
    public void logica1In2(int m, int n, int fila, int col){
        if(m>0 && validacion){
            if(n>0 && validacion){
                if(sudoku[m-1][n-1] == null){
                    llenaPosiblesIN(m, n, 9, 9);                
                }    
                cVal=new ConjuntoArreglo<>();
                dVal=new ConjuntoArreglo<>();
                eVal=new ConjuntoArreglo<>();
                logica1In2(m, n-1, fila, col);
            }else{
                logica1In2(m-1, col, fila, col); 
            }
        }
    }
    /**
     *Es metodo escanea toda la matriz en busca de alguna columna, fila o seccion
     * de 3*3 que contenga una celda en la cual 
     * 
     * @param m Numero de filas en esta matriz
     * @param n Numero de columnas en esra 
     * @param fila
     * @param col
     */
    public void logica2(int m, int n, int fila, int col){
        if(m>0){
            if(n>0){
                if(sudoku[m-1][n-1]==null){
                    auxL2=new ConjuntoArreglo<>();
                    while(col>0){
                        if(col!=n)
                            auxL2=auxL2.union(posibles.get(Math.abs(m-9)).get(Math.abs(col-9)));
                        col--;
                    }
                    auxL2=posibles.get(Math.abs(m-9)).get(Math.abs(n-9)).diferencia(auxL2);
                    if(auxL2.getCardinalidad()==1){
                        sudoku[m-1][n-1]=auxL2.removeLast();
                        col=9;
                        c=new ConjuntoArreglo<>();
                        b=new ArrayList<>();
                        posibles=new ArrayList<>();
                        logica1(9,9,9,9);                        
                    }else{
                        auxL2=new ConjuntoArreglo<>();
                        while(fila>0){
                            if(fila!=m)
                                auxL2=auxL2.union(posibles.get(Math.abs(fila-9)).get(Math.abs(n-9)));
                            fila--;
                        }
                        auxL2=posibles.get(Math.abs(m-9)).get(Math.abs(n-9)).diferencia(auxL2);                       
                        if(auxL2.getCardinalidad()==1){
                            sudoku[m-1][n-1]=auxL2.removeLast();
                            c=new ConjuntoArreglo<>();
                            b=new ArrayList<>();
                            posibles=new ArrayList<>();
                            logica1(9,9,9,9);
                        }else{
                            auxL2=new ConjuntoArreglo<>();
                            fila=m-1+(Math.abs(((m-1)%3)-3));
                            col=n-1+(Math.abs(((n-1)%3)-3));
                            cuad2(fila-3, col-3, fila, col, m, n);
                            auxL2=posibles.get(Math.abs(m-9)).get(Math.abs(n-9)).diferencia(auxL2);
                            fila=tam;
                            col=tam;
                            if(auxL2.getCardinalidad()==1){
                                sudoku[m-1][n-1]=auxL2.removeFirst();
                                c=new ConjuntoArreglo<>();
                                b=new ArrayList<>();
                                posibles=new ArrayList<>();
                                logica1(tam, tam, tam ,tam);
                            }
                        }
                    }
                }
                logica2(m, n-1, fila, col);
            }else
        logica2(m-1, col, fila, col);
        }
    }
    public boolean validacion(){
        logica1In2(9,9,9,9);
        return validacion;
    }
    public void lleno(int m, int n, int fila, int col){
        if(m>0){
            if(n>0){
                if(sudoku[m-1][n-1]!=null)
                    llenoI++;
                lleno(m, n-1, fila, col);
            }else
            lleno(m-1, col, fila, col);
        }
    }
    public Integer[][] resolver(){
        try{
            llena:        
            do{
                c=new ConjuntoArreglo<>();
                b=new ArrayList<>();
                posibles=new ArrayList<>();
                logica1(9,9,9,9);
                if(!validacion){
                    break llena;
                }
                logica2(9,9,9,9);
                llenoIAux=llenoI;
                llenoI=0;
                lleno(9,9,9,9);
                if(llenoI==llenoIAux){
                    break llena;
                }
            }while(llenoI!=81);
            
            if(llenoI!=81 && validacion){
                BruteForce bf=new BruteForce(sudoku, 9);
                sudoku=bf.ya();
                lleno(9,9,9,9);
                if(llenoI==llenoIAux){
                    validacion=false;
                    mensaje="No puedo dar solucion a este problema";
                }
            }
        }catch(NumberFormatException | StackOverflowError | ArrayIndexOutOfBoundsException e){
            validacion=false;
            mensaje="Solo se pueden introducir numeros enteros del 1 al 9"+e.getMessage();
        }
        //imprimeSud(9,9,9,9);
        return sudoku;
    }
    public Integer[][] getOriginal(){
        return original;
    }
    
    public void llenaPosiblesIN(int m, int n, int fila, int col){     
        if(fila>0 && validacion){
            if(sudoku[fila-1][n-1]!=null)
                if(sudoku[fila-1][n-1]>9){
                    validacion=false;
                    mensaje="Solo se pueden insertar numeros enteros del 1 al 9";
                }else
                validacion=cVal.agrega(sudoku[fila-1][n-1]);
            if(!validacion)
                mensaje="Error debido a que hay\n mas de un "+sudoku[fila-1][n-1]+
                        " en el sudoku\n en la columna: "+n;
            llenaPosiblesIN(m, n, fila-1, col);            
        }else{
            if(col>0 && validacion){
                if(sudoku[m-1][col-1]!=null)
                    validacion=dVal.agrega(sudoku[m-1][col-1]);
                if(!validacion)
                mensaje="Error debido a que hay\n mas de un "+sudoku[m-1][col-1]+
                        " en el sudoku\n en la fila: "+m;
                llenaPosiblesIN(m, n, fila, col-1);                
            }else{
                if(validacion){
                fila=m-1+(Math.abs(((m-1)%3)-3));
                col=n-1+(Math.abs(((n-1)%3)-3));
                cuadIN(fila-3, col-3, fila, col);
                }
            }
        }
    }
    public void cuadIN(int m, int n, int fila, int col){
        if(fila>m && validacion){
            if(col>n){
                if(sudoku[fila-1][col-1]!=null)
                    validacion=eVal.agrega(sudoku[fila-1][col-1]);
                if(!validacion)
                mensaje="Error debido a que hay mas\n de un "+sudoku[fila-1][col-1]+
                        " en el sudoku en\n la seccion: "+(m+n/3+1);
                cuadIN(m, n, fila, col-1);
            }else
                cuadIN(m, n, fila-1, n+3);                        
        }        
    }
    public String getMensaje(){
        return mensaje;
    }
    
    public void llenaPosibles(int m, int n, int fila, int col){     
        if(fila>0){
            if(sudoku[fila-1][n-1]!=null)
                if(sudoku[fila-1][n-1]>9){
                    validacion=false;
                    mensaje="Solo se pueden insertar numeros enteros del 1 al 9";
                    fila=0;
                }else
                c.agrega(sudoku[fila-1][n-1]);
            llenaPosibles(m, n, fila-1, col);            
        }else{
            if(col>0){
                if(sudoku[m-1][col-1]!=null)
                    if(sudoku[m-1][col-1]>9){
                        validacion=false;
                        mensaje="Solo se pueden insertar numeros enteros del 1 al 9";
                        col=0;
                    }else
                    c.agrega(sudoku[m-1][col-1]);
                llenaPosibles(m, n, fila, col-1);                
            }else{
                fila=m-1+(Math.abs(((m-1)%3)-3));
                col=n-1+(Math.abs(((n-1)%3)-3));
                cuad(fila-3, col-3, fila, col);
            }
        }
    }
    public void cuad(int m, int n, int fila, int col){
        if(fila>m){
            if(col>n){
                if(sudoku[fila-1][col-1]!=null)
                    if(sudoku[fila-1][col-1]>9){
                        validacion=false;
                        mensaje="Solo se pueden insertar numeros enteros del 1 al 9";
                        fila=m;
                    }else
                    c.agrega(sudoku[fila-1][col-1]);
                cuad(m, n, fila, col-1);
            }else
                cuad(m, n, fila-1, n+3);                        
        }        
    }
    public void cuad2(int m, int n, int fila, int col, int mO, int nO){
        if(fila>m){
            if(col>n){
                if(9*(fila-1)+col-1 != 9*(mO-1)+nO-1)
                    auxL2=auxL2.union(posibles.get(Math.abs(fila-9)).get(Math.abs(col-9)));
                cuad2(m, n, fila, col-1, mO, nO);
            }else
                cuad2(m, n, fila-1, n+3, mO, nO);                        
        }        
    }
    public void imprimeSud(int m, int n, int fila, int col){
        if(m>0){
            if(n>0){
                if(sudoku[m-1][n-1]!=null)
                    System.out.print(sudoku[m-1][n-1]+"   ,");
                else    
                    System.out.print("null,");
                imprimeSud(m, n-1, fila, col);
            }else{
                System.out.println("");
                imprimeSud(m-1, col, fila, col);
            }
        }
    }
    public static void main(String[] args) {        
        Integer[][] def={
            {null, null, 6   ,    null, null, null,    8   , null, null},
            {null, null, null,    null, 9   , 7   ,    5   , null, 1   },
            {null, 2   , null,    null, null, null,    7   , 4   , null},
            
            {null, null, 5  ,    null, 4   , null,    1   , null, null},
            {null, null, 9  ,    1   , null, null,    null, 7   , 4   },
            {null, null, 3  ,    9   , null, null,    null, null, null},
            
            {null, null, null,    7   , 8   , 3   ,    null, null, null},
            {null, null, null,    null, null, null,    null, 3   , null},
            {null, 8   , null,    null, null, null,    6   , null, null}
        };
        
        Integer[][] pru2={
            {null, null, null,    null, null, null,    null, null, null},
            {null, null, null,    null, null, null,    null, null, null},
            {null, null, null,    null, null, null,    null, null, null},
            
            {null, null, null,    null, null, null,    null, null, null},
            {null, null, null,    null, null, null,    null, null, null},
            {null, null, null,    null, null, null,    null, null, null},
            
            {null, null, null,    null, null, null,    null, null, null},
            {null, null, null,    null, null, null,    null, null, null},
            {null, null, null,    null, null, null,    null, null, null},
        };
        Integer[][] e={
            {null, 7, null, 3, null, null, null, null, null},
            {3, 5, null, 2, null, null, null, null, null},
            {null, null, 2, null, null, null, 8, null, null},
            {6, null, null, null, 5, 7, null, null, 4},
            {null, null, null, null, null, null, null, 2, null},
            {null, null, 7, null, null, null, 6, null, 8},
            {null, 6, null, null, 7, 4, null, null, 5},
            {5, null, 9, null, null, null, null, null, null},
            {null, 4, 8, null, null, 2, 3, null, null}
        };
        Sudoku2 s2=new Sudoku2(def,9);
        s2.resolver();   
        s2.imprimeSud(9,9,9,9);
    }
}

