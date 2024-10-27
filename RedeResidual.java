import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RedeResidual {

    VerticeResidual[] vertices;
    int numVertice;
    int time = 0;

    RedeResidual(int numVertice) {
        vertices = new VerticeResidual[numVertice];
        inicializar(numVertice);
        this.numVertice = numVertice;
    }

    void inicializar(int tam) {
        for (int i = 0; i < tam; i++) {
            vertices[i] = new VerticeResidual();
        }
    }

    // Montagem da rede residual
    public static RedeResidual montarRede(Grafo original) {
        RedeResidual residual = new RedeResidual(original.numVertice);

        for (int i = 0; i < original.numVertice-1; i++) {
            for (Aresta aresta : original.vertices[i].arestas) {
                if (aresta.fluxo == 1) {
                    residual.vertices[aresta.verticeDestino-1].addAresta(i, aresta);
                    residual.vertices[aresta.verticeDestino-1].arestas.getLast().setInversa();
                } else if (aresta.fluxo == 0) {
                    residual.vertices[i].addAresta(aresta.verticeDestino, aresta);
                }
            }
        }
        return residual;
    }

    public void imprimirRedeResidual(String nomeArquivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo, true))) {
            writer.write("=== Rede Residual ===\n");
            for (VerticeResidual vertice : vertices) {
                if (vertice == null) continue;
                System.out.println(vertice.numero);
                writer.write("Vértice " + vertice.numero + ":\n");
                for (ArestaResidual aresta : vertice.arestas) {
                    String tipoAresta = aresta.inversa ? "Inversa" : "Direta";
                    int fluxo = aresta.original.fluxo;
                    int capacidadeOriginal = aresta.original.capacidade;

                    writer.write("  -> Vértice " + aresta.verticeDestino +
                        " (" + tipoAresta + ")" +
                        ", Fluxo: " + fluxo +
                        ", Capacidade Original: " + capacidadeOriginal + "\n");
                }
            }
            writer.write("\n"); // Adiciona uma linha em branco entre as iterações
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    // Método principal
    public List<ArestaResidual> caminhoAumentante(int origem, int destino) {
        boolean[] visitado = new boolean[numVertice];
        Map<Integer, Integer> predecessores = new HashMap<>();

        boolean encontrouCaminho = dfs(origem, destino, visitado, predecessores);

        if (!encontrouCaminho) {
            System.out.println("Nenhum caminho aumentante encontrado.");
            return null;
        }

        return reconstruirCaminho(origem, destino, predecessores);
    }

    // Método DFS ajustado
    private boolean dfs(int atual, int destino, boolean[] visitado, Map<Integer, Integer> predecessores) {
        if (atual < 1 || atual > numVertice) {
            throw new IllegalArgumentException("Vértice atual inválido: " + atual);
        }

        // System.out.println("Visitando vértice atual: " + atual);
        visitado[atual - 1] = true;
    
        if (atual == destino) {
            System.out.println("Destino encontrado: " + destino);
            return true;
        }
    
        for (ArestaResidual aresta : vertices[atual - 1].arestas) {
            int proximo = aresta.verticeDestino;
            // System.out.println("Analisando aresta para o vértice: " + proximo);
    
            if (proximo < 1 || proximo > numVertice) {
                
            }  else {
                if (!visitado[proximo - 1]) {
                    predecessores.put(proximo, atual);
                    if (dfs(proximo, destino, visitado, predecessores)) {
                        return true;
                    }
                }
            }
    
        }
    
        return false;
    }
    

    private List<ArestaResidual> reconstruirCaminho(int origem, int destino, Map<Integer, Integer> predecessores) {
        List<ArestaResidual> caminho = new ArrayList<>();
        int atual = destino;

        while (atual != origem) {
            Integer predecessor = predecessores.get(atual);
            if (predecessor == null) {
                System.out.println("Predecessor nulo encontrado para o vértice: " + atual);
                return null; // Ou trate o erro apropriadamente
            }

            ArestaResidual aresta = encontrarAresta(predecessor, atual);
            if (aresta == null) {
                System.out.println("Aresta nula encontrada entre " + predecessor + " e " + atual);
                return null; // Ou trate o erro apropriadamente
            }

            caminho.add(0, aresta); // Adiciona no início para manter a ordem
            atual = predecessor;
        }

        return caminho;
    }

    // Dentro da classe RedeResidual
    private ArestaResidual encontrarAresta(int origem, int destino) {
        // Ajuste de índice se os vértices começam em 1
        VerticeResidual verticeOrigem = vertices[origem - 1];

        for (ArestaResidual aresta : verticeOrigem.arestas) {
            if (aresta.verticeDestino == destino) {
                return aresta;
            }
        }
        // Se não encontrar a aresta, retorna null
        return null;
    }

}
