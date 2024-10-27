import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class GeradorDeGrafos {

    public static void gerarGrafoLinear(int numVertices, String nomeArquivo) throws IOException {
        // O número de arestas em um grafo linear é sempre `numVertices - 1`
        int numArestas = numVertices - 1;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            // Escreve o número de vértices e arestas
            writer.write(numVertices + " " + numArestas + "\n");

            // Conecta cada vértice ao próximo, formando um grafo linear
            for (int i = 1; i < numVertices; i++) {
                writer.write(i + " " + (i + 1) + "\n");
            }
        }

        System.out.println("Arquivo " + nomeArquivo + " gerado com sucesso.");
    }

    public static void main(String[] args) {
        try {
            gerarGrafoLinear(10, "grafo_linear_10.txt");
            gerarGrafoLinear(100, "grafo_linear_100.txt");
            gerarGrafoLinear(1000, "grafo_linear_1000.txt");
            gerarGrafoLinear(5000, "grafo_linear_5000.txt");
            System.out.println("Arquivos gerados com sucesso.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
