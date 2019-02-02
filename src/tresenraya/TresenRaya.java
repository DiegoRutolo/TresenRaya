package tresenraya;

import java.util.Random;

/**
 * @author a16diegoar
 */
public class TresenRaya {
    private char[][] tablero = new char[3][3];
    private int dif;
    private char cv;       // caracter que indica espacio vacio
    private char jug = 'X';       // caracter del jugador
    private char cpu = 'O';       // caracter de la maquina
    
    /**
     * Inicializa el juego con dificultad 0 y espacio en blanco como
     * caracter vacío.
    */
    public TresenRaya() {
        this(3, ' ');
    }
    
    /**
     * Este constructor inicializa el juego con espacio en blanco como 
     * caracter vacío
     * 
     * @param dif   dificultad del juego
     */
    
    public TresenRaya(int dif) {
        this(dif, ' ');
    }
    
    /**
     * Inicializa el juego con dificultad <code>dif</code> y usando <code>cv</code> como casilla vacia
     * 
     * @param dif   dificultad del juego
     * @param cv    caracter que representa casilla vacía
     */
    public TresenRaya(int dif, char cv) {
        this.dif = dif;
        this.cv = cv;
        // inicializar valores del tablero
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                tablero[x][y] = cv;
            }
        }
    }
    
    /**
     * Devuelve el caracter en la posición (<code>x</code>, <code>y</code>)
     * 
     * @param x     coordenada x
     * @param y     coordenada y
     * @return      el caracter en (x, y)
     */
    public char charAt(int x, int y) {
        return this.tablero[x][y];
    }
    
    /**
     * Devuelve el caracter que representa al jugador
     * 
     * @return      caracter que representa la ficha del jugador
     */
    public char getJug() {
        return this.jug;
    }

    /**
     * Establece el caracter que representa al jugador
     * 
     * @param jug   caracter que representa la ficha del jugador
     */
    public void setJug(char jug) {
        this.jug = jug;
    }

    /**
     * Devuelve el caracter que representa la ficha de la máquina
     * 
     * @return      caracter que representa la ficha de la máquina
     */
    public char getCpu() {
        return this.cpu;
    }

    /**
     * Establece el caracter que representa la ficha de la máquina
     * 
     * @param cpu   caracter que representa la ficha de la máquina
     */
    public void setCpu(char cpu) {
        this.cpu = cpu;
    }
    
    /**
     * Imprime por salida estándar la matriz del tablero, sin decoraciones
     * pero en orden
     */
    public void imprimeTablero() {
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                System.out.print(tablero[x][y]);
            }
            System.out.print("\n");
        }
        System.out.print("\n");
    }
    
    /**
     * El jugador coloca ficha en (<code>x</code>, <code>y</code>)
     * 
     * @param x     coordenada horizontal
     * @param y     coordenada vertical
     */
    public void jugador(int x, int y) {
        if (x < 0 || x >= 3 || y < 0 || y >= 3) {
            return;
        }
        
        if (tablero[x][y] == cv) {
            tablero[x][y] = jug;
            if (this.espaciosLibres() > 0) {
                this.maquina();
            }
        }        
    }
    
    /**
     * Movimiento de la máquina
     */
    public void maquina() {
        Random rnd = new Random();
        int x, y, a, b;
        // dif 0 -> posicion aleatoria
        do {
            x = rnd.nextInt(3);
            y = rnd.nextInt(3);
        } while (this.tablero[x][y] != cv);
        
        // dif 1 -> coloca fichas contiguas
        if (this.dif >= 1) {
            do {
                x = rnd.nextInt(3);
                y = rnd.nextInt(3);
                a = rnd.nextInt(3)-1;
                b = rnd.nextInt(3)-1;
                
                if (this.charAt(x, y) == cpu && dentro(x+a, y+b) && this.charAt(x+a, y+b) == cv) {
                    // System.out.println("dif 1");
                    x = x+a;
                    y = y+b;
                    break;
                }
            } while(this.charAt(x, y) != cv);
        }
        
        // dif 2 -> busca completar linea
        if (this.dif >= 2) {
            // busca lineas horizontales
            iniHor:
            for (int i = 0; i < 3; i++) {
                if (enLinea(cpu, i, true) == 2) {
                    for (int j = 0; j < 3; j++) {
                        if (this.charAt(j, i) == cv) {
                            // System.out.println("Dif 2");
                            x = j;
                            y = i;
                            break iniHor;
                        }
                    }
                }
            }
            // busca lineas verticales
            iniVer:
            for (int i = 0; i < 3; i++) {
                if (enLinea(cpu, i, false) == 2) {
                    for (int j = 0; j < 3; j++) {
                        if (this.charAt(i, j) == cv) {
                            x = i;
                            y = j;
                            break iniVer;
                        }
                    }
                }
            }
            // busca lineas diagonales
            iniDiag:
            if (this.charAt(1, 1) == cpu) {
                if (this.charAt(0, 0) == cpu && this.charAt(2, 2) == cv) {
                    x = 2;
                    y = 2;
                } else if (this.charAt(2, 2) == cpu && this.charAt(0, 0) == cv) {
                    x = 0;
                    y = 0;
                } else if (this.charAt(2, 0) == cpu && this.charAt(0, 2) == cv) {
                    x = 0;
                    y = 2;
                } else if (this.charAt(0, 2) == cpu && this.charAt(2, 0) == cv) {
                    x = 2;
                    y = 0;
                }
            }
        }
        
        // dif 3 -> intenta bloquear al jugador
        if (this.dif >= 3){
             // busca lineas horizontales
            iniHor:
            for (int i = 0; i < 3; i++) {
                if (enLinea(jug, i, true) == 2) {
                    for (int j = 0; j < 3; j++) {
                        if (this.charAt(j, i) == cv) {
                            // System.out.println("Dif 2");
                            x = j;
                            y = i;
                            break iniHor;
                        }
                    }
                }
            }
            // busca lineas verticales
            iniVer:
            for (int i = 0; i < 3; i++) {
                if (enLinea(jug, i, false) == 2) {
                    for (int j = 0; j < 3; j++) {
                        if (this.charAt(i, j) == cv) {
                            x = i;
                            y = j;
                            break iniVer;
                        }
                    }
                }
            }
            // busca lineas diagonales
            iniDiag:
            if (this.charAt(1, 1) == jug) {
                if (this.charAt(0, 0) == jug && this.charAt(2, 2) == cv) {
                    x = 2;
                    y = 2;
                } else if (this.charAt(2, 2) == jug && this.charAt(0, 0) == cv) {
                    x = 0;
                    y = 0;
                } else if (this.charAt(2, 0) == jug && this.charAt(0, 2) == cv) {
                    x = 0;
                    y = 2;
                } else if (this.charAt(0, 2) == jug && this.charAt(2, 0) == cv) {
                    x = 2;
                    y = 0;
                }
            }
        }
        
        // intenta poner ficha en el centro
        if (this.charAt(1, 1) == cv) {
            x = 1;
            y = 1;
        }
        this.tablero[x][y] = cpu;
    }
    
    /**
     * Cuenta el número de caracteres <code>c</code> que hay alineados
     * en la linea o columna <code>n</code>.
     * 
     * @param c     caracter para comprovar
     * @param n     línea o columna a comprobar, dependiendo del valor
     *              de <code>h</code>
     * @param h     si verdadero, busca lineas horizontales y por lo tanto, 
     *              <code>n</code> se refiere a filas; si falso, busca
     *              lineas verticales
     * @return      un número entre 0 y 3, que indica cuantos caracters
     *              <code>c</code> hay alineados
     */
    public int enLinea(char c, int n, boolean h) {
        int cont = 0;
        
        if (h)  {
            for (int i = 0; i < 3; i++) {
                if (this.charAt(i, n) == c) {
                    cont++;
                }
            }
        } else {
            for (int i = 0; i < 3; i++) {
                if (this.charAt(n, i) == c) {
                    cont++;
                }
            }
        }
        
        return cont;
    }
    
    /**
     * Indica si las coordenadas (x, y) están dentro del tablero
     * 
     * @param x posicion horizontal
     * @param y posicion vertical
     * @return  verdadero si (x, y) está dentro del tablero, falso si no
     */
    public boolean dentro(int x, int y) {
        return (x >= 0 && x <= 2 && y >= 0 && y <= 2);
    }
    
    /**
     * Devuelve el número de espacios libres que quedan en el tablero
     * 
     * @return  entero en el rango [0, 9]
     */
    public int espaciosLibres() {
        int nEspacios = 0;
        for (char[] i : this.tablero) {
            for (char j : i) {
                if (j == cv) {
                    nEspacios++;
                }
            }
        }
        return nEspacios;
    }
    
    /**
     * Indica si el algún punto del tablero hay un tres en raya con el
     * caracter <code>c</code>
     * 
     * @param c     caracter a buscar
     * @return      verdadero si hay 3 en raya
     */
    public boolean linea(char c) {
        boolean linea = false;
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                if (this.tablero[x][y] == c) {
                    if (x == 1) {
                        if (this.tablero[x-1][y] == c && this.tablero[x+1][y] == c) {
                            linea = true;
                        }
                    }
                    if (y == 1) {
                        if (this.tablero[x][y-1] == c && this.tablero[x][y+1] == c) {
                            linea = true;
                        }
                    }
                    if (x == 1 && y == 1) {
                        if (this.tablero[x-1][y-1] == c && this.tablero[x+1][y+1] == c) {
                            linea = true;
                        }
                        if (this.tablero[x+1][y-1] == c && this.tablero[x-1][y+1] == c) {
                            linea = true;
                        }
                    }
                }
            }
        }
        return linea;
    }
}
