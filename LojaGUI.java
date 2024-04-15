import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LojaGUI extends JFrame implements ActionListener {
    private JButton botaoVisualizar;
    private JButton botaoAdicionar;
    private JButton botaoComprar;
    private ArrayList<Produto> listaProdutos;

    public LojaGUI() {
        listaProdutos = new ArrayList<>();

        setTitle("Loja");
        setSize(400, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JPanel painel = new JPanel();
        painel.setLayout(new GridLayout(4, 1));

        botaoVisualizar = new JButton("Visualizar Produtos");
        botaoAdicionar = new JButton("Adicionar Produto");
        botaoComprar = new JButton("Comprar Produto");

        botaoVisualizar.addActionListener(this);
        botaoAdicionar.addActionListener(this);
        botaoComprar.addActionListener(this);

        painel.add(botaoVisualizar);
        painel.add(botaoAdicionar);
        painel.add(botaoComprar);

        JTextArea areaProdutos = new JTextArea();
        painel.add(new JScrollPane(areaProdutos));

        add(painel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoVisualizar) {
            mostrarProdutosDetalhados();
        } else if (e.getSource() == botaoAdicionar) {
            adicionarProduto();
        } else if (e.getSource() == botaoComprar) {
            comprarProduto();
        }
    }

    private void mostrarProdutosDetalhados() {
        JFrame janelaProdutosDetalhados = new JFrame("Produtos Detalhados");
        janelaProdutosDetalhados.setSize(400, 300);
        janelaProdutosDetalhados.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JTextArea areaProdutosDetalhados = new JTextArea();
        areaProdutosDetalhados.setEditable(false);

        areaProdutosDetalhados.append("=== Lista Detalhada de Produtos ===\n");
        for (Produto produto : listaProdutos) {
            areaProdutosDetalhados.append("Nome: " + produto.getNome() + "\n");
            areaProdutosDetalhados.append("Preço: R$ " + String.format("%.2f", produto.getPreco()) + "\n");
            areaProdutosDetalhados.append("Quantidade em estoque: " + produto.getQuantidade() + "\n\n");
        }

        janelaProdutosDetalhados.add(new JScrollPane(areaProdutosDetalhados));
        janelaProdutosDetalhados.setVisible(true);
    }

    private void adicionarProduto() {
        JFrame frame = new JFrame("Adicionar Produto");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel labelNome = new JLabel("Nome:");
        JTextField campoNome = new JTextField();
        JLabel labelPreco = new JLabel("Preço:");
        JTextField campoPreco = new JTextField();
        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField();

        JButton botaoSalvar = new JButton("Salvar");
        botaoSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nome = campoNome.getText();
                double preco = Double.parseDouble(campoPreco.getText());
                int quantidade = Integer.parseInt(campoQuantidade.getText());
                listaProdutos.add(new Produto(nome, preco, quantidade));
                frame.dispose();
            }
        });

        panel.add(labelNome);
        panel.add(campoNome);
        panel.add(labelPreco);
        panel.add(campoPreco);
        panel.add(labelQuantidade);
        panel.add(campoQuantidade);
        panel.add(new JLabel()); // Espaço vazio
        panel.add(botaoSalvar);

        frame.add(panel);
        frame.setVisible(true);
    }

    private void comprarProduto() {
        JFrame frame = new JFrame("Comprar Produto");
        frame.setSize(350, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 2));

        JLabel labelProduto = new JLabel("Nome do Produto:");
        JTextField campoProduto = new JTextField();
        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField();
        JLabel labelDesconto = new JLabel("Desconto (%):");
        JTextField campoDesconto = new JTextField();
        JLabel labelDinheiro = new JLabel("Dinheiro recebido:");
        JTextField campoDinheiro = new JTextField();
        JCheckBox checkCartao = new JCheckBox("Pagar com cartão");

        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeProduto = campoProduto.getText();
                int quantidadeDesejada = Integer.parseInt(campoQuantidade.getText());
                double descontoPercentual = Double.parseDouble(campoDesconto.getText());
                double dinheiroRecebido = Double.parseDouble(campoDinheiro.getText());
                boolean usarCartao = checkCartao.isSelected();

                Produto produtoSelecionado = null;
                for (Produto produto : listaProdutos) {
                    if (produto.getNome().equals(nomeProduto)) {
                        produtoSelecionado = produto;
                        break;
                    }
                }

                if (produtoSelecionado == null) {
                    JOptionPane.showMessageDialog(null, "Produto não encontrado.");
                    return;
                }

                if (quantidadeDesejada > produtoSelecionado.getQuantidade()) {
                    JOptionPane.showMessageDialog(null, "Quantidade insuficiente em estoque.");
                    return;
                }

                double precoProduto = produtoSelecionado.getPreco();
                double precoTotal = quantidadeDesejada * precoProduto;
                double descontoValor = (descontoPercentual / 100) * precoTotal;
                double precoFinal = precoTotal - descontoValor;

                if (usarCartao) {
                    JOptionPane.showMessageDialog(null,
                            "Compra com cartão realizada com sucesso.\nTotal pago com desconto: R$ "
                                    + String.format("%.2f", precoFinal));
                } else {
                    if (dinheiroRecebido >= precoFinal) {
                        double troco = dinheiroRecebido - precoFinal;
                        JOptionPane.showMessageDialog(null,
                                "Compra realizada com sucesso.\nTotal pago com desconto: R$ "
                                        + String.format("%.2f", precoFinal) + "\nTroco: R$ "
                                        + String.format("%.2f", troco));
                    } else {
                        JOptionPane.showMessageDialog(null, "Dinheiro recebido insuficiente. Compra não realizada.");
                        return;
                    }
                }

                produtoSelecionado.setQuantidade(produtoSelecionado.getQuantidade() - quantidadeDesejada);
                frame.dispose();
            }
        });

        panel.add(labelProduto);
        panel.add(campoProduto);
        panel.add(labelQuantidade);
        panel.add(campoQuantidade);
        panel.add(labelDesconto);
        panel.add(campoDesconto);
        panel.add(labelDinheiro);
        panel.add(campoDinheiro);
        panel.add(checkCartao);
        panel.add(botaoComprar);

        frame.add(panel);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new LojaGUI();
    }
}

class Produto {
    private String nome;
    private double preco;
    private int quantidade;

    public Produto(String nome, double preco, int quantidade) {
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public String getNome() {
        return nome;
    }

    public double getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
