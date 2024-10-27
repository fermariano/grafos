import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Grafo {
    Vertice[] vertices;
    int numVertice;
    int time = 0;

    Grafo(int numVertice) {
        vertices = new Vertice[numVertice];
        inicializar(numVertice);
        this.numVertice = numVertice;
    }

    void inicializar(int tam) {
        for (int i = 0; i < tam; i++) {
            vertices[i] = new Vertice();
        }
    }

    void setVertice(int pos) {
        vertices[pos - 1].numero = pos;
    }

    Vertice getVertice(int pos) {
        return vertices[pos - 1];
    }

    Grafo cloneGrafo() {
        Grafo clone = new Grafo(numVertice);
        int pos = 0;

        for (Vertice vertice : vertices) {
            clone.vertices[pos] = this.vertices[pos].clone();
            pos++;
        }

        return clone;

    }

    
    
}