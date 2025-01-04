import java.io.File; // Importa la classe File della libreria standard di Java.
import java.io.IOException; // L'eccezione IOException viene sollevata quando si verificano errori di input/output, come il mancato accesso a un file o problemi di lettura/scrittura.
import javax.sound.sampled.*; // Questo package contiene le classi necessarie per lavorare con audio, come la lettura di file audio e la riproduzione del suono.

// Classe che si occupa di gestire la partita.
public class Partita {
    GestioneInput input; // Oggetto per gestire l'input dell'utente.
    Colore[] match; // Array per memorizzare i risultati dei match (vincitore di ciascun match).

    // Costruttore della classe.
    Partita() {
        this.input = new GestioneInput();  // Inizializza l'oggetto per la gestione dell'input.
        this.match = new Colore[this.input.validaNumeroPartite()]; // Determina il numero di partite.
    }
    // Metodo principale che gestisce l'intera partita.
    void gioco() {
        Giocatore[] giocatori = new Giocatore[2]; // Array per memorizzare i due giocatori.

        // Creazione del primo giocatore.
        System.out.println("\nGIOCATORE 1");
        giocatori[0] = new Giocatore(
            this.input.validaNome(),   // Richiede e valida il nome del primo giocatore.
            this.input.validaSimbolo(), // Richiede e valida il simbolo del primo giocatore.
            this.input.validaColore()  // Richiede e valida il colore del primo giocatore.
        );

        // Creazione del secondo giocatore con controlli per evitare duplicati.
        System.out.println("\nGIOCATORE 2");
        String nome2;
        char simbolo2;

        // Validazione del nome del secondo giocatore.
        do {
            nome2 = this.input.validaNome();
            if (nome2.equalsIgnoreCase(giocatori[0].nome)) { // Controlla che il nome non sia uguale al primo giocatore.
                System.out.println("Errore! Il nome è già stato scelto dal giocatore 1. Inserire un nome diverso.");
            }
        } while (nome2.equalsIgnoreCase(giocatori[0].nome));

        // Validazione del simbolo del secondo giocatore.
        do {
            simbolo2 = this.input.validaSimbolo();
            if (simbolo2 == giocatori[0].simbolo) { // Controlla che il simbolo non sia uguale al primo giocatore.
                System.out.println("Errore! Il simbolo è già stato scelto dal giocatore 1. Inserire un simbolo diverso.");
            }
        } while (simbolo2 == giocatori[0].simbolo);

        // Il colore del secondo giocatore è automaticamente quello non scelto dal primo giocatore.
        giocatori[1] = new Giocatore(nome2, simbolo2, 
            (giocatori[0].colore == Colore.GIALLO) ? Colore.ROSSO : Colore.GIALLO);

        // Ciclo per giocare il numero di partite specificato in input dall'utente.
        for (int i = 0; i < match.length; i++) {
            Match game = new Match(giocatori);
            match[i] = game.gioco(this.input, i, this.match.length);
        }

        // Calcolo delle statistiche: conteggio delle vittorie del primo giocatore.
        int win1 = 0;
        for (Colore colore : match) {
            if (colore == giocatori[0].colore) {
                win1++;
            }
        }

        // Stampa delle statistiche.
        System.out.println("STATISTICHE");
    System.out.println("Partite vinte da " + giocatori[1].nome + " (" +
    ((giocatori[0].colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m") +
    giocatori[1].simbolo + "\u001B[0m" + "): " + win1);

    System.out.println("Partite vinte da " + giocatori[0].nome + " (" +
    ((giocatori[1].colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m") +
    giocatori[0].simbolo + "\u001B[0m" + "): " + (match.length - win1));

        
        // Dichiarazione del vincitore in base al punteggio.
        if ((match.length - win1) < win1) {
            System.out.println("\nGiocatore "+giocatori[1].nome+", ");
        } else {
            System.out.println("\nGiocatore "+ giocatori[0].nome+", ");
        }

        // Animazione della vittoria con simboli grafici.
        System.out.println("""
            \u001B[33m╔════════════════════════════════════════════════════════════════════════════╗\u001B[0m
            \u001B[33m║\u001B[0m                                                                            \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888          d8b                 d8b          888                \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888          Y8P                 Y8P          888                \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888                                           888                \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m8888888888  8888b.  888        888  888 888 88888b.  888888  .d88b.     \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888     "88b 888        888  888 888 888 "88  b888   d88""88b    \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888 .d888888 888        Y88  88P 888 888  888 888    888  888    \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888 888  888 888         Y8bd8P  888 888  888 Y88b.  Y88..88P    \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m    \u001B[32m888    888  Y888888 888          Y88P   888 888  888 "Y888   "Y88P"  🏆 \u001B[33m║\u001B[0m
            \u001B[33m║\u001B[0m                                                                            \u001B[33m║\u001B[0m
            \u001B[33m╚════════════════════════════════════════════════════════════════════════════╝\u001B[0m
            """);
        
        // Riproduzione del suono della vittoria (Happy Wheels).
        this.riproduciSuono("Suono.wav");
        System.out.println("\nPARTITA TERMINATA");

        // Metodo di chiusura dello scanner.
        input.rilascia();
    }

    // Metodo per riprodurre un file audio.
    void riproduciSuono(String nomeFile) {
        try {
            File audioFile = new File(nomeFile); // Carica il file audio.
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile); /*  Legge i dati audio dal file specificato (audioFile) e li prepara per essere elaborati o riprodotti.
                                                                                            È il primo passo per lavorare con file audio nel programma. */                                                 
            AudioFormat format = audioStream.getFormat(); // Formato del file audio.
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            // Verifica se il formato audio è supportato.
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Formato audio non supportato");
                return;
            }

            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info); // Riproduce l'audio.
            audioLine.open(format);
            audioLine.start();

            byte[] buffer = new byte[1024]; // Buffer per leggere i dati audio.
            int bytesRead;

            // Legge i dati audio e li riproduce.
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                audioLine.write(buffer, 0, bytesRead);
            }
            // Rilascia le risorse.
            audioLine.drain();
            audioLine.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace(); // Gestisce eccezioni in caso di errore.
        }
    }
}
