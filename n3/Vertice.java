import java.util.LinkedList;

public class Vertice {
    int numero;
    LinkedList<Aresta> arestas = new LinkedList<>();
    

    Vertice() {
        int numero = -3;
    }

    Vertice(int numero) {
        this.numero = numero;
    }

    void addAresta(int verticeDestino) {
        arestas.add(new Aresta(verticeDestino));
    }

    Aresta getAresta(int verticeDestino) {
        for (Aresta aresta : arestas) {
            if (aresta.verticeDestino == verticeDestino) {
                return aresta;
            }
        }
        return null;
    }

    protected Vertice clone() {
        Vertice clone = new Vertice(numero);

        for (Aresta aresta : arestas) {
            clone.arestas.add(new Aresta(aresta.verticeDestino));
        }

        return clone;
    }
}
