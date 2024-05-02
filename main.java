import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        // Inicialização dos quartos
        Quarto[] quartos = new Quarto[10];
        for (int i = 0; i < 10; i++) {
            quartos[i] = new Quarto(i + 1, 4); 
        }

        // Inicia as threads para os recepcionistas
        BlockingQueue<Hospede> filaEspera = new ArrayBlockingQueue<>(50);
        BlockingQueue<Quarto> quartosDisponiveis = new ArrayBlockingQueue<>(10);
        BlockingQueue<Quarto> quartosLimpeza = new ArrayBlockingQueue<>(10);

        // Cria 5 recepcionistas
        for (int i = 0; i < 5; i++) {
            new Recepcionista(filaEspera, quartosDisponiveis, quartosLimpeza).start(); // Cria 5 recepcionistas
        }

        // Inicia as threads para as camareiras
        for (int i = 0; i < 10; i++) {
            new Camareira(quartos).start(); // Cria 10 camareiras
        }

        // Introduzindo um atraso antes de iniciar os hóspedes
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Inicia as threads para os hóspedes
        for (int i = 0; i < 50; i++) {
            new Hospede("Hóspede " + (i + 1), 1, quartos).start(); // Cria 50 hóspedes
        }
    }
}
