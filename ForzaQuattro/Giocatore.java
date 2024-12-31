 // L'Enum Colore definisce i colori disponibili per i giocatori.
 // Ogni giocatore può scegliere tra 2 colori: GIALLO o ROSSO.
enum Colore{
    GIALLO, ROSSO
}

// La classe Giocatore rappresenta un giocatore che partecipa alla partita.
// Ogni giocatore è identificato da un nome, un simbolo e un colore.
public class Giocatore {
    String nome; // Nome del giocatore.
    char simbolo; // Simbolo che viene scelto dal giocatore.
    Colore colore; // Colore del giocatore, scelto tra i valori definiti nell'enum Colore (GIALLO o ROSSO).

 // Costruttore della classe:
 // Permette di creare un'istanza di Giocatore, inizializzando il nome, il simbolo e il colore.
    Giocatore(String nome, char simbolo, Colore colore){
        this.nome = nome; // Assegna il nome passato al costruttore all'attributo `nome`.
        this.simbolo = simbolo;  // Assegna il simbolo passato al costruttore all'attributo `simbolo`.
        this.colore = colore; // Assegna il colore passato al costruttore all'attributo `colore`.
    }
}
