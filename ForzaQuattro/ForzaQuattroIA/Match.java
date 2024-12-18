import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

//classe che si occupa di gestire il match tra due giocatori seguendo le regole

public class Match {
    Giocatore[] giocatori;
    Colore[][] tabellaGioco;

    Match(Giocatore[] giocatori){
        this.giocatori = new Giocatore[2];
        this.tabellaGioco = new Colore[6][7];
        this.giocatori[0]=giocatori[0];
        this.giocatori[1]=giocatori[1];
    }
    
    Colore gioco(GestioneInput input, int index, int numeroMatchTotale, boolean computer){
        LocalTime adesso, scadenza;
        int index1=0, index2=0;
        String metodoVerifica="";

        int toccaA=0;
        if(this.randomInizio() == 1){
            toccaA = 0;
        }else{
            toccaA = 1;
        }

        while (this.verificaVincitore()==null) {
            this.clearScreen();
            
            if(tabellaPiena()==true){
                adesso = LocalTime.now();
                scadenza = adesso.plusSeconds(10);

                do {
                    this.clearScreen();
                    System.out.println("""
                    +-------------------------------------+
                    |          PARTITA PAREGGIATA         |
                    |  RINIZIO AUTOMATICO TRA 10 SECONDI  |
                    +-------------------------------------+
                    
                    """);
                    /*Questa riga calcola il numero di secondi rimanenti tra l'orario attuale e il momento di scadenza. 
                    Utilizza il metodo until di LocalTime insieme all'enum ChronoUnit.SECONDS per ottenere la differenza
                    in secondi tra i due tempi.*/
                    long secondiRimanenti = scadenza.until(LocalTime.now(), ChronoUnit.SECONDS);
                    System.out.println("Inzio nuovo match tra " + Math.abs(secondiRimanenti-1) + " secondi...");
                    aspettaSecondi(1);
                } while (LocalTime.now().isBefore(scadenza));
                this.clearScreen();

                for (int i = 0; i < this.tabellaGioco.length; i++) {
                    for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                        tabellaGioco[i][j]=null;
                    }
                }
            }

            System.out.println("        +-------------------+");
            if(index<9){
                System.out.println("        |   MATCH \t"+(index+1)+"   |");
            }else{
                System.out.println("        |   MATCH \t"+(index+1)+"  |");
            }
            System.out.println("        +-------------------+");
            System.out.println("");

            this.stampaTabella();
            if(computer==false){
                if(toccaA==0){
                    System.out.println("È il turno di "+this.giocatori[0].nome+" ("+((this.giocatori[0].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+this.giocatori[0].colore+"\u001B[0m)");
                }else{
                    System.out.println("È il turno di "+this.giocatori[1].nome+" ("+((this.giocatori[1].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+this.giocatori[1].colore+"\u001B[0m)");
                }
                
                int mossa = this.validaMossa(input, toccaA, index);
                this.inserisciGettoneInTabella(mossa-1, this.giocatori[toccaA].colore);
            }else{
                if(toccaA==0){
                    System.out.println("È il turno di "+this.giocatori[0].nome+" ("+((this.giocatori[0].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+this.giocatori[0].colore+"\u001B[0m)");
                    int mossa = this.validaMossa(input, toccaA, index);
                    this.inserisciGettoneInTabella(mossa-1, this.giocatori[toccaA].colore);
                }else{
                    int mossa = this.validaMossaIA();
                    this.inserisciGettoneInTabella(mossa-1, this.giocatori[toccaA].colore);
                }
            }
            if(toccaA==0){
                toccaA=1;
            }else{
                toccaA=0;
            }
        }
        
        if(this.coordinateInizio()!=null){
            index1 = this.coordinateInizio()[0];
            index2 = this.coordinateInizio()[1];
        }
        if(metodoVerifica()!=null){
            metodoVerifica=metodoVerifica();
        }

        String nomeWinner="";
        for (int i = 0; i < this.giocatori.length; i++) {
            if(this.giocatori[i].colore.equals(verificaVincitore())){
                nomeWinner=this.giocatori[i].nome;
            }
        }
        adesso = LocalTime.now();
        scadenza = adesso.plusSeconds(10);

        do {
            this.clearScreen();
            
            System.out.println("""
                            +-------------------+
                            |  MATCH TERMINATO  |
                            +-------------------+
                    """);
            
            /*Questa riga calcola il numero di secondi rimanenti tra l'orario attuale e il momento di scadenza. 
            Utilizza il metodo until di LocalTime insieme all'enum ChronoUnit.SECONDS per ottenere la differenza
            in secondi tra i due tempi.*/
            long secondiRimanenti = scadenza.until(LocalTime.now(), ChronoUnit.SECONDS);
            if(secondiRimanenti%2==0)
                this.stampaTabella();
            else
                this.stampaTabellaVincitore(index1, index2, metodoVerifica);
            
            System.out.println(nomeWinner+" ("+((this.verificaVincitore() == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+this.verificaVincitore()+"\u001B[0m)"+" VINCE IL MATCH "+(index+1)+"\n");

            if(index+1 == numeroMatchTotale){
                System.out.println("Riepilogo tra " + Math.abs(secondiRimanenti-1) + " secondi...");
            }else{
                System.out.println("Prossimo match tra " + Math.abs(secondiRimanenti-1) + " secondi...");
            }
            aspettaSecondi(1);
        } while (LocalTime.now().isBefore(scadenza));
        this.clearScreen();
        return this.verificaVincitore();
    }

    void aspettaSecondi(int secondi) {
        LocalTime start = LocalTime.now();
        while (LocalTime.now().isBefore(start.plusSeconds(secondi))) {
        }
    }

    boolean tabellaPiena() {
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                if(this.tabellaGioco[i][j]==null){
                    return false;
                }
            }
        }
        return true;
    }

    int randomInizio(){
        return (int)(Math.random()*2);
    }

    void inserisciGettoneInTabella(int mossa, Colore colore){
        for (int i = this.tabellaGioco.length - 1; i >= 0; i--) {
            if(this.tabellaGioco[i][mossa] == null){
                this.tabellaGioco[i][mossa] = colore;
                break;
            }
        }
    }

    //  ho preferito usare questo metodo nonostante l'utilizzo di try catch, in quanto cancella completamente la console,
    //  mentre l'altro invece si sposta all'inizio della console e cancella da li in poi.
    void clearScreen() {
        try {
            // Verifica il sistema operativo
            if (System.getProperty("os.name").contains("Windows")) {
                // Se il sistema operativo è Windows, esegui il comando "cls" per pulire la console
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Altrimenti, per sistemi Unix/Linux, esegui il comando "clear" per pulire la console
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Gestisci eventuali eccezioni stampando la traccia dell'errore
            e.printStackTrace();
        }
    }
    
    /* 
    Questa funzione crea solo l'illusione dello schermo pulito.
    Fa uso di sequenze di escape ANSI '\033[H\033[2J', 
    utilizzata per muovere il cursore alla posizione in alto a sinistra dello schermo (\033[H)
    e poi cancellare l'intero schermo (\033[2J).

    void clearScreen() {
        // Carattere di escape ANSI per pulire la console
        System.out.print("\033[H\033[2J");
        System.out.flush();
    } */
    
    int validaMossaIA() {
        int mossa;
    
        // controllo che ci siano mosse vincenti
        mossa = this.mossaVincente();
        if (mossa != 0) {
            return mossa;
        }
    
        // controllo che ci siano mosse bloccanti
        mossa = this.mossaBloccante();
        if (mossa != 0) {
            System.out.println("mossa blocco");
            return mossa;
        }
        return this.mossaMigliore();
    }
    
    int mossaVincente() {
        //scorro le colonne
        for (int i = 0; i < this.tabellaGioco[0].length; i++) {
            //controllo solo se si possono ancora inserire gettoni nella colonna
            if (this.tabellaGioco[0][i] == null) {
                //inserisco il nuovo gettone dal basso verso l'alto
                for (int j = this.tabellaGioco.length - 1; j >= 0; j--) {
                    //se la cella è vuota provo a mettere il gettone del colore del computer;
                    if (this.tabellaGioco[j][i] == null) {
                        this.tabellaGioco[j][i] = this.giocatori[1].colore;
                        //se verificaVincitore ritorna il colore del computer, vuol dire che è la mossa vincente; 
                        //reimposto la cella a null e ritorno la mossa corretta;
                        if (this.verificaVincitore() == this.giocatori[1].colore) {
                            this.tabellaGioco[j][i] = null;
                            return i + 1;
                        }
                        //se non è la mossa vincente semplicemente reimposto la cella della tabella a null e ritorno 0;
                        this.tabellaGioco[j][i] = null;
                        break;
                    }
                }
            }
        }
        return 0;
    }
    
    int mossaBloccante() {
        for (int i = 0; i < this.tabellaGioco[0].length; i++) {
            //controllo solo se si possono ancora inserire gettoni nella colonna
            if (this.tabellaGioco[0][i] == null) {
                //inserisco il nuovo gettone dal basso verso l'alto
                for (int j = this.tabellaGioco.length - 1; j >= 0; j--) {
                    //se la cella è vuota provo a mettere il gettone del colore del giocatore;
                    if (this.tabellaGioco[j][i] == null) {
                        this.tabellaGioco[j][i] = this.giocatori[0].colore;
                        //se verificaVincitore ritorna il colore dell giocatore, vuol dire che è la mossa da bloccare; 
                        //reimposto la cella a null e ritorno la mossa bloccante;
                        if (this.verificaVincitore() == this.giocatori[0].colore) {
                            this.tabellaGioco[j][i] = null;
                            return i + 1;
                        }
                        //se non è la mossa vincente semplicemente reimposto la cella della tabella a null e ritorno 0;
                        this.tabellaGioco[j][i] = null;
                        break;
                    }
                }
            }
        }
        return 0;
    }
    
    // questo metodo cerca la mossa migliore prevedendo la mossa immediatamente successiva
    // Inizializza un array chiamato colonneNonOttimali per tenere traccia delle colonne che non sono considerate ottimali per la mossa.
    // Utilizza due cicli for annidati per esplorare tutte le colonne e le righe della tabellaGioco.
    // Per ogni colonna, cerca di inserire un gettone del computer e controlla se questa mossa impedisce all'avversario di vincere al turno successivo.
    //      -   Prova a inserire il gettone sopra, a destra e a sinistra del gettone del computer.
    //      -   Se l'avversario può vincere al turno successivo, segna la colonna come non ottimale e continua con la prossima colonna.
    // Se la colonna è considerata ottimale, controlla se il computer può vincere al turno successivo. Se sì, restituisce immediatamente la colonna.
    // Se non trova mosse ottimali, genera una mossa casuale evitando le colonne segnate come non ottimali.

    int mossaMigliore() {
        int[] colonneNonOttimali = new int[this.tabellaGioco[0].length];
        for (int i = 0; i < this.tabellaGioco[0].length; i++) {
            //controllo solo se si possono ancora inserire gettoni nella colonna
            if (this.tabellaGioco[0][i] == null) {
                //inserisco il nuovo gettone dal basso verso l'alto
                for (int j = this.tabellaGioco.length - 1; j >= 0; j--) {
                    boolean check = true;
                    //se la cella è vuota provo a mettere il gettone del colore del computer
                    if (this.tabellaGioco[j][i] == null) {
                        this.tabellaGioco[j][i] = giocatori[1].colore;
                        //controllo che l'avversario con una mossa successiva non faccia forza quattro

                        //provo a mettere nella posizione sopra il gettone dell'avversario
                        if(j > 0 && this.tabellaGioco[j-1][i] == null){
                            this.tabellaGioco[j-1][i] = giocatori[0].colore;
                            if(this.verificaVincitore()==giocatori[0].colore){
                                this.tabellaGioco[j-1][i] = null;
                                check=false;
                                for (int k = 0; k < colonneNonOttimali.length-1; k++) {
                                    if(colonneNonOttimali[k]==0){
                                        colonneNonOttimali[k]=i+1;
                                        System.out.println("c1 - utente forza 4 in ["+i+"]["+(j-1)+"], se mossa = ["+i+"]["+j+"]");
                                        break;
                                    }
                                }
                            }
                            this.tabellaGioco[j-1][i] = null;
                        }

                        //provo a mettere nella posizione a destra il gettone dell'avversario e controllo che funzioni solo se è il primo gettone di quella colonna o se ha qualcosa sotto
                        for (int k = this.tabellaGioco.length-1; k >= 0 ; k--) {
                            if(i < this.tabellaGioco[0].length - 2 && (k == this.tabellaGioco.length - 1 || (this.tabellaGioco[k+1][i+1] != null))  && this.tabellaGioco[k][i+1] == null){
                                this.tabellaGioco[k][i+1] = giocatori[0].colore;
                                if(this.verificaVincitore()==giocatori[0].colore){
                                    this.tabellaGioco[k][i+1] = null;
                                    check=false;
                                    for (int h = 0; h < colonneNonOttimali.length-1; h++) {
                                        if(colonneNonOttimali[h]==0){
                                            colonneNonOttimali[h]=i+1;
                                            System.out.println("c2 - utente forza 4 in ["+(i+1)+"]["+k+"], se mossa = ["+i+"]["+j+"]");
                                            break;
                                        }
                                    }
                                }
                                this.tabellaGioco[k][i+1] = null;
                            }
                        }

                        //provo a mettere nella posizione a sinistra il gettone dell'avversario e controllo che funzioni solo se è il primo gettone di quella colonna o se ha qualcosa sotto
                        for (int k = this.tabellaGioco.length-1; k >= 0 ; k--) {
                            if(i > 0 && (k == this.tabellaGioco.length - 1 || (this.tabellaGioco[k+1][i-1] != null))  && this.tabellaGioco[k][i-1] == null){
                                this.tabellaGioco[k][i-1] = giocatori[0].colore;
                                if(this.verificaVincitore()==giocatori[0].colore){
                                    this.tabellaGioco[k][i-1] = null;
                                    check=false;
                                    for (int h = 0; h < colonneNonOttimali.length-1; h++) {
                                        if(colonneNonOttimali[h]==0){
                                            colonneNonOttimali[h]=i+1;
                                            System.out.println("c3 - utente forza 4 in ["+(i-1)+"]["+k+"], se mossa = ["+i+"]["+j+"]");
                                            break;
                                        }
                                    }
                                }
                                this.tabellaGioco[k][i-1] = null;
                            }
                        }

                        if(check==true){
                            //se arrivo qua vuol dire che la colonna può andare bene perchè l'avversario non fa punto immediatamente dopo
                            //controllo che se alla mossa dopo il comuter mettesse la pedina sopra, vincerebbe lui
                            if(j > 0 && this.tabellaGioco[j-1][i] == null){
                                this.tabellaGioco[j-1][i] = giocatori[1].colore;
                                if(this.verificaVincitore()==giocatori[1].colore){
                                    this.tabellaGioco[j-1][i] = null;
                                    this.tabellaGioco[j][i] = null;
                                    System.out.println("c4 - forza 4 pc in ["+i+"]["+(j-1)+"], se mossa = ["+i+"]["+j+"]; mossa effettuata: "+i);
                                    return i+1;
                                }
                                this.tabellaGioco[j-1][i] = null;
                            }
                            //provo a mettere nella posizione a destra il gettone del computer e controllo che funzioni solo se è il primo gettone di quella colonna o se ha qualcosa sotto
                            for (int k = this.tabellaGioco.length-1; k >= 0 ; k--) {
                                if(i < this.tabellaGioco[0].length - 2 && (k == this.tabellaGioco.length - 1 || (this.tabellaGioco[k+1][i+1] != null))  && this.tabellaGioco[k][i+1] == null){
                                    this.tabellaGioco[k][i+1] = giocatori[1].colore;
                                    if(this.verificaVincitore()==giocatori[1].colore){
                                        this.tabellaGioco[k][i+1] = null;
                                        this.tabellaGioco[j][i] = null;
                                        System.out.println("c5 - forza 4 pc in ["+(i+1)+"]["+k+"], se mossa = ["+i+"]["+j+"]; mossa effettuata: "+i);
                                        return i+1;
                                    }
                                    this.tabellaGioco[k][i+1] = null;
                                }
                            }

                            //provo a mettere nella posizione a sinistra il gettone del computer e controllo che funzioni solo se è il primo gettone di quella colonna o se ha qualcosa sotto
                            for (int k = this.tabellaGioco.length-1; k >= 0 ; k--) {
                                if(i > 0 && (k == this.tabellaGioco.length - 1 || (this.tabellaGioco[k+1][i-1] != null))  && this.tabellaGioco[k][i-1] == null){
                                    this.tabellaGioco[k][i-1] = giocatori[1].colore;
                                    if(this.verificaVincitore()==giocatori[1].colore){
                                        this.tabellaGioco[k][i-1] = null;
                                        this.tabellaGioco[j][i] = null;
                                        System.out.println("c6 - forza 4 pc in ["+(i-1)+"]["+k+"], se mossa = ["+i+"]["+j+"]; mossa effettuata: "+i);
                                        return i+1;
                                    }
                                    this.tabellaGioco[k][i-1] = null;
                                }
                            }
                        }

                        this.tabellaGioco[j][i] = null;
                    }
                }
            }
        }
        //se arrivo qua vuol dire che non ha trovato una mossa ottimale; 
        //effettuo una mossa casuale che sia diversa però dalle colonne negate
        //se l'array è pieno, fai una mossa random e basta
        boolean arrayPieno=true;
        for (int k = 0; k < colonneNonOttimali.length-1; k++) {
            if(colonneNonOttimali[k] == 0){
                arrayPieno=false;
            }
        }
        
        int mossa = (int)(Math.random()*7+1);
        if(!arrayPieno){
            for (int k = 0; k < colonneNonOttimali.length-1; k++) {
                if(colonneNonOttimali[k]==mossa || this.tabellaGioco[0][mossa-1]!=null){
                    mossa = (int)(Math.random()*7+1);
                }
            }
        }
        System.out.println("mossa random "+mossa);
        return mossa;
    }

    int validaMossa(GestioneInput input, int turno, int index) {
        boolean check = false;
        int mossa = input.validaMossa();
        while(!check){
            if(this.tabellaGioco[0][mossa-1]!=null){
                this.clearScreen();
                this.stampaTabella();
                System.out.println("MATCH "+(index+1));
                if(turno==0){
                    System.out.println("È il turno di "+this.giocatori[0].nome+" ("+this.giocatori[0].colore+")");
                }else{
                    System.out.println("È il turno di "+this.giocatori[1].nome+" ("+this.giocatori[1].colore+")");
                }
                check=false;
                System.out.println("Colonna piena, Inserire un'altra colonna");
                mossa = input.validaMossa();
            }else{
                check=true;
            }
        }
        return mossa;
    }

    Colore verificaVincitore() {
        //logica per controllare che qualcuno abbia vinto
        if (this.verificaOrizzontale()!=null)
            return this.verificaOrizzontale();
        else if(this.verificaVerticale()!=null)
            return this.verificaVerticale();
        else if(this.verificaDiagonale()!=null)
            return this.verificaDiagonale();
        else if(this.verificaDiagonaleInversa()!=null)
            return this.verificaDiagonaleInversa();
        else
            return null;
    }

    Colore verificaOrizzontale(){
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            for (int j = 0; j < this.tabellaGioco[i].length-3; j++) {
                if(this.tabellaGioco[i][j]!=null){
                    if(this.tabellaGioco[i][j+1] !=null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j+1].name())){
                        if(this.tabellaGioco[i][j+2] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j+2].name())){
                            if(this.tabellaGioco[i][j+3] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j+3].name())){
                                return this.tabellaGioco[i][j];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    Colore verificaVerticale(){
        for (int i = 0; i < this.tabellaGioco[0].length; i++) {
            for (int j = 0; j < this.tabellaGioco.length-3; j++) {
                if(this.tabellaGioco[j][i]!=null){
                    if(this.tabellaGioco[j+1][i] !=null && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j+1][i].name())){
                        if(this.tabellaGioco[j+2][i] != null && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j+2][i].name())){
                            if(this.tabellaGioco[j+3][i] != null && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j+3][i].name())){
                                return this.tabellaGioco[j][i];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
    
    Colore verificaDiagonale(){
        for (int i = 0; i < this.tabellaGioco.length-3; i++) {
            for (int j = 0; j < this.tabellaGioco[0].length-3; j++) {
                if(this.tabellaGioco[i][j]!=null){
                    if(this.tabellaGioco[i+1][j+1] !=null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i+1][j+1].name())){
                        if(this.tabellaGioco[i+2][j+2] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i+2][j+2].name())){
                            if(this.tabellaGioco[i+3][j+3] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i+3][j+3].name())){
                                return this.tabellaGioco[i][j];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    Colore verificaDiagonaleInversa() {
        for (int i = 0; i < this.tabellaGioco.length - 3; i++) {
            for (int j = this.tabellaGioco[0].length - 1; j >= this.tabellaGioco[0].length - 4; j--) {
                if (this.tabellaGioco[i][j] != null) {
                    if (this.tabellaGioco[i + 1][j - 1] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 1][j - 1].name())) {
                        if (this.tabellaGioco[i + 2][j - 2] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 2][j - 2].name())) {
                            if (this.tabellaGioco[i + 3][j - 3] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 3][j - 3].name())) {
                                return this.tabellaGioco[i][j];
                            }
                        }
                    }
                }
            }
        }
        return null;
    }    

    int[] coordinateOrizzonale(){
        int[] arrayCoordinate = new int[2];
        if(this.verificaOrizzontale()!=null){
            for (int i = 0; i < this.tabellaGioco.length; i++) {
                for (int j = 0; j < this.tabellaGioco[i].length-3; j++) {
                    if(this.tabellaGioco[i][j]!=null){
                        if(this.tabellaGioco[i][j+1] !=null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j+1].name())){
                            if(this.tabellaGioco[i][j+2] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j+2].name())){
                                if(this.tabellaGioco[i][j+3] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j+3].name())){
                                    arrayCoordinate[0]=i;
                                    arrayCoordinate[1]=j;
                                    return arrayCoordinate;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    int[] coordinateVerticale(){
        int[] arrayCoordinate = new int[2];
        if(this.verificaVerticale()!=null){
            for (int i = 0; i < this.tabellaGioco[0].length; i++) {
                for (int j = 0; j < this.tabellaGioco.length-3; j++) {
                    if(this.tabellaGioco[j][i]!=null){
                        if(this.tabellaGioco[j+1][i] !=null && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j+1][i].name())){
                            if(this.tabellaGioco[j+2][i] != null && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j+2][i].name())){
                                if(this.tabellaGioco[j+3][i] != null && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j+3][i].name())){
                                    arrayCoordinate[0]=j;
                                    arrayCoordinate[1]=i;
                                    return arrayCoordinate;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    int[] coordinateDiagonale(){
        int[] arrayCoordinate = new int[2];
        if(this.verificaDiagonale()!=null){
            for (int i = 0; i < this.tabellaGioco.length-3; i++) {
                for (int j = 0; j < this.tabellaGioco[0].length-3; j++) {
                    if(this.tabellaGioco[i][j]!=null){
                        if(this.tabellaGioco[i+1][j+1] !=null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i+1][j+1].name())){
                            if(this.tabellaGioco[i+2][j+2] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i+2][j+2].name())){
                                if(this.tabellaGioco[i+3][j+3] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i+3][j+3].name())){
                                    arrayCoordinate[0]=i;
                                    arrayCoordinate[1]=j;
                                    return arrayCoordinate;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    int[] coordinateDiagonaleInversa(){
        int[] arrayCoordinate = new int[2];
        if(this.verificaDiagonaleInversa()!=null){
            for (int i = 0; i < this.tabellaGioco.length - 3; i++) {
                for (int j = this.tabellaGioco[0].length - 1; j >= this.tabellaGioco[0].length - 4; j--) {
                    if (this.tabellaGioco[i][j] != null) {
                        if (this.tabellaGioco[i + 1][j - 1] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 1][j - 1].name())) {
                            if (this.tabellaGioco[i + 2][j - 2] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 2][j - 2].name())) {
                                if (this.tabellaGioco[i + 3][j - 3] != null && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 3][j - 3].name())) {
                                    arrayCoordinate[0]=i;
                                    arrayCoordinate[1]=j;
                                    return arrayCoordinate;
                                }
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    int[] coordinateInizio(){
        if(this.coordinateOrizzonale()!=null){
            return this.coordinateOrizzonale();
        }else if(this.coordinateVerticale()!=null){
            return this.coordinateVerticale();
        }else if(this.coordinateDiagonale()!=null){
            return this.coordinateDiagonale();
        }else if(this.coordinateDiagonaleInversa()!=null){
            return this.coordinateDiagonaleInversa();
        }else{
            return null;
        }
    }

    String metodoVerifica(){
        if(this.coordinateOrizzonale()!=null){
            return "orizzontale";
        }else if(this.coordinateVerticale()!=null){
            return "verticale";
        }else if(this.coordinateDiagonale()!=null){
            return "diagonale";
        }else if(this.coordinateDiagonaleInversa()!=null){
            return "diagonaleInversa";
        }else{
            return null;
        }
    }

    void stampaTabella(){
        System.out.println("   1    2    3    4    5    6    7");
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            System.out.println("+----+----+----+----+----+----+----+");
            System.out.print("|");
            for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                
                if(this.tabellaGioco[i][j]==null){
                    System.out.print(" "+"   |");
                }else if(this.tabellaGioco[i][j].equals(Colore.GIALLO)){
                    System.out.print(" 🟡"+" |");
                }else{
                    System.out.print(" 🔴"+" |");
                }
            }
            System.out.println("");
        }
        System.out.println("+----+----+----+----+----+----+----+");
    }
    
    //metodo che stampa la tabella e colora la riga vinta di viola
    void stampaTabellaVincitore(int index1, int index2, String metodoVerifica){
        
        System.out.println("   1    2    3    4    5    6    7");
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            System.out.println("+----+----+----+----+----+----+----+");
            System.out.print("|");
            for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                if(metodoVerifica=="orizzontale"){
                    if(this.tabellaGioco[i][j]==null){
                        System.out.print(" "+"   |");
                    }else if(i==index1 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1 && j==index2+1 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1 && j==index2+2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1 && j==index2+3 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(this.tabellaGioco[i][j].equals(Colore.GIALLO)){
                        System.out.print(" 🟡"+" |");
                    }else{
                        System.out.print(" 🔴"+" |");
                    }
                }else if (metodoVerifica=="verticale") {
                    if(this.tabellaGioco[i][j]==null){
                        System.out.print(" "+"   |");
                    }else if(i==index1 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+1 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+2 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+3 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(this.tabellaGioco[i][j].equals(Colore.GIALLO)){
                        System.out.print(" 🟡"+" |");
                    }else{
                        System.out.print(" 🔴"+" |");
                    }
                }else if(metodoVerifica=="diagonale"){
                    if(this.tabellaGioco[i][j]==null){
                        System.out.print(" "+"   |");
                    }else if(i==index1 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+1 && j==index2+1 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+2 && j==index2+2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+3 && j==index2+3 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(this.tabellaGioco[i][j].equals(Colore.GIALLO)){
                        System.out.print(" 🟡"+" |");
                    }else{
                        System.out.print(" 🔴"+" |");
                    }
                }else if(metodoVerifica=="diagonaleInversa"){
                    if(this.tabellaGioco[i][j]==null){
                        System.out.print(" "+"   |");
                    }else if(i==index1 && j==index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+1 && j==index2-1 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+2 && j==index2-2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(i==index1+3 && j==index2-3 && this.tabellaGioco[i][j].equals(this.verificaVincitore())){
                        System.out.print(" 🟣"+" |");
                    }else if(this.tabellaGioco[i][j].equals(Colore.GIALLO)){
                        System.out.print(" 🟡"+" |");
                    }else{
                        System.out.print(" 🔴"+" |");
                    }
                }
            }
            
            System.out.println("");
        }
        System.out.println("+----+----+----+----+----+----+----+");
    }
    
}