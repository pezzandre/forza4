/**
 * ForzaQuattroTest
 * 
 * Personalizzazioni:
 * 1 - Scelta di un carattere e di un colore (non possono essere uguali)
 * 2 - Pulizia dello schermo con il metodo clearScreen()
 * 3 - Colorazione della riga di vittoria
 * 5 - Musica finale
 */

public class ForzaQuattroTest {
    public static void main(String[] args){
        System.out.println("""

                REGOLE:

                1️⃣: All’inizio, i due giocatori dovranno inserire il loro nome ed il colore scelto.
                2️⃣: Inserisci il numero di partite, max 15. (SOLO DISPARI)
                3️⃣: A turno, ogni giocatore dovrà scegliere una colonna in cui inserire il suo gettone (da 1 a 7).
                4️⃣: Nel caso il tabellone si dovesse riempire senza alcun vincitore, si dovrà rifare.

                """);
        Partita partita = new Partita();
        partita.gioco();
    }
}
