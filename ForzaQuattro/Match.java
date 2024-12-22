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
    
    Colore gioco(GestioneInput input, int index, int numeroMatchTotale){
        int index1=0, index2=0;
        String metodoVerifica="";

        int toccaA=0;
        if(this.randomInizio() == 1){
            toccaA = 0;
        }else{
            toccaA = 1;
        }

        while (this.verificaVincitore() == null) {
            this.clearScreen();
            
            if(tabellaPiena() == true){
                
                    this.clearScreen();
                    System.out.println("PARTITA PAREGGIATA, RIFARE");
                   
                
                //in caso di pareggio resettiamo la tabella
                for (int i = 0; i < this.tabellaGioco.length; i++) {
                    for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                        tabellaGioco[i][j] = null;
                    }
                }
            }
            if(index<9){
                System.out.println("MATCH \t"+(index+1));
            }else{
                System.out.println("MATCH \t"+(index+1));
            }
            this.stampaTabella();
            
            if(toccaA == 0){
                System.out.println("È il turno di "+this.giocatori[0].nome+" ("+((this.giocatori[0].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+this.giocatori[0].colore+"\u001B[0m)");
            }else{
                System.out.println("È il turno di "+this.giocatori[1].nome+" ("+((this.giocatori[1].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m")+this.giocatori[1].colore+"\u001B[0m)");
            }
            
            int mossa = this.validaMossa(input, toccaA, index);
            this.inserisciGettoneInTabella(mossa-1, this.giocatori[toccaA].colore);
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


        
            this.clearScreen();
            
            System.out.println("MATCH TERMINATO");
            
            this.stampaTabellaVincitore(index1, index2, metodoVerifica);
            
            System.out.println(nomeWinner+" ("+this.verificaVincitore()+")"+" VINCE IL MATCH "+(index+1)+"\n");
    
        return this.verificaVincitore();
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
//TODO far si che la griglia non venga alterata, magari cambiarla del tutto
// Metodo per stampare la tabella del gioco
void stampaTabella() {
    System.out.println("   1    2    3    4    5    6    7");

    // Itera attraverso le righe della tabella
    for (int i = 0; i < this.tabellaGioco.length; i++) {
        System.out.println("+----+----+----+----+----+----+----+");
        System.out.print("|");
        
        // Itera attraverso le colonne della tabella
        for (int j = 0; j < this.tabellaGioco[0].length; j++) {

            // Se la cella è vuota
            if (this.tabellaGioco[i][j] == null) {
                System.out.print("    |"); // Stampa vuoto
            } else {
                // Recupera il colore associato a questa cella
                Colore colore = this.tabellaGioco[i][j];
                char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo; // Associa il simbolo al colore del giocatore

                // Usa codici di escape ANSI per colorare il testo (rosso o giallo)
                String coloreSimbolo = (colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m"; // Giallo o Rosso

                // Stampa il simbolo con il colore scelto
                System.out.print(coloreSimbolo + "  " + simbolo + "  \u001B[0m|"); // Resetta il colore dopo ogni simbolo
            }
        }

        System.out.println("");
    }
    System.out.println("+----+----+----+----+----+----+----+");
}
    //TODO non cambiare i colori, solo i 4 simboli vincenti e non stampare la scritta del colore, correggi anche qui la griglia
// Metodo che stampa la tabella e colora la riga vincente di viola
void stampaTabellaVincitore(int index1, int index2, String metodoVerifica) {
    System.out.println("   1    2    3    4    5    6    7");
    
    // Itera attraverso le righe della tabella
    for (int i = 0; i < this.tabellaGioco.length; i++) {
        System.out.println("+----+----+----+----+----+----+----+");
        System.out.print("|");
        
        // Itera attraverso le colonne della tabella
        for (int j = 0; j < this.tabellaGioco[0].length; j++) {

            // Gestione del caso orizzontale
            if (metodoVerifica.equals("orizzontale")) {
                if (this.tabellaGioco[i][j] == null) {
                    System.out.print("    |"); // Cella vuota
                } else if (i == index1 && j >= index2 && j < index2 + 4 && this.tabellaGioco[i][j].equals(this.verificaVincitore())) {
                    // Cella vincente, colorazione viola
                    System.out.print(" \033[0;35m" + this.tabellaGioco[i][j] + "\033[0m |"); // Viola
                } else if (this.tabellaGioco[i][j].equals(Colore.GIALLO)) {
                    // Colore giallo, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[0].simbolo + " |");
                } else if (this.tabellaGioco[i][j].equals(Colore.ROSSO)) {
                    // Colore rosso, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[1].simbolo + " |");
                }
            }
            // Gestione del caso verticale
            else if (metodoVerifica.equals("verticale")) {
                if (this.tabellaGioco[i][j] == null) {
                    System.out.print("    |"); // Cella vuota
                } else if (j == index2 && i >= index1 && i < index1 + 4 && this.tabellaGioco[i][j].equals(this.verificaVincitore())) {
                    // Cella vincente, colorazione viola
                    System.out.print(" \033[0;35m" + this.tabellaGioco[i][j] + "\033[0m |"); // Viola
                } else if (this.tabellaGioco[i][j].equals(Colore.GIALLO)) {
                    // Colore giallo, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[0].simbolo + " |");
                } else if (this.tabellaGioco[i][j].equals(Colore.ROSSO)) {
                    // Colore rosso, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[1].simbolo + " |");
                }
            }
            // Gestione del caso diagonale
            else if (metodoVerifica.equals("diagonale")) {
                if (this.tabellaGioco[i][j] == null) {
                    System.out.print("    |"); // Cella vuota
                } else if (i == index1 && j == index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())) {
                    // Cella vincente, colorazione viola
                    System.out.print(" \033[0;35m" + this.tabellaGioco[i][j] + "\033[0m |"); // Viola
                } else if (this.tabellaGioco[i][j].equals(Colore.GIALLO)) {
                    // Colore giallo, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[0].simbolo + " |");
                } else if (this.tabellaGioco[i][j].equals(Colore.ROSSO)) {
                    // Colore rosso, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[1].simbolo + " |");
                }
            }
            // Gestione del caso diagonale inversa
            else if (metodoVerifica.equals("diagonaleInversa")) {
                if (this.tabellaGioco[i][j] == null) {
                    System.out.print("    |"); // Cella vuota
                } else if (i == index1 && j == index2 && this.tabellaGioco[i][j].equals(this.verificaVincitore())) {
                    // Cella vincente, colorazione viola
                    System.out.print(" \033[0;35m" + this.tabellaGioco[i][j] + "\033[0m |"); // Viola
                } else if (this.tabellaGioco[i][j].equals(Colore.GIALLO)) {
                    // Colore giallo, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[0].simbolo + " |");
                } else if (this.tabellaGioco[i][j].equals(Colore.ROSSO)) {
                    // Colore rosso, simbolo scelto dal giocatore
                    System.out.print(" " + giocatori[1].simbolo + " |");
                }
            }
        }
        
        System.out.println("");
    }
    System.out.println("+----+----+----+----+----+----+----+");
}
    
}