// Classe che si occupa di gestire il match tra due giocatori seguendo le regole.
public class Match {
    Giocatore[] giocatori; // Array di due giocatori che partecipano al match.
    Colore[][] tabellaGioco; // Matrice che rappresenta il tabellone di gioco (6 righe, 7 colonne).

    Match(Giocatore[] giocatori) { // Costruttore che inizializza i giocatori e il tabellone vuoto.
        this.giocatori = new Giocatore[2];
        this.tabellaGioco = new Colore[6][7];
        this.giocatori[0] = giocatori[0];
        this.giocatori[1] = giocatori[1];
    }

    // Metodo principale che gestisce l'intero svolgimento della partita.
    Colore gioco(GestioneInput input, int index, int numeroMatchTotale) {
        int index1 = 0, index2 = 0; // Coordinate iniziali della sequenza vincente.
        String metodoVerifica = "";  // Metodo di verifica della vittoria.

        int toccaA = 0;
        // Determina casualmente chi inizia il turno.
        if (this.randomInizio() == 1) {
            toccaA = 0;
        } else {
            toccaA = 1;
        }

        // Ciclo iterativo principale fino a quando non si trova un vincitore o si verifica un pareggio.
        while (this.verificaVincitore() == null) {
            this.clearScreen(); // Pulisce lo schermo.

             // Controlla se la tabella è piena (pareggio).
            if (tabellaPiena() == true) {

                this.clearScreen();
                System.out.println("PARTITA PAREGGIATA, RIFARE");

                // Reset della tabella in caso di pareggio.
                for (int i = 0; i < this.tabellaGioco.length; i++) {
                    for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                        tabellaGioco[i][j] = null;
                    }
                }
            }

            // Stampa il numero del match.
            if (index < 9) {
                System.out.println("MATCH \t" + (index + 1));
            } else {
                System.out.println("MATCH \t" + (index + 1));
            }
            this.stampaTabella(); // Stampa il tabellone di gioco.

            // Mostra il turno del giocatore corrente.
            if (toccaA == 1) {
                System.out.println("È il turno di " + this.giocatori[0].nome + " (" +
                        ((this.giocatori[0].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m") +
                        this.giocatori[0].simbolo + "\u001B[0m)"); // Mostra il simbolo scelto dal giocatore
            } else {
                System.out.println("È il turno di " + this.giocatori[1].nome + " (" +
                        ((this.giocatori[1].colore == Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m") +
                        this.giocatori[1].simbolo + "\u001B[0m)"); // Mostra il simbolo scelto dal giocatore
            }

            // Valida la mossa effettuata dal giocatore.
            int mossa = this.validaMossa(input, toccaA, index);
            // Inserisce il simbolo nella colonna scelta.
            this.inserisciCharInTabella(mossa - 1, this.giocatori[toccaA].colore);

            // Cambia turno.
            if (toccaA == 0) {
                toccaA = 1;
            } else {
                toccaA = 0;
            }
        }

        // Recupera le coordinate di inizio e il metodo di verifica della vittoria.
        if (this.coordinateInizio() != null) {
            index1 = this.coordinateInizio()[0];
            index2 = this.coordinateInizio()[1];
        }
        if (metodoVerifica() != null) {
            metodoVerifica = metodoVerifica();
        }

        // Determina il nome del vincitore.
        String nomeWinner = "";
        for (int i = 0; i < (this.giocatori.length); i++) {
            if (this.giocatori[i].colore.equals(verificaVincitore())) {
                if (this.giocatori[i].nome == this.giocatori[0].nome) {
                    nomeWinner = this.giocatori[1].nome;
                } else {
                    nomeWinner = this.giocatori[0].nome;
                }
            }
        }

        this.clearScreen(); // Pulisce lo schermo.

        System.out.println("MATCH TERMINATO");

        // Stampa il tabellone con la riga vincente evidenziata.
        this.stampaTabellaVincitore(index1, index2, metodoVerifica); 

        System.out.println(nomeWinner + " (" +
                ((this.verificaVincitore() != Colore.GIALLO) ? "\u001B[33m" : "\u001B[31m") +
                this.giocatori[1].simbolo + "\u001B[0m) VINCE IL MATCH " + (index + 1) + "\n");

        return this.verificaVincitore(); // Restituisce il colore del vincitore.
    }

    // Controllo per vedere se la tabella è piena.
    boolean tabellaPiena() {
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                if (this.tabellaGioco[i][j] == null) {
                    return false;
                }
            }
        }
        return true;
    }

    // Determina casualmente il giocatore che inizia il turno.
    int randomInizio() {
        GestioneInput gestioneInput = new GestioneInput(); // Crea un'istanza di GestioneInput.
        if (gestioneInput.continua()) {
            return (int) (Math.random() * 2); // Genera un numero casuale (0 o 1).
        }
        return -1; // Valore di default in caso di errore (non necessario, ma indicativo)
    }

    // Metodo che inserisce il simbolo del giocatore nella colonna selezionata dall'utente.
    void inserisciCharInTabella(int mossa, Colore colore) {
        for (int i = this.tabellaGioco.length - 1; i >= 0; i--) {
            if (this.tabellaGioco[i][mossa] == null) {
                this.tabellaGioco[i][mossa] = colore;
                break;
            }
        }
    }

    // Metodo per pulire la console.
    void clearScreen() {
        try {
            // Verifica il sistema operativo.
            if (System.getProperty("os.name").contains("Windows")) {
                // Se il sistema operativo è Windows, esegui il comando "cls" per pulire la console.
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // Altrimenti, per sistemi Unix/Linux, esegui il comando "clear" per pulire la console.
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            // Gestisci eventuali eccezioni stampando la traccia dell'errore.
            e.printStackTrace();
        }
    }

    // Controlla la validità della mossa selezionata.
    int validaMossa(GestioneInput input, int turno, int index) {
        boolean check = false;
        int mossa = input.validaMossa();
        while (!check) {
            if (this.tabellaGioco[0][mossa - 1] != null) {
                this.clearScreen();
                this.stampaTabella();
                System.out.println("MATCH " + (index + 1));
                if (turno == 0) {
                    System.out
                            .println("È il turno di " + this.giocatori[0].nome + " (" + this.giocatori[0].colore + ")");
                } else {
                    System.out
                            .println("È il turno di " + this.giocatori[1].nome + " (" + this.giocatori[1].colore + ")");
                }
                check = false;
                System.out.println("Colonna piena, Inserire un'altra colonna");
                mossa = input.validaMossa();
            } else {
                check = true;
            }
        }
        return mossa;
    }

    // Controlla se c'è un vincitore nella partita.
    Colore verificaVincitore() {
        if (this.verificaOrizzontale() != null)
            return this.verificaOrizzontale();
        else if (this.verificaVerticale() != null)
            return this.verificaVerticale();
        else if (this.verificaDiagonale() != null)
            return this.verificaDiagonale();
        else if (this.verificaDiagonaleInversa() != null)
            return this.verificaDiagonaleInversa();
        else
            return null;
    }

    // Metodo per verificare se c'è una combinazione vincente orizzontale.
    Colore verificaOrizzontale() {
        for (int i = 0; i < this.tabellaGioco.length; i++) { // Ciclo for che itera su tutte le righe.
            for (int j = 0; j < this.tabellaGioco[i].length - 3; j++) { // Ciclo for che itera su tutte le colonne, fino a (lunghezza - 3) per evitare un errore di out of bounds.
                if (this.tabellaGioco[i][j] != null) { // Controlla se la cella corrente non è vuota.
                     // Verifica che le quattro celle consecutive siano uguali.
                    if (this.tabellaGioco[i][j + 1] != null
                            && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j + 1].name())) {
                        if (this.tabellaGioco[i][j + 2] != null
                                && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j + 2].name())) {
                            if (this.tabellaGioco[i][j + 3] != null
                                    && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j + 3].name())) {
                                return this.tabellaGioco[i][j]; // Restituisce il colore della cella vincente.
                            }
                        }
                    }
                }
            }
        }
        return null; // Nessuna combinazione trovata.
    }
    
    // Metodo per verificare se c'è una combinazione vincente verticale.
    Colore verificaVerticale() {
        for (int i = 0; i < this.tabellaGioco[0].length; i++) { // Ciclo for che itera su tutte le colonne.
            for (int j = 0; j < this.tabellaGioco.length - 3; j++) {  // Ciclo for che itera su tutte le righe, fino a (altezza - 3) per evitare un errore di out of bounds.
                if (this.tabellaGioco[j][i] != null) { // Controlla se la cella corrente non è vuota.
                    // Verifica che le quattro celle consecutive siano uguali.
                    if (this.tabellaGioco[j + 1][i] != null
                            && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j + 1][i].name())) {
                        if (this.tabellaGioco[j + 2][i] != null
                                && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j + 2][i].name())) {
                            if (this.tabellaGioco[j + 3][i] != null
                                    && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j + 3][i].name())) {
                                return this.tabellaGioco[j][i]; // Restituisce il colore della cella vincente.
                            }
                        }
                    }
                }
            }
        }
        return null; // Nessuna combinazione trovata.
    }

    // Metodo per verificare se c'è una combinazione vincente in diagonale.
    Colore verificaDiagonale() {
        for (int i = 0; i < this.tabellaGioco.length - 3; i++) { // Ciclo for che itera sulle righe, fino a (altezza - 3).
            for (int j = 0; j < this.tabellaGioco[0].length - 3; j++) { // Ciclo for che itera sulle colonne, fino a (lunghezza - 3).
                if (this.tabellaGioco[i][j] != null) {
                    // Verifica che le celle in diagonale siano uguali.
                    if (this.tabellaGioco[i + 1][j + 1] != null
                            && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 1][j + 1].name())) {
                        if (this.tabellaGioco[i + 2][j + 2] != null
                                && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 2][j + 2].name())) {
                            if (this.tabellaGioco[i + 3][j + 3] != null
                                    && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 3][j + 3].name())) {
                                return this.tabellaGioco[i][j]; // Restituisce il colore della cella vincente.
                            }
                        }
                    }
                }
            }
        }
        return null; // Nessuna combinazione trovata.
    }

    // Metodo per verificare se c'è una combinazione vincente in diagonale inversa.
    Colore verificaDiagonaleInversa() {
        for (int i = 0; i < this.tabellaGioco.length - 3; i++) { // Ciclo for che itera sulle righe, fino a (altezza - 3).
            for (int j = this.tabellaGioco[0].length - 1; j >= this.tabellaGioco[0].length - 4; j--) { // Ciclo for che itera sulle colonne partendo dall'ultima, andando indietro.
                if (this.tabellaGioco[i][j] != null) {
                    if (this.tabellaGioco[i + 1][j - 1] != null // Verifica che le celle in diagonale inversa siano uguali.
                            && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 1][j - 1].name())) {
                        if (this.tabellaGioco[i + 2][j - 2] != null
                                && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 2][j - 2].name())) {
                            if (this.tabellaGioco[i + 3][j - 3] != null
                                    && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 3][j - 3].name())) {
                                return this.tabellaGioco[i][j]; // Restituisce il colore della cella vincente.
                            }
                        }
                    }
                }
            }
        }
        return null; // Nessuna combinazione trovata.
    }

    // Metodo che restituisce le coordinate iniziali di una sequenza vincente orizzontale (se esiste).
    int[] coordinateOrizzonale() {
        int[] arrayCoordinate = new int[2]; // Array per memorizzare le coordinate iniziali.
        if (this.verificaOrizzontale() != null) { // Verifica se esiste una sequenza vincente orizzontale.
            for (int i = 0; i < this.tabellaGioco.length; i++) { // Ciclo for che fa scorrere le righe della tabella.
                for (int j = 0; j < this.tabellaGioco[i].length - 3; j++) { // Scorre le colonne fino a un massimo di 3 celle dalla fine.
                    if (this.tabellaGioco[i][j] != null) { // Controlla se la cella corrente non è vuota.
                        if (this.tabellaGioco[i][j + 1] != null
                                && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j + 1].name())) {
                            if (this.tabellaGioco[i][j + 2] != null
                                    && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j + 2].name())) {
                                if (this.tabellaGioco[i][j + 3] != null
                                        && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i][j + 3].name())) {
                                    arrayCoordinate[0] = i; // Salva la riga della sequenza.
                                    arrayCoordinate[1] = j; // Salva la colonna iniziale della sequenza.
                                    return arrayCoordinate; // Restituisce le coordinate.
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // Restituisce null se non esiste una sequenza vincente orizzontale.
    }

    // Metodo che restituisce le coordinate iniziali di una sequenza vincente verticale (se esiste).
    int[] coordinateVerticale() {
        int[] arrayCoordinate = new int[2]; // Array per memorizzare le coordinate iniziali.
        if (this.verificaVerticale() != null) { // Verifica se esiste una sequenza vincente verticale.
            for (int i = 0; i < this.tabellaGioco[0].length; i++) { // Ciclo for che fa scorrere le colonne della tabella.
                for (int j = 0; j < this.tabellaGioco.length - 3; j++) { // Scorre le righe fino a un massimo di 3 celle dalla fine.
                    if (this.tabellaGioco[j][i] != null) { // Controlla se la cella corrente non è vuota.
                        if (this.tabellaGioco[j + 1][i] != null
                                && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j + 1][i].name())) {
                            if (this.tabellaGioco[j + 2][i] != null
                                    && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j + 2][i].name())) {
                                if (this.tabellaGioco[j + 3][i] != null
                                        && this.tabellaGioco[j][i].name().equals(this.tabellaGioco[j + 3][i].name())) {
                                    arrayCoordinate[0] = j; // Salva la riga iniziale della sequenza
                                    arrayCoordinate[1] = i; // Salva la colonna della sequenza
                                    return arrayCoordinate; // Restituisce le coordinate.
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // Restituisce null se non esiste una sequenza vincente verticale.
    }

    // Metodo che restituisce le coordinate iniziali di una sequenza vincente diagonale (da sinistra a destra), se esiste.
    int[] coordinateDiagonale() {
        int[] arrayCoordinate = new int[2]; // Array per memorizzare le coordinate iniziali.
        if (this.verificaDiagonale() != null) { // Verifica se esiste una sequenza vincente diagonale.
            for (int i = 0; i < this.tabellaGioco.length - 3; i++) { // Ciclo for che fa scorrere le righe fino a un massimo di 3 celle dalla fine.
                for (int j = 0; j < this.tabellaGioco[0].length - 3; j++) { // Scorre le colonne fino a un massimo di 3 celle dalla fine.
                    if (this.tabellaGioco[i][j] != null) { // Controlla se la cella corrente non è vuota.
                        if (this.tabellaGioco[i + 1][j + 1] != null
                                && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 1][j + 1].name())) {
                            if (this.tabellaGioco[i + 2][j + 2] != null
                                    && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 2][j + 2].name())) {
                                if (this.tabellaGioco[i + 3][j + 3] != null && this.tabellaGioco[i][j].name()
                                        .equals(this.tabellaGioco[i + 3][j + 3].name())) {
                                    arrayCoordinate[0] = i; // Salva la riga iniziale della sequenza.
                                    arrayCoordinate[1] = j; // Salva la colonna iniziale della sequenza.
                                    return arrayCoordinate; // Restituisce le coordinate.
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // Restituisce null se non esiste una sequenza vincente diagonale.
    }

    // Metodo che restituisce le coordinate iniziali di una sequenza vincente diagonale inversa (da destra a sinistra), se esiste.
    int[] coordinateDiagonaleInversa() {
        int[] arrayCoordinate = new int[2]; // Array per memorizzare le coordinate iniziali.
        if (this.verificaDiagonaleInversa() != null) { // Verifica se esiste una sequenza vincente diagonale inversa.
            for (int i = 0; i < this.tabellaGioco.length - 3; i++) { // Ciclo for che fa scorrere le righe fino a un massimo di 3 celle dalla fine.
                for (int j = this.tabellaGioco[0].length - 1; j >= this.tabellaGioco[0].length - 4; j--) { // Scorre le colonne all'indietro.
                    if (this.tabellaGioco[i][j] != null) { // Controlla se la cella corrente non è vuota.
                        if (this.tabellaGioco[i + 1][j - 1] != null
                                && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 1][j - 1].name())) {
                            if (this.tabellaGioco[i + 2][j - 2] != null
                                    && this.tabellaGioco[i][j].name().equals(this.tabellaGioco[i + 2][j - 2].name())) {
                                if (this.tabellaGioco[i + 3][j - 3] != null && this.tabellaGioco[i][j].name()
                                        .equals(this.tabellaGioco[i + 3][j - 3].name())) {
                                    arrayCoordinate[0] = i; // Salva la riga iniziale della sequenza.
                                    arrayCoordinate[1] = j; // Salva la colonna iniziale della sequenza.
                                    return arrayCoordinate; // Restituisce le coordinate.
                                }
                            }
                        }
                    }
                }
            }
        }
        return null; // Restituisce null se non esiste una sequenza vincente diagonale inversa.
    }

    // Metodo che restituisce le coordinate iniziali della prima sequenza vincente trovata.
    int[] coordinateInizio() {
        if (this.coordinateOrizzonale() != null) {
            return this.coordinateOrizzonale(); // Restituisce le coordinate della sequenza orizzontale.
        } else if (this.coordinateVerticale() != null) {
            return this.coordinateVerticale(); // Restituisce le coordinate della sequenza verticale.
        } else if (this.coordinateDiagonale() != null) {
            return this.coordinateDiagonale(); // Restituisce le coordinate della sequenza diagonale.
        } else if (this.coordinateDiagonaleInversa() != null) {
            return this.coordinateDiagonaleInversa(); // Restituisce le coordinate della sequenza diagonale inversa.
        } else {
            return null; // Restituisce null se non esistono sequenze vincenti.
        }
    }

    // Metodo che restituisce il tipo di sequenza vincente trovata (orizzontale, verticale, diagonale o diagonale inversa).
    String metodoVerifica() {
        if (this.coordinateOrizzonale() != null) {
            return "orizzontale"; // Sequenza orizzontale.
        } else if (this.coordinateVerticale() != null) {
            return "verticale"; // Sequenza verticale.
        } else if (this.coordinateDiagonale() != null) {
            return "diagonale"; // Sequenza diagonale.
        } else if (this.coordinateDiagonaleInversa() != null) {
            return "diagonaleInversa"; // Sequenza diagonale inversa.
        } else {
            return null; // Nessuna sequenza trovata.
        }
    }

    // Metodo per stampare lo stato attuale della tabella di gioco.
    void stampaTabella() {
        System.out.println("   1    2    3    4    5    6    7");
        // Ciclo for che itera attraverso  tutte le righe della tabella.
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            System.out.println("+----+----+----+----+----+----+----+");
            System.out.print("|");
            // Ciclo for che itera attraverso tutte le colonne della tabella.
            for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                // Stampa il valore della cella, o uno spazio vuoto se la cella è null.
                if (this.tabellaGioco[i][j] == null) {
                    System.out.print("    |"); 
                } else {
                    // Recupera il colore associato a questa cella.
                    Colore colore = this.tabellaGioco[i][j];
                    // Associa il simbolo al colore del giocatore.
                    char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo; 
                    // Usa codici di escape ANSI per colorare il testo (rosso o giallo).
                    String coloreSimbolo = (colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m"; // Giallo o Rosso.

                    // Stampa il simbolo con il colore scelto, e aggiungi uno spazio per garantire l'allineamento.
                    System.out.print(coloreSimbolo + " " + simbolo + "  \u001B[0m|"); // Assicura la larghezza fissa per il simobolo.
                }
            }

            System.out.println("");
        }
        System.out.println("+----+----+----+----+----+----+----+");

    }

    // Metodo che stampa la tabella e colora la riga vincente di viola.
    void stampaTabellaVincitore(int index1, int index2, String metodoVerifica) {
        // Stampa l'intestazione della tabella.
        System.out.println("   1    2    3    4    5    6    7");
        // Ciclo for che itera attraverso le righe della tabella.
        for (int i = 0; i < this.tabellaGioco.length; i++) {
            System.out.println("+----+----+----+----+----+----+----+");
            System.out.print("|");

            // Ciclo for che itera attraverso le colonne della tabella.
            for (int j = 0; j < this.tabellaGioco[0].length; j++) {
                // Gestione del caso orizzontale.
                if (metodoVerifica.equals("orizzontale")) {
                    if (this.tabellaGioco[i][j] == null) {
                        System.out.print("    |"); // Cella vuota.
                    } else if (i == index1 && j >= index2 && j < index2 + 4) {
                        // Cella vincente, colorazione viola, ma mantiene il simbolo.
                        Colore colore = this.tabellaGioco[i][j];
                        char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo;
                        System.out.print(" \033[0;35m" + simbolo + "  \033[0m|"); // Colore viola per i simboli vincenti.
                    } else {
                        // Colore normale.
                        Colore colore = this.tabellaGioco[i][j];
                        char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo;
                        String coloreSimbolo = (colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m"; // Giallo o Rosso.
                        System.out.print(coloreSimbolo + " " + simbolo + "  \u001B[0m|"); // Viene aggiunto uno spazio extra per mantenere l'allineamento.
                    }
                }
                // Gestione del caso verticale.
                else if (metodoVerifica.equals("verticale")) {
                    if (this.tabellaGioco[i][j] == null) {
                        System.out.print("    |"); // Cella vuota.
                    } else if (j == index2 && i >= index1 && i < index1 + 4) {
                        // Cella vincente, colorazione viola, ma mantiene il simbolo.
                        Colore colore = this.tabellaGioco[i][j];
                        char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo;
                        System.out.print(" \033[0;35m" + simbolo + "  \033[0m|"); // Colore viola per i simboli vincenti.
                    } else {
                        // Colore normale
                        Colore colore = this.tabellaGioco[i][j];
                        char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo;
                        String coloreSimbolo = (colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m"; // Giallo o Rosso
                        System.out.print(coloreSimbolo + " " + simbolo + "  \u001B[0m|"); // Viene aggiunto uno spazio extra per mantenere l'allineamento.
                    }
                }

                // Gestione del caso diagonale.
                else if (metodoVerifica.equals("diagonale")) {
                    // Memorizza gli indici delle 4 celle da colorare.
                    int[] righe = new int[4];
                    int[] colonne = new int[4];

                    for (int k = 0; k < 4; k++) {
                        righe[k] = index1 + k;
                        colonne[k] = index2 + k;
                    }

                    if (this.tabellaGioco[i][j] == null) {
                        System.out.print("    |"); // Cella vuota.
                    } else {
                        boolean daColorare = false;
                        for (int k = 0; k < 4; k++) {
                            if (i == righe[k] && j == colonne[k]) {
                                daColorare = true;
                                break;
                            }
                        }

                        Colore colore = this.tabellaGioco[i][j];
                        char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo;
                        String coloreSimbolo = (colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m"; // Giallo o Rosso.
                        if (daColorare) {
                            System.out.print(" \033[0;35m" + simbolo + "  \033[0m|"); // Colore viola per i simboli vincenti.
                        } else {
                            System.out.print(coloreSimbolo + " " + simbolo + "  \u001B[0m|"); // Colore normale.
                        }
                    }
                }
                // Gestione del caso diagonale inversa.
                else if (metodoVerifica.equals("diagonaleInversa")) {
                    // Memorizza gli indici delle 4 celle da colorare.
                    int[] righe = new int[4];
                    int[] colonne = new int[4];

                    for (int k = 0; k < 4; k++) {
                        righe[k] = index1 + k;
                        colonne[k] = index2 - k;
                    }

                    if (this.tabellaGioco[i][j] == null) {
                        System.out.print("    |"); // Cella vuota.
                    } else {
                        boolean daColorare = false;
                        for (int k = 0; k < 4; k++) {
                            if (i == righe[k] && j == colonne[k]) {
                                daColorare = true;
                                break;
                            }
                        }

                        Colore colore = this.tabellaGioco[i][j];
                        char simbolo = (colore == Colore.GIALLO) ? giocatori[0].simbolo : giocatori[1].simbolo;
                        String coloreSimbolo = (colore == Colore.GIALLO) ? "\u001B[31m" : "\u001B[33m"; // Giallo o Rosso.
                        if (daColorare) {
                            System.out.print(" \033[0;35m" + simbolo + "  \033[0m|"); // Colore viola per i simboli vincenti.
                        } else {
                            System.out.print(coloreSimbolo + " " + simbolo + "  \u001B[0m|"); // Colore normale.
                        }
                    }
                }

            }

            System.out.println("");
        }
        System.out.println("+----+----+----+----+----+----+----+");
    }

}
