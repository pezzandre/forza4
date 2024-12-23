import java.io.File;
import java.io.IOException;
import javax.sound.sampled.*;

// Classe che si occupa di gestire la partita
public class Partita {
    GestioneInput input;
    Colore[] match;

    // Costruttore della classe
    Partita() {
        this.input = new GestioneInput();
        this.match = new Colore[this.input.validaNumeroPartite()];
    }

    void gioco() {
        Giocatore[] giocatori = new Giocatore[2];

        // Creazione del primo giocatore
        System.out.println("\nGIOCATORE 1");
        giocatori[0] = new Giocatore(this.input.validaNome(), this.input.validaSimbolo(), this.input.validaColore());

        // Creazione del secondo giocatore con controlli
        System.out.println("\nGIOCATORE 2");
        String nome2;
        char simbolo2;

        // Validazione del nome del secondo giocatore
        do {
            nome2 = this.input.validaNome();
            if (nome2.equalsIgnoreCase(giocatori[0].nome)) {
                System.out.println("Errore! Il nome è già stato scelto dal giocatore 1. Inserire un nome diverso.");
            }
        } while (nome2.equalsIgnoreCase(giocatori[0].nome));

        // Validazione del simbolo del secondo giocatore
        do {
            simbolo2 = this.input.validaSimbolo();
            if (simbolo2 == giocatori[0].simbolo) {
                System.out.println("Errore! Il simbolo è già stato scelto dal giocatore 1. Inserire un simbolo diverso.");
            }
        } while (simbolo2 == giocatori[0].simbolo);

        // Il colore del secondo giocatore è determinato automaticamente
        giocatori[1] = new Giocatore(nome2, simbolo2, 
            (giocatori[0].colore == Colore.GIALLO) ? Colore.ROSSO : Colore.GIALLO);

        // Gioco per il numero di match determinato
        for (int i = 0; i < match.length; i++) {
            Match game = new Match(giocatori);
            match[i] = game.gioco(this.input, i, this.match.length);
        }

        // Calcolo delle statistiche
        int win1 = 0;
        for (Colore colore : match) {
            if (colore == giocatori[0].colore) {
                win1++;
            }
        }

        // Stampa delle statistiche
        System.out.println("STATISTICHE");
    System.out.println("Partite vinte da " + giocatori[1].nome + " (" +
    ((giocatori[0].colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m") +
    giocatori[1].simbolo + "\u001B[0m" + "): " + win1);

    System.out.println("Partite vinte da " + giocatori[0].nome + " (" +
    ((giocatori[1].colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m") +
    giocatori[0].simbolo + "\u001B[0m" + "): " + (match.length - win1));

        //TODO cambiare questo
        // Animazione e vincitore
        System.out.println("\n\n" + """
                                         \u001B[33m \\ | /
                            \u001B[32m \\ | /      \u001B[33m-- * --      \u001B[34m\\ | /
                            \u001B[32m-- * --      \u001B[33m/ | \\      \u001B[34m-- * --
            \u001B[31m \\ | /          \u001B[32m/ | \\                   \u001B[34m/ | \\    \u001B[35m\\ | /
            \u001B[31m-- * --                                         \u001B[35m-- * --
            \u001B[31m / | \\      \u001B[0m\\      / | |\\  | |\\  | +-- +--+      \u001B[35m/ | \\\u001B[0m
                         \\ \\/ /  | | \\ | | \\ | |-  |--+  
                          \\/\\/   | |  \\| |  \\| |__ | \
        """);
        
        System.out.println("");
        if ((match.length - win1) < win1) {
            System.out.println("VINCE IL GIOCATORE: " + giocatori[1].nome + "!");
        } else {
            System.out.println("VINCE IL GIOCATORE: " + giocatori[0].nome + "!");
        }
        this.riproduciSuono("Suono.wav");
        System.out.println("\nPARTITA TERMINATA");

        input.rilascia();
    }

    // Metodo per riprodurre un file audio
    void riproduciSuono(String nomeFile) {
        try {
            File audioFile = new File(nomeFile);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);
            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Formato audio non supportato");
                return;
            }

            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);
            audioLine.open(format);
            audioLine.start();

            byte[] buffer = new byte[1024];
            int bytesRead;

            while ((bytesRead = audioStream.read(buffer)) != -1) {
                audioLine.write(buffer, 0, bytesRead);
            }

            audioLine.drain();
            audioLine.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
    }
}
