 //classe che rappresenta il giocatore che partecipa alla partita. Ogni 
 //giocatore è identificato dal nome e dal simbolo del gettone.

enum Colore{
    GIALLO, ROSSO
}

public class Giocatore {
    String nome;
    char simbolo;
    Colore colore;

    Giocatore(String nome, char simbolo, Colore colore){
        this.nome = nome;
        this.simbolo = simbolo;
        this.colore = colore;
    }
}