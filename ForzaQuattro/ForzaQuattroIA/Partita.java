import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

//classe che si occupa di gestire la partita, dalla creazione dei giocatori alla gestione dei match.


public class Partita {
    GestioneInput input;
    Colore[]  match;

    Partita(){
        this.input = new GestioneInput();
        this.match = new Colore[this.input.validaNumeroPartite()];
    }

    void PartitaGenerale(){
        System.out.print("\033[H\033[2J");
        System.out.flush();
        System.out.println("""

                --------------------- SCEGLI COME GIOCARE ---------------------

                        1   -   Gioca contro il computer
                        2   -   Gioca contro un umano

                ---------------------------------------------------------------

                """);
        int scelta = input.validaSceltaIniziale();
        System.out.print("\033[H\033[2J");
        System.out.flush();
        this.gioco(scelta);
    }
    
    void gioco(int scelta){
        Giocatore[] giocatori = new Giocatore[2];
        System.out.println("");
        System.out.println("------ GIOCATORE 1 ------");
        giocatori[0] = new Giocatore(this.input.validaNome(), this.input.validaColore(), false);
        System.out.println("");
        if(scelta==2){
            System.out.println("------ GIOCATORE 2 ------");
            giocatori[1] = new Giocatore(this.input.validaNome(), (giocatori[0].colore == Colore.GIALLO) ? Colore.ROSSO : Colore.GIALLO, false); 
        }else{
            giocatori[1] = new Giocatore("computer", (giocatori[0].colore == Colore.GIALLO) ? Colore.ROSSO : Colore.GIALLO, true); 

        }
        
        for (int i = 0; i < match.length; i++) {
            Match game = new Match(giocatori);
            match[i]=game.gioco(this.input, i, this.match.length, giocatori[1].computer);

        }
        
        int win1=0;
        for (int i = 0; i < match.length; i++) {
            if(match[i].name().equals(giocatori[0].colore.name())){
                win1++;
            }
        }
        System.out.println("""
                            +-------------------------------------------------------+
                            |                      STATISTICHE                      | 
                            +-------------------------------------------------------+
                            """);
        System.out.println("            Match vinti da "+ giocatori[0].nome+" ("+((giocatori[0].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+giocatori[0].colore+"\u001B[0m) : "+win1);
        System.out.println("            Match vinti da "+ giocatori[1].nome+" ("+((giocatori[1].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+giocatori[1].colore+"\u001B[0m) : "+(match.length-win1));

        System.out.println("");
        System.out.println("");
        System.out.println("""
                                                        \u001B[33m \\ | /
                                            \u001B[32m \\ | /      \u001B[33m-- * --      \u001B[34m\\ | /
                                            \u001B[32m-- * --      \u001B[33m/ | \\      \u001B[34m-- * --
                                \u001B[31m \\ | /       \u001B[32m/ | \\                   \u001B[34m/ | \\       \u001B[35m\\ | /
                                \u001B[31m-- * --                                         \u001B[35m-- * --
                                \u001B[31m / | \\      \u001B[0m\\      / | |\\  | |\\  | +-- +--+      \u001B[35m/ | \\\u001B[0m
                                             \\ \\/ /  | | \\ | | \\ | |-  |--+  
                                              \\/\\/   | |  \\| |  \\| |__ | \
                            """);
        System.out.println("");
        if((match.length-win1)<win1){
            System.out.println("            VINCE IL GIOCATORE: "+giocatori[0].nome+"!");
        }else{
            System.out.println("            VINCE IL GIOCATORE: "+giocatori[1].nome+"!");
        }
        this.riproduciSuono("suono_vittoria.wav");
        System.out.println("");
        System.out.println("""
                                    +---------------------------------------+
                                    |           PARTITA TERMINATA           |
                                    +---------------------------------------+
                            """);


        input.rilascia();
    }

    // Questo metodo riproduce un file audio specificato (su MAC funziona :) ) 
    void riproduciSuono(String nomeFile) {
        try {
            // Creazione di un oggetto File basato sul nome del file audio
            File audioFile = new File(nomeFile);
            
            // Ottenimento di un flusso di input audio dal file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Ottenimento del formato audio del flusso
            AudioFormat format = audioStream.getFormat();

            // Creazione di un oggetto di informazioni sulla linea audio
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);

            // Verifica se il sistema supporta la linea audio con il formato specificato
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Formato audio non supportato");
                return;
            }

            // Ottenimento della linea audio (SourceDataLine) dal sistema
            SourceDataLine audioLine = (SourceDataLine) AudioSystem.getLine(info);

            // Apertura della linea audio con il formato specificato
            audioLine.open(format);

            // Avvio della riproduzione
            audioLine.start();

            // Creazione di un buffer per leggere i dati audio
            byte[] buffer = new byte[1024];
            int bytesRead;

            // Lettura e scrittura dei dati audio nel buffer fino a quando non ci sono più dati da leggere
            while ((bytesRead = audioStream.read(buffer)) != -1) {
                audioLine.write(buffer, 0, bytesRead);
            }

            // Attende che la linea audio sia completamente svuotata prima di chiudere
            audioLine.drain();

            // Chiusura della linea audio e del flusso audio
            audioLine.close();
            audioStream.close();
        } catch (UnsupportedAudioFileException | LineUnavailableException | IOException e) {
            // Gestione delle eccezioni in caso di problemi durante la riproduzione audio
            e.printStackTrace();
        }
    }
}