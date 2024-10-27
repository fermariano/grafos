import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;

class Grafo {
    Vertice[] vertices;
    int tam;
    int time = 0;

    Grafo(int tam) {
        vertices = new Vertice[tam];
        inicializar(tam);
        this.tam = tam;
    }

    void inicializar(int tam) {
        for (int i = 0; i < tam; i++) {
            vertices[i] = new Vertice();
        }
    }

    public Vertice[] getGrafo() {
        return vertices;
    }

    public void setValor(int valor, int pos) {
        vertices[pos - 1].valor = valor;
    }

    public Vertice getVertice(int pos) {
        return vertices[pos - 1];
    }

    void addTempoD(Vertice v) {
        v.tempoD = ++time;
    }

    void addTempoT(Vertice v) {
        v.tempoT = ++time;
    }

    public void printGrafo() {

        for (int i = 0; i < tam; i++) {
            Aresta inicio = vertices[i].getAresta();
            while (inicio != null) {
                String envio = "Vertice: " + vertices[i].valor + " aresta: " + inicio.valor + " tipo: " + inicio.clas;
                printStringToFile("saida.txt", envio);
                inicio = inicio.prox;
            }

        }

    }

    public void printVertice(int pos) {
        Aresta inicio = vertices[pos-1].getAresta();
        while (inicio != null) {
            System.out.println("Vertice: " + vertices[pos-1].valor + " aresta: " + inicio.valor + " tipo: " + inicio.clas);
            inicio = inicio.prox;
        }

    }

    public static void printStringToFile(String fileName, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true))) {
            writer.write(content);
            writer.newLine(); // Adiciona uma nova linha após o conteúdo
        } catch (IOException e) {
            System.err.println("Erro ao escrever no arquivo: " + e.getMessage());
        }
    }

    public void busca_prof(int pos) {
        for (Vertice v : vertices) {
            if (v.tempoD == 0) {
                busca_prof(v);
            }

        }
        printGrafo();
        printVertice(pos);
    }

    public void busca_prof(Vertice v) {
        addTempoD(v);
        Aresta aresta = v.getAresta();
        while (aresta != null) {
            if (vertices[aresta.valor - 1].tempoD == 0) {
                aresta.setType(classificacao.ARVORE);
                vertices[aresta.valor - 1].pai = v;
                busca_prof(vertices[aresta.valor - 1]);
            } else {
                if (vertices[aresta.valor - 1].tempoT == 0) {
                    aresta.setType(classificacao.RETORNO);
                } else {
                    if (v.tempoD < vertices[aresta.valor - 1].tempoD) {
                        aresta.setType(classificacao.AVANCO);
                    } else {
                        aresta.setType(classificacao.CRUZAMENTO);
                    }
                }
            }

            aresta = aresta.prox;
        }
        addTempoT(v);

    }

}

class Vertice {
    Aresta aresta;
    int valor;
    int tempoD = 0;
    int tempoT = 0;
    Vertice pai;

    public Aresta getAresta() {
        return this.aresta;
    }

    public void setAresta(Aresta aresta) {
        this.aresta = aresta;
    }

    public int getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    void addAresta(int valor) {
        Aresta inicio = aresta;

        if (inicio == null) {
            aresta = new Aresta(valor);
        } else if (inicio.valor > valor) {
            Aresta temp = new Aresta(valor);
            temp.prox = inicio;
            aresta = temp;
        } else {
            while (inicio.prox != null && inicio.prox.valor < valor) {
                inicio = inicio.prox;
            }

            Aresta temp = new Aresta(valor);

            if (inicio.prox == null) {
                inicio.prox = temp;
            } else {
                temp.setProx(inicio.prox);
                inicio.setProx(temp);
            }
        }

    }

}

enum classificacao {
    ARVORE,
    RETORNO,
    AVANCO,
    CRUZAMENTO
}

class Aresta {
    Aresta prox;
    int valor;
    classificacao clas;

    Aresta(int valor) {
        this.valor = valor;
    }

    public Aresta getProx() {
        return this.prox;
    }

    public void setProx(Aresta prox) {
        this.prox = prox;
    }

    public int getValor() {
        return this.valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public void setType(classificacao clas) {
        this.clas = clas;
    }

}

public class buscaProf {

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
                grafo.setValor(origem, origem);
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

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Digite o nome do arquivo: ");
        String arquivo = sc.nextLine();
        System.out.println("Digite o vértice: ");
        String vertice = sc.nextLine();

        Grafo g = lerArq(arquivo);
        int ver = Integer.parseInt(vertice);
        System.out.println(ver);
        g.busca_prof(ver);

    }

}