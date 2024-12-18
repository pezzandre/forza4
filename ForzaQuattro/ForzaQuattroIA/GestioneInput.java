import java.util.Scanner;

    //classe di utilità che gestisce l’input da parte dell’utente. Dovrà
    //esisterne un’unica istanza in tutto il programma, e dovrà essere passata ove
    //necessario alle classi che ne faranno uso tramite parametro del costruttore.
    //Dovrà essere presente un metodo, void rilascia(), che al suo interno chiuda
    //lo scanner. Questo metodo dovrà essere chiamato come ultima istruzione
    //dell’intero programma.

 /* Le funzioni di inserimento potevano essere di meno, passando parametri in più;
 */

public class GestioneInput {

    Scanner input;

    GestioneInput(){
        this.input = new Scanner(System.in);
    }

    String validaNome(){
        System.out.print("Inserire il nome: ");
        String nome = this.input.nextLine().trim();
        while (nome.length()==0){
            System.out.print("Errore! Inserire il nome: ");
            nome = this.input.nextLine().trim();
        }
        return nome;
    }

    Colore validaColore(){
        boolean risultato=false;
        String valore="";
    
        System.out.print("Che colore vuoi essere: ");
        for (int i = 0; i < Colore.values().length; i++) {
            if(i==Colore.values().length-1){
                System.out.print(Colore.values()[i].name());
            }else{
                System.out.print(Colore.values()[i].name()+ " o ");
            }
        }
        System.out.println("?");
        
    
        while(!risultato){
            System.out.print("Inserire un colore: ");
            valore=this.input.nextLine().toUpperCase();
            while(valore.length() == 0){
                System.out.print("Inserire un colore valido: ");
                valore=this.input.nextLine().toUpperCase();
            }
            for (Colore val : Colore.values()) {
                if (val.name().equals(valore)) {
                    risultato = true;
                    break;
                }
            }

            if (!risultato) {
                System.out.println("Valore non valido. Riprovare!");
                valore = "";
            }
        }
        
        return Colore.valueOf(valore);
    }

    int validaMossa(){
        int numero=0;
        System.out.print("Inserire il numero della colonna che si vuole inserire (da 1 a 7): ");
        while(numero<1 || numero>7){
            while(!this.input.hasNextInt()){
                System.out.print("Inserire un intero: ");
                this.input.nextLine();
            }
            numero=this.input.nextInt();
            if(numero<0 || numero>7){
                System.out.print("Errore! Inserire il numero della colonna che si vuole inserire (da 1 a 7): ");
            }
            this.input.nextLine();
        }
        return numero;
    }

    int validaSceltaIniziale(){
        int numero=0;
        System.out.print("Scegliere contro chi giocare (1 o 2): ");
        while(numero<1 || numero>2){
            while(!this.input.hasNextInt()){
                System.out.print("Inserire un intero: ");
                this.input.nextLine();
            }
            numero=this.input.nextInt();
            if(numero<0 || numero>2){
                System.out.print("Errore! Scegliere con chi giocare (1 o 2): ");
            }
            this.input.nextLine();
        }
        return numero;
    }

    int validaNumeroPartite() {
        int numero = 0;
        System.out.print("Inserire il numero di partite da giocare (da 1 a 15, SOLO NUMERI DISPARI): ");
        while (numero < 1 || numero > 15 || numero % 2 == 0) {
            while (!this.input.hasNextInt()) {
                System.out.print("Inserire un intero: ");
                this.input.nextLine();
            }
            numero = this.input.nextInt();
            if (numero < 1 || numero > 15 || numero % 2 == 0) {
                System.out.print("Errore! Inserire il numero di partite da giocare (da 1 a 15, SOLO NUMERI DISPARI): ");
            } else {
                this.input.nextLine();
            }
        }
        return numero;
    }

    void rilascia(){
        this.input.close();
    }
}