/**
 * ForzaQuattroTest
 * 
 * Personalizzazioni:
 * 1 - Scelta solo tra giallo e rosso, non di un carattere
 * 2 - Pulizia dello schermo ocn il metodo clearScreen()
 * 3 - Illuminazione a intermittenza della riga di vittoria
 * 4 - al termine della partita si aspettano 10 secondi prima di vedere o il riepilogo o il nuovo match
 * 5 - Musica finale
 * 6 - Partita contro il computer
 * 
 * Miglioramenti:
 * 1 - Metodo Mossa Migliore del computer (match):
 *          il metodo è abbastanza complesso e fa molte operazioni, ridondanti;
 *          si dovrebbe implementare un algoritmo minimaxi per considerare una gameplay più realistico
 *          ma con il poco tempo a disposizione non è stato possibile
 * 
 * 2 - Ridondanza del codice
 * 
 */

public class ForzaQuattroTest {
    public static void main(String[] args){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("""
            
                        +----------------------------------------------------------+
                        |                                                          |
                        |   +-----  +----*  +----+  ----+   +----+        |    |   |
                        |   |       |    |  |    |     /    |    |        |    |   |
                        |   |---    |    |  |----+    /     |____|        |____|   |
                        |   |       |    |  |  \\     /      |    |             |   |
                        |   |       +----+  |   \\   +----   |    |             |   |
                        |                                                          |
                        +----------------------------------------------------------+

                """);
        Partita partita = new Partita();
        partita.PartitaGenerale();
    }
}