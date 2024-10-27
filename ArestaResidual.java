public class ArestaResidual {
    int verticeDestino;
    boolean inversa = false;
    Aresta original;

    ArestaResidual(int verticeDestino, Aresta original) {
        this.verticeDestino = verticeDestino;
        this.original = original;
    }

    void setInversa() {
        inversa = true;
    }

}
