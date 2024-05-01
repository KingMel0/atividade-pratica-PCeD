public class Quarto {
    private int numero;
    private int capacidade;
    private boolean ocupado;
    private boolean chaveNaRecepcao;
    
    public Quarto(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.ocupado = false;
        this.chaveNaRecepcao = true; // Inicialmente a chave está na recepção
    }
    
    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public void setCapacidade(int capacidade) {
        this.capacidade = capacidade;
    }

    public boolean isOcupado() {
        return ocupado;
    }

    public void setOcupado(boolean ocupado) {
        this.ocupado = ocupado;
    }

    public boolean isChaveNaRecepcao() {
        return chaveNaRecepcao;
    }

    public void setChaveNaRecepcao(boolean chaveNaRecepcao) {
        this.chaveNaRecepcao = chaveNaRecepcao;
    }
}