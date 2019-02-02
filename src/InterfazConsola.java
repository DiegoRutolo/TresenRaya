
import java.util.Scanner;
import tresenraya.TresenRaya;

/**
 * @author a16diegoar
 */
public class InterfazConsola {
    public static void dibujaTablero(TresenRaya tr) {
        System.out.println("┌─┬─┬─┐");
        System.out.println("│" + tr.charAt(0, 0) + "│" + tr.charAt(1, 0) + "│" + tr.charAt(2, 0) + "│");
        System.out.println("├─┼─┼─┤");
        System.out.println("│" + tr.charAt(0, 1) + "│" + tr.charAt(1, 1) + "│" + tr.charAt(2, 1) + "│");
        System.out.println("├─┼─┼─┤");
        System.out.println("│" + tr.charAt(0, 2) + "│" + tr.charAt(1, 2) + "│" + tr.charAt(2, 2) + "│");
        System.out.println("└─┴─┴─┘\n");
    }
    
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nDificultad [0,1,2] -> ");
        int dif = sc.nextInt();
        System.out.print("Numero de partidas -> ");
        int nPart = sc.nextInt();
        int pJug = 0;
        int pMaq = 0;
        for (; nPart > 0; nPart--) {
            TresenRaya tr = new TresenRaya(dif);
            System.out.println("El jugador es " + tr.getJug());
            dibujaTablero(tr);
            while (tr.espaciosLibres() > 0) {
                //  el jugador mueve
                //      la máquina va automáticamente despues
                tr.jugador(sc.nextInt(), sc.nextInt());
                System.out.print("\n");
                dibujaTablero(tr);
                if (tr.linea(tr.getJug())) {
                    System.out.println("Punto para el Jugador");
                    pJug++;
                    break;
                }
                if (tr.linea(tr.getCpu())) {
                    System.out.println("Punto para la maquina");
                    pMaq++;
                    break;
                }
            }
            System.out.println("Fin de la ronda");
            System.out.println("---\n");
        }
        
        if (pJug == pMaq) {
            System.out.println("EMPATE");
        } else if (pJug > pMaq) {
            System.out.println("GANA EL JUGADOR CON " + pJug + " puntos");
        } else {
            System.out.println("GANA LA MAQUINA CON " + pMaq + " puntos");
        }
    }
}
