public class Camareira extends Thread {
    private Quarto[] quartos;

    public Camareira(Quarto[] quartos) {
        this.quartos = quartos;
    }

    @Override
    public void run() {
        try {
            while (true) {
                // Aguarda para ser designada para limpar um quarto
                Quarto quarto = getQuartoParaLimpar();

                if (quarto != null) {
                    // Realiza a limpeza do quarto
                    limparQuarto(quarto);

                    // Dorme por um tempo simulando o tempo de limpeza
                    Thread.sleep(5000); // 5 segundos
                } else {
                    // Se não houver quartos para limpar, espera um pouco antes de verificar novamente
                    Thread.sleep(2000); // 2 segundos
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private Quarto getQuartoParaLimpar() {
        for (Quarto quarto : quartos) {
            if (quarto.isOcupado() && !quarto.isChaveNaRecepcao()) {
                return quarto;
            }
        }
        return null;
    }

    private void limparQuarto(Quarto quarto) {
        // Lógica para limpar o quarto
        System.out.println("A camareira está limpando o quarto: " + quarto.getNumero());

        // Exemplo: Simula a limpeza, alterando o estado do quarto
        quarto.setOcupado(false);
        quarto.setChaveNaRecepcao(true);
        System.out.println("Quarto " + quarto.getNumero() + " limpo e pronto para ocupação.");
    }
}
