import java.util.*;

class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Getters e Setters (opcional)
    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "nome='" + nome + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                '}';
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}

public class GerenciamentoPadaria {
    private static List<Produto> listaProdutos = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Sistema de Gerenciamento de Padaria ===");
            System.out.println("1. Adicionar Produto");
            System.out.println("2. Comprar Produto");
            System.out.println("3. Visualizar Produtos");
            System.out.println("4. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    adicionarProduto(scanner);
                    break;
                case 2:
                    comprarProduto(scanner);
                    break;
                case 3:
                    visualizarProdutos();
                    break;
                case 4:
                    System.out.println("Encerrando o programa. Até mais!");
                    System.exit(0);
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void adicionarProduto(Scanner scanner) {
        System.out.print("Nome do produto: ");
        String nome = scanner.next();
        System.out.print("Preço do produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine(); // Consumir a quebra de linha

        System.out.print("Quantidade em estoque: ");
        int quantidade = scanner.nextInt();

        Produto produto = new Produto(nome, preco, quantidade);
        listaProdutos.add(produto);
    }

    private static void comprarProduto(Scanner scanner) {
        System.out.print("Digite o nome do produto que deseja comprar: ");
        String nomeProduto = scanner.next();

        // Procurar o produto na lista
        for (Produto produto : listaProdutos) {
            if (produto.getNome().equals(nomeProduto)) {
                System.out.print("Quantidade desejada: ");
                int quantidadeDesejada = scanner.nextInt();

                if (quantidadeDesejada <= produto.getQuantidade()) {
                    double total = quantidadeDesejada * produto.getPreco();
                    System.out.printf("Total a pagar: R$ %.2f%n", total);
                    produto.setQuantidade(produto.getQuantidade() - quantidadeDesejada);

                    // Opção de pagamento
                    System.out.print("Forma de pagamento (digite 'cartao' ou 'dinheiro'): ");
                    String formaPagamento = scanner.next();

                    if (formaPagamento.equalsIgnoreCase("cartao")) {
                        // Lógica para processar o pagamento com cartão
                        System.out.println("Pagamento com cartão realizado com sucesso!");
                    } else if (formaPagamento.equalsIgnoreCase("dinheiro")) {
                        // Lógica para processar o pagamento em dinheiro (incluindo o troco)
                        System.out.print("Valor recebido em dinheiro: ");
                        double valorRecebido = scanner.nextDouble();

                        if (valorRecebido >= total) {
                            double troco = valorRecebido - total;
                            System.out.printf("Troco a ser dado: R$ %.2f%n", troco);
                        } else {
                            System.out
                                    .println("Valor insuficiente. O cliente deve pagar pelo menos o total da compra.");
                        }
                    } else {
                        System.out.println("Opção de pagamento inválida. Escolha 'cartao' ou 'dinheiro'.");
                    }
                } else {
                    System.out.println("Quantidade insuficiente em estoque.");
                }
                return;
            }
        }
    }

    private static void visualizarProdutos() {
        System.out.println("\n=== Produtos na Lista ===");
        for (Produto produto : listaProdutos) {
            System.out.printf("%s - R$ %.2f (%d unidades)%n", produto.getNome(), produto.getPreco(),
                    produto.getQuantidade());
        }
    }
}
