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
    
    public TresenRaya() {
        this(0, ' ');
    }
    
    public TresenRaya(int dif) {
        this(dif, ' ');
    }
    
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
    
    public char getPos(int x, int y) {
        return this.tablero[x][y];
    }
    
    public char getJug() {
        return this.jug;
    }

    public void setJug(char jug) {
        this.jug = jug;
    }

    public char getCpu() {
        return this.cpu;
    }

    public void setCpu(char cpu) {
        this.cpu = cpu;
    }
    
    /*
     *  Innecesario
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
    
    public void jugador(int x, int y) {
        if (x < 0 || x >= 3 || y < 0 || y >= 3) {
            return;
        }
        
        if (tablero[x][y] == cv) {
            tablero[x][y] = jug;
            if (this.hayEspacio() > 0) {
                this.maquina();
            }
        }        
    }

    public void maquina() {
        // dificultad 0 -> posicion aleatoria
        if (this.dif == 0) {
            Random rnd = new Random();
            int x, y;
            do {
                x = rnd.nextInt(3);
                y = rnd.nextInt(3);
            } while (this.tablero[x][y] != cv);
            this.tablero[x][y] = cpu;
        }
        // dificultad 1 -> busca tres en raya
        if (this.dif == 1) {
            
        }
    }
    
    public int hayEspacio() {
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
