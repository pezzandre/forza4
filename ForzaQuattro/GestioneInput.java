import java.util.Scanner;
    //classe di utilità che gestisce l’input da parte dell’utente. Dovrà
    //esisterne un’unica istanza in tutto il programma, e dovrà essere passata ove
    //necessario alle classi che ne faranno uso tramite parametro del costruttore.
    //Dovrà essere presente un metodo, void rilascia(), che al suo interno chiuda
    //lo scanner. Questo metodo dovrà essere chiamato come ultima istruzione
    //dell’intero programma.
public class GestioneInput {

    Scanner input;

    // Costruttore che inizializza lo scanner
    GestioneInput() {
        this.input = new Scanner(System.in);
    }

    // Metodo per validare l'inserimento del nome (trim rimuove spazi iniziali e finali)
    String validaNome() {
        System.out.print("Inserire il nome: ");
        String nome;

        while ((nome = input.nextLine().trim()).isEmpty()) {
            System.out.print("Errore! Inserire il nome: ");
        }

        return nome;
    }

    // Metodo per validare l'inserimento di un simbolo
    char validaSimbolo() {
        System.out.print("Inserire il simbolo desiderato: ");
        String inputString;

        while ((inputString = input.nextLine().trim()).length() != 1) {
            System.out.print("Errore! Inserire un simbolo valido: ");
        }

        return inputString.charAt(0); // Ritorna il primo carattere
    }

    // Metodo per validare l'inserimento di un colore
    Colore validaColore() {
        boolean risultato = false;
        String valore = "";

        System.out.print("Che colore vuoi? ");
        for (int i = 0; i < Colore.values().length; i++) {
            if (i == Colore.values().length - 1) {
                System.out.print(Colore.values()[i].name());
            } else {
                System.out.print(Colore.values()[i].name() + " o ");
            }
        }
        System.out.println("?");

        while (!risultato) {
            System.out.print("Inserire un colore: ");
            valore = input.nextLine().trim().toUpperCase();

            if (!valore.isEmpty()) {
                for (Colore val : Colore.values()) {
                    if (val.name().equals(valore)) {
                        risultato = true;
                        break;
                    }
                }
            }

            if (!risultato) {
                System.out.println("Valore non valido. Riprovare!");
            }
        }

        return Colore.valueOf(valore);
    }

    // Metodo per validare l'inserimento della mossa
    int validaMossa() {
        int numero = 0;
        System.out.print("Inserire il numero della colonna che si vuole inserire (da 1 a 7): ");

        while (numero < 1 || numero > 7) {
            while (!input.hasNextInt()) {
                System.out.print("Inserire un intero: ");
                input.nextLine();
            }
            numero = input.nextInt();

            if (numero < 1 || numero > 7) {
                System.out.print("Errore! Inserire il numero della colonna che si vuole inserire (da 1 a 7): ");
            }

            input.nextLine(); // Pulisce il buffer
        }

        return numero;
    }

    // Metodo per validare il numero di partite da giocare
    int validaNumeroPartite() {
        int numero = 0;
        System.out.print("Inserire il numero di partite da giocare (da 1 a 15, SOLO NUMERI DISPARI): ");

        while (numero < 1 || numero > 15 || numero % 2 == 0) {
            while (!input.hasNextInt()) {
                System.out.print("Inserire un intero: ");
                input.nextLine();
            }
            numero = input.nextInt();

            if (numero < 1 || numero > 15 || numero % 2 == 0) {
                System.out.print("Errore! Inserire il numero di partite da giocare (da 1 a 15, SOLO NUMERI DISPARI): ");
            }

            input.nextLine(); // Pulisce il buffer
        }

        return numero;
    }

    // Metodo per chiudere lo scanner
    void rilascia() {
        input.close();
    }
}