public class Hospede extends Thread {
    private String nome;
    private int numPessoasNoGrupo;
    private Quarto[] quartos;

    public Hospede(String nome, int numPessoasNoGrupo, Quarto[] quartos) {
        this.nome = nome;
        this.numPessoasNoGrupo = numPessoasNoGrupo;
        this.quartos = quartos;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getNumPessoasNoGrupo() {
        return numPessoasNoGrupo;
    }

    public void setNumPessoasNoGrupo(int numPessoasNoGrupo) {
        this.numPessoasNoGrupo = numPessoasNoGrupo;
    }

    @Override
    public void run() {
        try {
            Quarto quarto = getQuartoDisponivel(); // Obtém um quarto disponível
            if (quarto != null) {
                synchronized (quarto) {
                    // Verifica se o quarto está disponível para reserva
                    if (!quarto.isOcupado() && quarto.isChaveNaRecepcao()) {
                        // Marca o quarto como ocupado
                        quarto.setOcupado(true); 
                        // Remove a chave da recepção
                        quarto.setChaveNaRecepcao(false); 
                        System.out.println(nome + " reservou o quarto " + quarto.getNumero());
                        // Simula a estadia no hotel
                        Thread.sleep(5000); 
                        // Marca o quarto como desocupado
                        quarto.setOcupado(false); 
                        // Devolve a chave à recepção
                        quarto.setChaveNaRecepcao(true); 
                        System.out.println(nome + " desocupou o quarto " + quarto.getNumero());

                        // Limpeza do Quarto
                        limparQuarto(quarto);
                    } else {
                        System.out.println(nome + " não conseguiu reservar um quarto.");
                    }
                }
            } else {
                System.out.println(nome + " não conseguiu reservar um quarto.");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void limparQuarto(Quarto quarto) {
        // Lógica para limpar o quarto
        System.out.println("A camareira está limpando o quarto: " + quarto.getNumero());

        // Exemplo: Simula a limpeza, alterando o estado do quarto
        quarto.setOcupado(false);
        quarto.setChaveNaRecepcao(true);
        System.out.println("Quarto " + quarto.getNumero() + " limpo e pronto para ocupação.");
    }

    private Quarto getQuartoDisponivel() {
        for (Quarto quarto : quartos) {
            if (!quarto.isOcupado() && quarto.isChaveNaRecepcao()) {
                return quarto;
            }
        }
        return null;
    }
}
