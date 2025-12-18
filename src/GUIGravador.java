
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener; 

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Semaphore;

public class GUIGravador extends JFrame {
    
	private static final long serialVersionUID = 1L;
	private JTextField txtRaio, txtAngulo, txtDistancia, txtRobot, txtFicheiro;
    private JCheckBox chkLigar;
    private JButton btnFrente, btnEsquerda, btnDireita, btnTras, btnParar, btnGravar, 
                    btnReproduzir, btnLimpar, btnImprimir, btnSelecionarFicheiro;
    private JSpinner spnNumero;
    private JCheckBox rdbMovimentos;
    private JTextArea txtConsola;

   
    private RobotLegoEV3 robot;
    private Dados dados;
    private Movimentos_Aleatorios mov;
    Thread tRobot;
    Thread tmov;
    private Gravador gravador;
    private My_Robot myRobot;
    void myInit() {
        this.robot = new RobotLegoEV3();
        this.dados = new Dados(robot);
        this.myRobot = new My_Robot(dados);
        this.tRobot = new Thread(myRobot);
        tRobot.start();
        this.gravador = new Gravador(myRobot);
        myRobot.setGravador(gravador);
    }
    
    public GUIGravador() {
        setTitle("GUI Gravador");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650); 
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        
        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 5, 10));

        
        JPanel line1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        line1.add(new JLabel("Raio"));
        txtRaio = new JTextField("20");
        txtRaio.setPreferredSize(new Dimension(50, 25));
        line1.add(txtRaio);

        line1.add(new JLabel("Ângulo"));
        txtAngulo = new JTextField();
        txtAngulo.setPreferredSize(new Dimension(80, 25));
        line1.add(txtAngulo);

        line1.add(new JLabel("Distância"));
        txtDistancia = new JTextField();
        txtDistancia.setPreferredSize(new Dimension(80, 25));
        line1.add(txtDistancia);

        line1.add(new JLabel("Robot"));
        txtRobot = new JTextField();
        txtRobot.setPreferredSize(new Dimension(100, 25));
        line1.add(txtRobot);

        panelTop.add(line1);

        // Linha 2: checkbox "Ligar"
        JPanel line2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        chkLigar = new JCheckBox("Ligar");
        line2.add(chkLigar);

        panelTop.add(line2);
        add(panelTop, BorderLayout.NORTH);
        
        // Painel Meio (Contém Movimento e Gravador - CENTER do JFrame) 
        JPanel panelMiddle = new JPanel(new GridLayout(2, 1, 10, 10)); // GridLayout para empilhar Movimento e Gravador
        panelMiddle.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));

        
        JPanel panelMovimento = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        Dimension buttonSize = new Dimension(100, 40);

        // Botões de Movimento
        btnFrente = new JButton("FRENTE");
        btnFrente.setBackground(Color.GREEN.brighter());
        btnFrente.setPreferredSize(buttonSize);
        gbc.gridx = 2; gbc.gridy = 0;
        panelMovimento.add(btnFrente, gbc);

        btnEsquerda = new JButton("ESQUERDA");
        btnEsquerda.setBackground(Color.MAGENTA.darker());
        btnEsquerda.setPreferredSize(buttonSize);
        gbc.gridx = 1; gbc.gridy = 1;
        panelMovimento.add(btnEsquerda, gbc);

        btnParar = new JButton("PARAR");
        btnParar.setBackground(Color.RED);
        btnParar.setForeground(Color.WHITE);
        btnParar.setPreferredSize(buttonSize);
        gbc.gridx = 2; gbc.gridy = 1;
        panelMovimento.add(btnParar, gbc);

        btnDireita = new JButton("DIREITA");
        btnDireita.setBackground(Color.BLUE.darker());
        btnDireita.setForeground(Color.WHITE);
        btnDireita.setPreferredSize(buttonSize);
        gbc.gridx = 3; gbc.gridy = 1;
        panelMovimento.add(btnDireita, gbc);

        btnTras = new JButton("TRÁS");
        btnTras.setBackground(new Color(255, 69, 147));
        btnTras.setPreferredSize(buttonSize);
        gbc.gridx = 2; gbc.gridy = 2;
        panelMovimento.add(btnTras, gbc);
        
        panelMiddle.add(panelMovimento); // Adiciona o painel de movimento (NORTH do panelMiddle)

        // 2. Painel Gravador Ficheiro (CENTER do panelMiddle)
        JPanel panelGravador = new JPanel(new BorderLayout(5, 5));
        panelGravador.setBorder(BorderFactory.createTitledBorder("Gravador"));

        // Painel para o campo do ficheiro
        JPanel panelFicheiro = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        panelFicheiro.add(new JLabel("Ficheiro"));
        
        
        txtFicheiro = new JTextField(60); 
        panelFicheiro.add(txtFicheiro);
        
        // Botão de reticências (Selecionar Ficheiro)
        btnSelecionarFicheiro = new JButton("...");
        btnSelecionarFicheiro.setPreferredSize(new Dimension(30, 25));
        panelFicheiro.add(btnSelecionarFicheiro);
        
        panelGravador.add(panelFicheiro, BorderLayout.NORTH);

        // Painel para os botões Gravar/Reproduzir
        JPanel panelBotoesGravador = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        
        
        btnGravar = new JButton("Gravar"); 
        btnReproduzir = new JButton("Reproduzir");
        
        panelBotoesGravador.add(btnGravar);
        panelBotoesGravador.add(btnReproduzir);
        
        panelGravador.add(panelBotoesGravador, BorderLayout.CENTER);
        
        panelMiddle.add(panelGravador); // Adiciona o painel do gravador (SOUTH do panelMiddle)
        
        add(panelMiddle, BorderLayout.CENTER); // Adiciona o painel central ao JFrame
        
        // Painel Consola 
        JPanel panelConsolaGlobal = new JPanel(new BorderLayout(10, 10));
        panelConsolaGlobal.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Margem à volta
        
        // Área de texto
        JPanel panelConsolaArea = new JPanel(new BorderLayout());
        panelConsolaArea.add(new JLabel("Consola"), BorderLayout.NORTH);

        txtConsola = new JTextArea(8, 40);
        txtConsola.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtConsola);
        panelConsolaArea.add(scroll, BorderLayout.CENTER);
        
        panelConsolaGlobal.add(panelConsolaArea, BorderLayout.CENTER);
        
        // Botões Limpar/Imprimir e Opções
        JPanel panelFooterConsola = new JPanel(new BorderLayout());
        
        
        
        JPanel panelBotoesFim = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnLimpar = new JButton("Limpar");
        btnImprimir = new JButton("Imprimir");
        
        panelBotoesFim.add(btnLimpar);
        panelBotoesFim.add(btnImprimir);
        
        panelFooterConsola.add(panelBotoesFim, BorderLayout.CENTER);
        
        panelConsolaGlobal.add(panelFooterConsola, BorderLayout.SOUTH);
        
        add(panelConsolaGlobal, BorderLayout.SOUTH); // Adiciona a Consola ao SOUTH do JFrame

        myInit();
        configurarEventos();
    }


    public Gravador getGravador()
    {
    	return this.gravador;
    }
    
    private void configurarEventos() {
    	chkLigar.addActionListener(e -> {
    	    try {
    	        if (chkLigar.isSelected()) {
    	            dados.setRobotName(txtRobot.getText());
    	            dados.getRobo().OpenEV3(dados.getRoboName());
    	            dados.getRobo().SensorToque(1);
    	        } else {
    	        	mov.setEstado(Movimentos_Aleatorios.Estado.Fim);
    	            dados.getRobo().CloseEV3();
    	        }
    	    } catch (Exception ex) {
    	        JOptionPane.showMessageDialog(null, "Erro ao ligar/desligar o robô: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
    	        chkLigar.setSelected(false);
    	    }
    	});
    	
        btnFrente.addActionListener(e -> {
            try {
                int dist = Integer.parseInt(txtDistancia.getText());
                Comando c = new Comando(Comando.Tipo.RETA, dist);

                myRobot.enviarComando(c);
                txtConsola.append("Movimento: Frente (" + dist + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: distância inválida\n");
            } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        btnEsquerda.addActionListener(e -> {
            try {
                int raio = Integer.parseInt(txtRaio.getText());
                int angulo = Integer.parseInt(txtAngulo.getText());
                Comando c = new Comando(Comando.Tipo.CE, raio, angulo);

                myRobot.enviarComando(c);
                txtConsola.append("Movimento: Esquerda (raio=" + txtRaio.getText() + ", ângulo=" + txtAngulo.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: raio/ângulo inválidos\n");
            } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        btnDireita.addActionListener(e -> {
            try {
            	int raio = Integer.parseInt(txtRaio.getText());
                int angulo = Integer.parseInt(txtAngulo.getText());
                Comando c = new Comando(Comando.Tipo.CD, raio, angulo);
                myRobot.enviarComando(c);
                txtConsola.append("Movimento: Direita (raio=" + txtRaio.getText() + ", ângulo=" + txtAngulo.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: raio/ângulo inválidos\n");
            } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });

        btnTras.addActionListener(e -> {
            try {
            	int dist = Integer.parseInt(txtDistancia.getText());
            	Comando c = new Comando(Comando.Tipo.RETA, -dist);
            	myRobot.enviarComando(c);
                txtConsola.append("Movimento: Marcha atrás (" + txtDistancia.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: distância inválida\n");
            } catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        });
        
        btnParar.addActionListener(e -> {
        	Comando c = new Comando(Comando.Tipo.PARAR);
        	try {
				myRobot.enviarComando(c);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
            txtConsola.append("Movimento: Parar\n");
        });
        
        btnGravar.addActionListener(e -> {

            if (!gravador.estaAtivo()) {
                gravador.iniciar();
                txtConsola.append("🎙 Gravação iniciada\n");
            } else {
                gravador.parar(txtFicheiro.getText());
                txtConsola.append("💾 Gravação terminada\n");
            }
        });
        
        btnReproduzir.addActionListener(e -> {
            gravador.reproduzirComandos(txtFicheiro.getText());
            txtConsola.append("▶ Reproduzindo comandos\n");
        });
        
        btnLimpar.addActionListener(e -> {
            txtConsola.setText("");
            txtConsola.append("Consola limpa.\n");
        });
        
        btnImprimir.addActionListener(e -> {
            txtConsola.append("A imprimir consola...\n");
        });
        
        btnSelecionarFicheiro.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int resultado = chooser.showOpenDialog(this);

            if (resultado == JFileChooser.APPROVE_OPTION) {
                String caminho = chooser.getSelectedFile().getAbsolutePath();
                txtFicheiro.setText(caminho);
                txtConsola.append("Ficheiro selecionado: " + caminho + "\n");
            } else {
                txtConsola.append("Seleção de ficheiro cancelada.\n");
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIGravador frame = new GUIGravador();
            frame.setVisible(true);
        });
    }
}


