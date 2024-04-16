import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PadariaGUI extends JFrame implements ActionListener {
    private ArrayList<Produto> listaProdutos;

    public PadariaGUI() {
        listaProdutos = new ArrayList<>();

        setTitle("Padaria");
        setSize(800, 600); // Tamanho inicial da janela
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Painel principal com layout BorderLayout
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        painelPrincipal.setBackground(new Color(245, 222, 179)); // Cor de fundo estilo padaria

        // Painel para os botões no topo
        JPanel painelBotoes = new JPanel();
        painelBotoes.setBackground(new Color(245, 222, 179)); // Cor de fundo estilo padaria

        JButton botaoVisualizar = new JButton("Visualizar Produtos");
        JButton botaoAdicionar = new JButton("Adicionar Produto");
        JButton botaoComprar = new JButton("Comprar Produto");

        botaoVisualizar.addActionListener(this);
        botaoAdicionar.addActionListener(this);
        botaoComprar.addActionListener(this);

        // Estilo dos botões
        botaoVisualizar.setBackground(new Color(255, 215, 0)); // Cor do botão estilo padaria
        botaoVisualizar.setForeground(Color.WHITE); // Cor do texto
        botaoAdicionar.setBackground(new Color(255, 69, 0)); // Cor do botão estilo padaria
        botaoAdicionar.setForeground(Color.WHITE); // Cor do texto
        botaoComprar.setBackground(new Color(34, 139, 34)); // Cor do botão estilo padaria
        botaoComprar.setForeground(Color.WHITE); // Cor do texto

        painelBotoes.add(botaoVisualizar);
        painelBotoes.add(botaoAdicionar);
        painelBotoes.add(botaoComprar);

        // Adiciona o painel de botões ao norte do painel principal
        painelPrincipal.add(painelBotoes, BorderLayout.NORTH);

        JTextArea areaProdutos = new JTextArea();
        areaProdutos.setEditable(false); // Para evitar edição direta
        areaProdutos.setBackground(new Color(255, 250, 205)); // Cor de fundo da área de texto estilo padaria

        // Adiciona a área de produtos ao centro do painel principal
        painelPrincipal.add(new JScrollPane(areaProdutos), BorderLayout.CENTER);

        // Adiciona o painel principal à janela
        add(painelPrincipal);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Visualizar Produtos")) {
            mostrarProdutosDetalhados();
        } else if (e.getActionCommand().equals("Adicionar Produto")) {
            adicionarProduto();
        } else if (e.getActionCommand().equals("Comprar Produto")) {
            comprarProduto();
        }
    }

    private void mostrarProdutosDetalhados() {
        JTextArea areaProdutos = new JTextArea();
        areaProdutos.setEditable(false);

        areaProdutos.append("=== Produtos Disponíveis ===\n");
        for (Produto produto : listaProdutos) {
            areaProdutos.append("Nome: " + produto.getNome() + "\n");
            areaProdutos.append("Preço: R$ " + String.format("%.2f", produto.getPreco()) + "\n");
            areaProdutos.append("Quantidade em estoque: " + produto.getQuantidade() + "\n\n");
        }

        JOptionPane.showMessageDialog(this, new JScrollPane(areaProdutos), "Produtos Disponíveis",
                JOptionPane.PLAIN_MESSAGE);
    }

    private void adicionarProduto() {
        JTextField campoNome = new JTextField();
        JTextField campoPreco = new JTextField();
        JTextField campoQuantidade = new JTextField();

        JPanel painel = new JPanel(new GridLayout(3, 2));
        painel.add(new JLabel("Nome do Produto:"));
        painel.add(campoNome);
        painel.add(new JLabel("Preço:"));
        painel.add(campoPreco);
        painel.add(new JLabel("Quantidade:"));
        painel.add(campoQuantidade);

        int resultado = JOptionPane.showOptionDialog(this, painel, "Adicionar Produto", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (resultado == JOptionPane.OK_OPTION) {
            String nome = campoNome.getText();
            double preco = Double.parseDouble(campoPreco.getText());
            int quantidade = Integer.parseInt(campoQuantidade.getText());
            listaProdutos.add(new Produto(nome, preco, quantidade));
            JOptionPane.showMessageDialog(this, "Produto adicionado com sucesso!", "Sucesso",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void comprarProduto() {
        JComboBox<Produto> comboProdutos = new JComboBox<>(listaProdutos.toArray(new Produto[0]));
        JTextField campoQuantidade = new JTextField();
        JTextField campoDesconto = new JTextField();
        JTextField campoDinheiro = new JTextField();
        JCheckBox checkCartao = new JCheckBox("Pagar com Cartão");

        JPanel painel = new JPanel(new GridLayout(6, 2));
        painel.add(new JLabel("Selecione o Produto:"));
        painel.add(comboProdutos);
        painel.add(new JLabel("Quantidade:"));
        painel.add(campoQuantidade);
        painel.add(new JLabel("Desconto (%):"));
        painel.add(campoDesconto);
        painel.add(new JLabel("Dinheiro Recebido:"));
        painel.add(campoDinheiro);
        painel.add(checkCartao);

        int resultado = JOptionPane.showOptionDialog(this, painel, "Comprar Produto", JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE, null, null, null);
        if (resultado == JOptionPane.OK_OPTION) {
            Produto produtoSelecionado = (Produto) comboProdutos.getSelectedItem();
            int quantidadeDesejada = Integer.parseInt(campoQuantidade.getText());
            double desconto = Double.parseDouble(campoDesconto.getText());
            double dinheiroRecebido = Double.parseDouble(campoDinheiro.getText());
            boolean pagarComCartao = checkCartao.isSelected();

            // Cálculo do preço total do produto
            double precoTotal = produtoSelecionado.getPreco() * quantidadeDesejada;

            // Aplica o desconto
            double precoComDesconto = precoTotal - (precoTotal * (desconto / 100.0));

            // Verifica se o pagamento será feito com cartão ou dinheiro
            if (pagarComCartao) {
                JOptionPane.showMessageDialog(this,
                        "Compra realizada com cartão.\nTotal pago: R$ " + String.format("%.2f", precoComDesconto),
                        "Compra Realizada", JOptionPane.INFORMATION_MESSAGE);
            } else {
                // Calcula o troco
                double troco = dinheiroRecebido - precoComDesconto;
                if (troco >= 0) {
                    JOptionPane.showMessageDialog(this,
                            "Compra realizada com dinheiro.\nTotal pago: R$ " + String.format("%.2f", precoComDesconto)
                                    + "\nTroco: R$ " + String.format("%.2f", troco),
                            "Compra Realizada", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Dinheiro insuficiente para pagar a compra.", "Erro",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    public static void main(String[] args) {
        new PadariaGUI();
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

    public Produto(int codigo, String nome2, double preco2, int quantidade2) {
        // TODO Auto-generated constructor stub
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

    @Override
    public String toString() {
        return nome;
    }

    public String getCodigo() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCodigo'");
    }
}
