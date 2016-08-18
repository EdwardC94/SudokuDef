package sudokudef;

public class BruteForce {
    
    Integer[][] sudoku;
    ConjuntoArreglo<Integer> c1,intocables;
    int tamano;
    
    public BruteForce(Integer[][] a, int tam){        
        c1 = new ConjuntoArreglo<>();
        intocables = new ConjuntoArreglo<>();
        tamano=tam;
        sudoku=a;
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
    public void llenaInt(int m, int n, int fila, int col){
        if(m>0){
            if(n>0){
                if(sudoku[m-1][n-1]!=null)
                    intocables.agrega(9*(m-1)+n-1);
                llenaInt(m, n-1, fila, col);                    
            }else
                llenaInt(m-1, col, fila, col);            
        }
    }
    public void cuad2(int m, int n, int fila, int col, int mO, int nO){
        if(fila>m){
            if(col>n){
                if(9*(fila-1)+col-1 != 9*(mO-1)+nO-1)
                    c1.remove(sudoku[fila-1][col-1]);
                //System.out.println(c1.toString()+"   3");
                cuad2(m, n, fila, col-1, mO, nO);
            }else
                cuad2(m, n, fila-1, n+3, mO, nO);                        
        }        
    }
    public void llenaC1(int m, int n, int fila, int col){
        if(fila>0){
            if(sudoku[fila-1][n-1]!=null && fila!=m)
                c1.remove(sudoku[fila-1][n-1]);
            //System.out.println(c1.toString()+"   1");
            llenaC1(m, n, fila-1, col);
        }else{
            if(col>0){
                if(sudoku[m-1][col-1]!=null && col!=n)
                    c1.remove(sudoku[m-1][col-1]);
                //System.out.println(c1.toString()+"   2");
                llenaC1(m, n, fila, col-1);
            }else{
                fila=m-1+(Math.abs(((m-1)%3)-3));
                col=n-1+(Math.abs(((n-1)%3)-3));
                int mO=m;
                int nO=n;
                cuad2(fila-3, col-3, fila, col, mO, nO);
            }
        }
    }
    public void pos(int m, int n){
        c1=new ConjuntoArreglo<>();
        for(int i=1;i<tamano+1;i++){
            c1.agrega(i);
        }
        llenaC1(m, n, tamano, tamano);
    }
    public void bruteF(int m, int n, int col){
        if(m>0){
            if(n>0){
                while(intocables.contiene(9*(m-1)+n-1)){
                    if(n>1)
                        n--;
                    else{
                        m--;
                        n=9;
                    }
                }
                pos(m, n);
                do{
                sudoku[m-1][n-1]=sudoku[m-1][n-1]!=null ?                        
                        sudoku[m-1][n-1]+1:                        
                        1;
                }while(!c1.contiene(sudoku[m-1][n-1]) && sudoku[m-1][n-1]<10);
                if(sudoku[m-1][n-1]<10)                
                    bruteF(m, n-1, col);
                else{
                    sudoku[m-1][n-1]=null;
                    do{
                        if(n<tamano)
                            n++;
                        else{
                            m++;
                            n=1;
                        }
                    }while(intocables.contiene(9*(m-1)+n));
                    bruteF(m, n, col);                    
                }
            }else
                bruteF(m-1, col, col);
        }            
    }
    public Integer[][] ya(){
        llenaInt(tamano, tamano, tamano, tamano);
        bruteF(tamano, tamano, tamano);
        return sudoku;
    }
    
}
