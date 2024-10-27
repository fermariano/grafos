import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Aplicacao {
    // ler o grafo colocando capacidade 1 nas arestas


    static Grafo lerArq(String name) {

        try (BufferedReader file = new BufferedReader(new FileReader(name))) {
            // leitura do número de vértices
            String line;
            line = file.readLine();
            int ver_num = Integer.parseInt(line.trim().split("\\s+")[0]);

            Grafo grafo = new Grafo(ver_num);

            while ((line = file.readLine()) != null) {
                // faz a leitura do vertice de origem e destino
                String[] parts = line.trim().split("\\s+");
                int origem = Integer.parseInt(parts[0]);
                grafo.setVertice(origem);
                int dest = Integer.parseInt(parts[1]);

                grafo.getVertice(origem).addAresta(dest);

            }
            System.out.println("=== Lista preenchida ===");

            return grafo;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;

    }

    static void atualizarFluxo(List<ArestaResidual> caminho) {
        for (ArestaResidual aresta : caminho) {
            if (aresta == null) {
                // System.out.println("Encontrada aresta nula no caminho.");
                continue;
            }
            // System.out.println("Atualizando fluxo da aresta: " + aresta);
            if (aresta.inversa) {
                aresta.original.fluxo--;
            } else {
                aresta.original.fluxo++;
            }
        }
    }
    
    

    public static void main(String[] args) {
        Scanner teclado = new Scanner(System.in);

        System.out.print("Nome do arquivo: ");
        String nome = teclado.nextLine();

        Grafo grafo = lerArq("grafos_modelo/" + nome);

        List<ArestaResidual> caminhoFinal = new ArrayList<>();
        int cont = 0;

        System.out.print("Digite o vértice de origem e o vértice de destino: ");
        int origem = teclado.nextInt();
        int destino = teclado.nextInt();

        // Marca o tempo de início
        long inicio = System.nanoTime();
        while (true) {
            RedeResidual rede = RedeResidual.montarRede(grafo);
            List<ArestaResidual> caminho = rede.caminhoAumentante(origem, destino);

            if (caminho == null) {
                break;
            } else {
                caminhoFinal = caminho;
            }

            System.out.print(origem + " ");
            for (int i = 0; i < caminhoFinal.size(); i++) {
                System.out.print(caminhoFinal.get(i).verticeDestino + " ");
            }
            System.out.println();
            cont++;

            atualizarFluxo(caminho);
        }

        // Marca o tempo de término
        long fim = System.nanoTime();

        // Calcula o tempo de execução em milissegundos
        long tempoExecucao = (fim - inicio) / 1_000_000; // Converte para milissegundos

        System.out.println("Tempo de execução do método caminhoAumentante: " + tempoExecucao + " ms");

        System.out.println("Número de caminhos: " + cont);
        
    }

    
}


    
