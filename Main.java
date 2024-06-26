import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Main {
    public static void main(String[] args) {
        
        // Inicializa as entidades
        Quarto[] quartos = inicializarQuartos(10);
        BlockingQueue<Hospede> filaEspera = new ArrayBlockingQueue<>(50);
        BlockingQueue<Quarto> quartosDisponiveis = new ArrayBlockingQueue<>(10);
        BlockingQueue<Quarto> quartosLimpeza = new ArrayBlockingQueue<>(10);

        // Crie e inicie as threads para as Camareiras
        iniciarCamareiras(quartos);

        // Crie e inicie as threads para os Recepcionistas
        iniciarRecepcionistas(filaEspera, quartosDisponiveis, quartosLimpeza);

        // Introduza um atraso antes de iniciar os hóspedes
        introduzirAtraso();

        // Crie e inicie as threads para os Hóspedes
        Thread[] hospedeThreads = iniciarHospedes(quartos);

        // Aguarde que todas as threads dos hóspedes terminem
        for (Thread thread : hospedeThreads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        // Após todas as threads terminarem, imprima uma mensagem de conclusão
        System.out.println("Encerrando o programa.");
    }

    // Método para inicializar instâncias de Quarto
    private static Quarto[] inicializarQuartos(int numQuartos) {
        Quarto[] quartos = new Quarto[numQuartos];
        for (int i = 0; i < numQuartos; i++) {
            quartos[i] = new Quarto(i + 1, 4); // Os números dos quartos começam em 1
        }
        return quartos;
    }

    // Método para iniciar threads de Camareiras
    private static void iniciarCamareiras(Quarto[] quartos) {
        for (int i = 0; i < 10; i++) {
            new Camareira(quartos).start(); // Crie e inicie 10 Camareiras
        }
    }

    // Método para iniciar threads de Recepcionistas
    private static void iniciarRecepcionistas(BlockingQueue<Hospede> filaEspera,
                                              BlockingQueue<Quarto> quartosDisponiveis,
                                              BlockingQueue<Quarto> quartosLimpeza) {
        for (int i = 0; i < 5; i++) {
            new Recepcionista(filaEspera, quartosDisponiveis, quartosLimpeza).start(); // Crie e inicie 5 Recepcionistas
        }
    }

    // Método para introduzir um atraso
    private static void introduzirAtraso() {
        try {
            Thread.sleep(1000); 
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Método para iniciar threads de Hóspedes
    private static Thread[] iniciarHospedes(Quarto[] quartos) {
        Thread[] threadsHospedes = new Thread[50];
        for (int i = 0; i < 50; i++) {
            threadsHospedes[i] = new Thread(new Hospede("Hóspede " + (i + 1), 1, quartos));
            threadsHospedes[i].start(); 
        }
        return threadsHospedes;
    }
}
