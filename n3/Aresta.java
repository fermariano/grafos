public class Aresta {
    int verticeDestino;
    final int capacidade = 1;
    int fluxo;
    

    Aresta(int verticeDestino) {
        this.verticeDestino = verticeDestino;
    }
    
    void setFluxo(int valor) {
        fluxo = valor;
    }

    void addFluxo() {
        fluxo++; 
    }

    void setVerticeDestino(int valor) {
        this.verticeDestino = valor;
    }
}
