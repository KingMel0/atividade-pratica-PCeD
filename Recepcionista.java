import java.util.concurrent.BlockingQueue;

public class Recepcionista extends Thread {
    private BlockingQueue<Hospede> filaEspera;
    private BlockingQueue<Quarto> quartosDisponiveis;
    private BlockingQueue<Quarto> quartosLimpeza;

    public Recepcionista(BlockingQueue<Hospede> filaEspera, BlockingQueue<Quarto> quartosDisponiveis, BlockingQueue<Quarto> quartosLimpeza) {
        this.filaEspera = filaEspera;
        this.quartosDisponiveis = quartosDisponiveis;
        this.quartosLimpeza = quartosLimpeza;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Recebe um hóspede na recepção
                Hospede hospede = filaEspera.take();

                // Verifica se há quartos disponíveis
                Quarto quarto = quartosDisponiveis.take();

                synchronized (quarto) {
                    // Verifica se o quarto está disponível para alocação
                    if (!quarto.isOcupado() && quarto.isChaveNaRecepcao() && !quartoEmLimpeza(quarto)) {
                        // Verifica se o grupo de hóspedes cabe no quarto
                        if (hospede.getNumPessoasNoGrupo() <= (4 - quarto.getQuantidadeHospedes())) {
                            quarto.adicionarHospede(hospede); // Adiciona o grupo de hóspedes ao quarto
                            quarto.setOcupado(true); // Marca o quarto como ocupado
                            quarto.setChaveNaRecepcao(false); // Remove a chave da recepção
                            System.out.println("Recepcionista alocou o quarto " + quarto.getNumero() + " para " + hospede.getNome());
                        } else {
                            // Se o grupo de hóspedes não couber no quarto, divide em vários quartos
                            while (hospede.getNumPessoasNoGrupo() > 0) {
                                Quarto novoQuarto = quartosDisponiveis.take(); // Obtém um novo quarto disponível
                                synchronized (novoQuarto) {
                                    // Verifica se o novo quarto está disponível para alocação
                                    if (!novoQuarto.isOcupado() && novoQuarto.isChaveNaRecepcao() && !quartoEmLimpeza(novoQuarto)) {
                                        int vagasRestantes = 4 - novoQuarto.getQuantidadeHospedes();
                                        int pessoasNoGrupo = hospede.getNumPessoasNoGrupo();
                                        int pessoasParaAdicionar = Math.min(pessoasNoGrupo, vagasRestantes);
                                        hospede.setNumPessoasNoGrupo(pessoasNoGrupo - pessoasParaAdicionar);
                                        // Adiciona parte do grupo ao quarto
                                        novoQuarto.adicionarHospedeParcial(hospede, pessoasParaAdicionar); 
                                        // Marca o novo quarto como ocupado
                                        novoQuarto.setOcupado(true); 
                                        // Remove a chave da recepção
                                        novoQuarto.setChaveNaRecepcao(false); 
                                        System.out.println("Recepcionista alocou o quarto " + novoQuarto.getNumero() + " para parte do grupo de " + hospede.getNome());
                                    }
                                }
                            }
                        }
                    } else {
                        // Se não houver quartos disponíveis, adiciona o hóspede à fila de espera novamente
                        filaEspera.offer(hospede);
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean quartoEmLimpeza(Quarto quarto) {
        return quartosLimpeza.contains(quarto);
    }
}
