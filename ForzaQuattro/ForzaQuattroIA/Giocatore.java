 //classe che rappresenta il giocatore che partecipa alla partita. Ogni 
 //giocatore è identificato dal nome e dal simbolo del gettone.

enum Colore{
    GIALLO, ROSSO
}

public class Giocatore {

    String nome;
    Colore colore;
    boolean computer;

    Giocatore(String nome, Colore colore, boolean computer){
        this.nome = nome;
        this.colore = colore;
        this.computer = computer;
    }
}