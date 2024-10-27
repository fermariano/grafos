import java.util.LinkedList;

public class VerticeResidual {
    int numero;
    LinkedList<ArestaResidual> arestas = new LinkedList<>();
    int tempoD = 0;
    int tempoT = 0;

    VerticeResidual() {
        int numero = 0;
    }

    VerticeResidual(int numero) {
        this.numero = numero;
    }

    void addAresta(int verticeDestino, Aresta original) {
        arestas.add(new ArestaResidual(verticeDestino, original));
    }

    public void addAresta(ArestaResidual aresta) {
        arestas.add(aresta);
    }

    ArestaResidual getAresta(int verticeDestino) {
        for (ArestaResidual aresta : arestas) {
            if (aresta.verticeDestino == verticeDestino) {
                return aresta;
            }
        }
        return null;
    }

    protected VerticeResidual clone() {
        VerticeResidual clone = new VerticeResidual(numero);

        for (ArestaResidual aresta : arestas) {
            clone.arestas.add(new ArestaResidual(aresta.verticeDestino, aresta.original));
        }

        return clone;
    }
}
