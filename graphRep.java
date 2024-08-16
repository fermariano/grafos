/*
  author: Fernanda Rodrigues Dias Mariano
  Representação do grafo utilizando Matriz de Adjacência
 */


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class graphRep {

    // cria a matriz com o tamanho lido na primeira linha
    static int[][] create_matriz(int ver_num) {
        return new int[ver_num][ver_num];
    }

    // 1 represnta a existencia de aresta
    static void preenche_matriz(int origem, int dest, int[][] matriz) {
        matriz[origem-1][dest-1] = 1;
    }

    static int[][] lerArq(String name) {

        int[][] matriz = create_matriz(0);

        try (BufferedReader file = new BufferedReader(new FileReader(name))) {
            // leitura do número de vértices
            String line;
            line = file.readLine();
            int ver_num = Integer.parseInt(line.trim().split("\\s+")[0]);
            matriz = create_matriz(ver_num); // cria a matriz com o tamanho lido

           
            while ((line = file.readLine()) != null) {
                // faz a leitura do vertice de origem e destino
                String[] parts = line.trim().split("\\s+"); 
                int origem = Integer.parseInt(parts[0]);
                int dest = Integer.parseInt(parts[1]);
                
                preenche_matriz(origem, dest, matriz); // preenche a matriz com os valores lidos
                System.out.println("origem: " + origem + " destino: " + dest);

            }
            System.out.println("=== Matriz preenchida ===");

    } catch (IOException e) {
        e.printStackTrace();
    }

    return matriz;
}

    static void get_sucessores(int[][] matriz, int vertice) {
        for (int i = 0; i < matriz[vertice-1].length; i++) {
            if (matriz[vertice-1][i] == 1) {
                System.out.println("Sucessor: " + (i+1));
            } 
        }
    }

    static void get_pre(int[][] matriz, int vertice) {
        for (int i = 0; i < matriz.length; i++) {
            if (matriz[i][vertice-1] == 1) {
                System.out.println("Predecessor: " + (i+1));
            }
        }
    }

    static int leave_degree(int[][] matriz, int vertice) {
        int degree = 0;
        for (int i = 0; i < matriz[vertice-1].length; i++) { // caminha pela linha
            if (matriz[vertice-1][i] == 1) {
                degree++;
            }
        }
        return degree;
    }

    static int join_degree(int [][] matriz, int vertice) {
        int degree = 0;
        for (int i = 0; i < matriz.length; i++) { // percorre a linha!
            if (matriz[i][vertice-1] == 1) {
                degree++;
            } 
        }
        return degree;
    }


    public static void main(String[] args) {
        
            Scanner teclado = new Scanner(System.in);
            System.out.print("Digite o nome do arquivo: ");
            String name = teclado.nextLine();
            int[][] matriz = lerArq(name);

            System.out.print("Digite o vértice desejado: ");
            int ver = teclado.nextInt();

            System.out.println("==== Informações sobre o vértice " + ver + " ====");
            get_pre(matriz, ver);
            get_sucessores(matriz, ver);

            System.out.println("Grau de entrada: " + join_degree(matriz, ver));
            System.out.println("Grau de saída: " + leave_degree(matriz, ver));

            teclado.close();
        
    }
}