import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class LojaGUI extends JFrame implements ActionListener {
    private JButton botaoVisualizar;
    private JButton botaoAdicionar;
    private JButton botaoComprar;
    private JTextArea areaProdutos;

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
        areaProdutos = new JTextArea();

        botaoVisualizar.addActionListener(this);
        botaoAdicionar.addActionListener(this);
        botaoComprar.addActionListener(this);

        painel.add(botaoVisualizar);
        painel.add(botaoAdicionar);
        painel.add(botaoComprar);
        painel.add(new JScrollPane(areaProdutos));

        add(painel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botaoVisualizar) {
            visualizarProdutos();
        } else if (e.getSource() == botaoAdicionar) {
            adicionarProduto();
        } else if (e.getSource() == botaoComprar) {
            comprarProduto();
        }
    }

    private void visualizarProdutos() {
        areaProdutos.setText("");
        areaProdutos.append("=== Produtos na Lista ===\n");
        for (Produto produto : listaProdutos) {
            areaProdutos.append(produto.getNome() + " - R$ " + String.format("%.2f", produto.getPreco()) + " ("
                    + produto.getQuantidade() + " unidades)\n");
        }
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
                visualizarProdutos();
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
        frame.setSize(300, 250);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2));

        JLabel labelProduto = new JLabel("Nome do Produto:");
        JTextField campoProduto = new JTextField();
        JLabel labelQuantidade = new JLabel("Quantidade:");
        JTextField campoQuantidade = new JTextField();
        JLabel labelDinheiro = new JLabel("Dinheiro recebido:");
        JTextField campoDinheiro = new JTextField();
        JCheckBox checkCartao = new JCheckBox("Pagar com cartão");

        JButton botaoComprar = new JButton("Comprar");
        botaoComprar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeProduto = campoProduto.getText();
                int quantidadeDesejada = Integer.parseInt(campoQuantidade.getText());
                double dinheiroRecebido = Double.parseDouble(campoDinheiro.getText());
                boolean usarCartao = checkCartao.isSelected();

                for (Produto produto : listaProdutos) {
                    if (produto.getNome().equals(nomeProduto)) {
                        if (quantidadeDesejada <= produto.getQuantidade()) {
                            double total = quantidadeDesejada * produto.getPreco();

                            if (usarCartao) {
                                // Caso seja pagamento com cartão
                                JOptionPane.showMessageDialog(null,
                                        "Compra com cartão realizada com sucesso.\nTotal pago: R$ "
                                                + String.format("%.2f", total));
                            } else {
                                // Caso seja pagamento com dinheiro
                                if (dinheiroRecebido >= total) {
                                    double troco = dinheiroRecebido - total;
                                    JOptionPane.showMessageDialog(null,
                                            "Compra realizada com sucesso.\nTotal pago: R$ "
                                                    + String.format("%.2f", total) + "\nTroco: R$ "
                                                    + String.format("%.2f", troco));
                                } else {
                                    JOptionPane.showMessageDialog(null,
                                            "Dinheiro recebido insuficiente. Compra não realizada.");
                                    return;
                                }
                            }

                            produto.setQuantidade(produto.getQuantidade() - quantidadeDesejada);
                            frame.dispose();
                            return;
                        } else {
                            JOptionPane.showMessageDialog(null, "Quantidade insuficiente em estoque.");
                            return;
                        }
                    }
                }
                JOptionPane.showMessageDialog(null, "Produto não encontrado.");
            }
        });

        panel.add(labelProduto);
        panel.add(campoProduto);
        panel.add(labelQuantidade);
        panel.add(campoQuantidade);
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
