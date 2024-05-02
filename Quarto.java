import java.util.ArrayList;
import java.util.List;

public class Quarto {
    private int numero;
    private int capacidade;
    private boolean ocupado;
    private boolean chaveNaRecepcao;
    private List<Hospede> hospedes;

    public Quarto(int numero, int capacidade) {
        this.numero = numero;
        this.capacidade = capacidade;
        this.ocupado = false;
        this.chaveNaRecepcao = true;
        this.hospedes = new ArrayList<>();
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

    public synchronized int getQuantidadeHospedes() {
        return hospedes.size();
    }

    public synchronized void adicionarHospede(Hospede hospede) {
        hospedes.add(hospede);
    }

    public synchronized void adicionarHospedeParcial(Hospede hospede, int quantidade) {
        for (int i = 0; i < quantidade; i++) {
            hospedes.add(hospede);
        }
    }
}
