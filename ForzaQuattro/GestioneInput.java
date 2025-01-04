import java.util.Scanner;
    /* Classe di utilità che gestisce l’input da parte dell’utente. Dovrà
       esisterne un’unica istanza in tutto il programma, e dovrà essere passata ove
       necessario alle classi che ne faranno uso tramite parametro del costruttore.
       Dovrà essere presente un metodo, void rilascia(), che al suo interno chiuda
       lo scanner. Questo metodo dovrà essere chiamato come ultima istruzione
       dell’intero programma.
    */
public class GestioneInput {

    Scanner input;

    // Costruttore che inizializza lo scanner.
    GestioneInput() {
        this.input = new Scanner(System.in);
    }

    // Metodo per validare l'inserimento del nome (trim rimuove spazi iniziali e finali).
    String validaNome() {
        System.out.print("Inserire il nome: ");
        String nome;

        while ((nome = input.nextLine().trim()).isEmpty()) {
            System.out.print("Errore! Inserire il nome: ");
        }

        return nome;
    }

    // Metodo per validare l'inserimento di un simbolo.
    // Questo metodo chiede un singolo carattere come input.
    // Se l'input inserito dall'utente non contiente un singolo carattere, viene rischiesto nuovamente.
    char validaSimbolo() {
        System.out.print("Inserire il simbolo desiderato: ");
        String inputString;
    // Verifica che l'input contenga esattamente un singolo carattere.
        while ((inputString = input.nextLine().trim()).length() != 1) { 
            System.out.print("Errore! Inserire un simbolo valido: ");
        }

        return inputString.charAt(0); // Ritorna il primo carattere.
    }

    // Metodo per validare l'inserimento di un colore.
Colore validaColore() {
    boolean risultato = false; // Variabile per tracciare se l'input è valido.
    String valore = ""; // Stringa per memorizzare il valore inserito dall'utente.

    System.out.print("Che colore vuoi? ");
    for (int i = 0; i < Colore.values().length; i++) { // Ciclo per iterare su tutti i valori dell'enum Colore.
        if (i == Colore.values().length - 1) { // Se è l'ultimo elemento.
            System.out.print(Colore.values()[i].name()); 
        } else {
            System.out.print(Colore.values()[i].name() + " o ");
        }
    }
    System.out.println("?"); 

    while (!risultato) { // Ciclo che continua ad iterare finché non viene inserito un colore valido.
        System.out.print("Inserire un colore: "); 
        valore = input.nextLine().trim().toUpperCase(); // Legge l'input dell'utente, rimuove spazi bianchi e lo converte in maiuscolo.
        
        if (!valore.isEmpty()) { // Controlla se l'input non è vuoto.
            for (Colore val : Colore.values()) { 
                if (val.name().equals(valore)) { // Confronta il valore inserito con il nome del colore dell'enum.
                    risultato = true; // Se il valore corrisponde, imposta risultato a true.
                    break; 
                }
            }
        }

        if (!risultato) { // Se il valore non è valido.
            System.out.println("Valore non valido. Riprovare!"); // Stampa un messaggio di errore.
        }
    }

    return Colore.valueOf(valore); // Restituisce il valore dell'enum Colore corrispondente alla stringa inserita.
}

    // Metodo per validare l'inserimento della mossa.
    int validaMossa() {
        int numero = 0;
        System.out.print("Inserire il numero della colonna che si vuole inserire (da 1 a 7): ");
        //controllo che il numero sia compreso tra 1 e 7.
        while (numero < 1 || numero > 7) {
            while (!input.hasNextInt()) {
                System.out.print("Inserire un intero: ");
                input.nextLine();
            }
            numero = input.nextInt();

            if (numero < 1 || numero > 7) {
                System.out.print("Errore! Inserire il numero della colonna che si vuole inserire (da 1 a 7): ");
            }

            input.nextLine(); // Pulisce il buffer.
        }

        return numero;
    }

    // Metodo per validare il numero di partite da giocare.
    int validaNumeroPartite() {
        int numero = 0;
        System.out.print("Inserire il numero di partite da giocare (da 1 a 15, SOLO NUMERI DISPARI): ");
        //controllo che il numero sia dispari.
        while (numero < 1 || numero > 15 || numero % 2 == 0) {
            while (!input.hasNextInt()) {
                System.out.print("Inserire un intero: ");
                input.nextLine();
            }
            numero = input.nextInt();

            if (numero < 1 || numero > 15 || numero % 2 == 0) {
                System.out.print("Errore! Inserire il numero di partite da giocare (da 1 a 15, SOLO NUMERI DISPARI): ");
            }

            input.nextLine(); // Pulisce il buffer.
        }

        return numero;
    }

    // Metodo per validare la scelta di continuare.
    boolean continua() {
        System.out.println("Per continuare premere INVIO");
        while (!input.hasNextLine()) { // Attende un input da tastiera.
            // Nessuna azione, si aspetta che l'utente prema INVIO.
        }
        input.nextLine(); // Consuma la riga vuota.
        return true; // Conferma che l'utente vuole continuare.
    }
    
    // Metodo per chiudere lo scanner.
    void rilascia() {
        input.close();
    }
}
