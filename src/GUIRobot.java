//package fso;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIRobot extends JFrame {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField txtRaio, txtAngulo, txtDistancia, txtRobot;
    private JCheckBox chkLigar;
    private JButton btnFrente, btnEsquerda, btnDireita, btnTras, btnParar;
    private JSpinner spnNumero;
    private JRadioButton rdbMovimentos;
    private JTextArea txtConsola;

    private RobotLegoEV3 robot;
    private Dados dados;

    private void myInit() {
        this.robot = new RobotLegoEV3();
        this.dados = new Dados(robot);
    }

    public GUIRobot() {
        setTitle("GUI Trabalho Prático 1");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Painel de cima (inputs)
        JPanel panelTop = new JPanel(new GridLayout(2, 1, 5, 5));
        panelTop.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Linha 1: inputs
        JPanel line1 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        line1.add(new JLabel("Raio"));
        txtRaio = new JTextField();
        txtRaio.setPreferredSize(new Dimension(80, 25));
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

        // Painel central
        JPanel panelCenter = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        Dimension buttonSize = new Dimension(120, 50);

        btnFrente = new JButton("FRENTE");
        btnFrente.setBackground(Color.GREEN);
        btnFrente.setPreferredSize(buttonSize);
        gbc.gridx = 1; gbc.gridy = 0;
        panelCenter.add(btnFrente, gbc);

        btnEsquerda = new JButton("ESQUERDA");
        btnEsquerda.setBackground(Color.PINK);
        btnEsquerda.setPreferredSize(buttonSize);
        gbc.gridx = 0; gbc.gridy = 1;
        panelCenter.add(btnEsquerda, gbc);

        btnParar = new JButton("PARAR");
        btnParar.setBackground(Color.RED);
        btnParar.setForeground(Color.WHITE);
        btnParar.setPreferredSize(buttonSize);
        gbc.gridx = 1; gbc.gridy = 1;
        panelCenter.add(btnParar, gbc);

        btnDireita = new JButton("DIREITA");
        btnDireita.setBackground(Color.BLUE);
        btnDireita.setForeground(Color.WHITE);
        btnDireita.setPreferredSize(buttonSize);
        gbc.gridx = 2; gbc.gridy = 1;
        panelCenter.add(btnDireita, gbc);

        btnTras = new JButton("TRÁS");
        btnTras.setBackground(new Color(255, 100, 100));
        btnTras.setPreferredSize(buttonSize);
        gbc.gridx = 1; gbc.gridy = 2;
        panelCenter.add(btnTras, gbc);

        add(panelCenter, BorderLayout.CENTER);

        // Painel de baixo 
        JPanel panelBottom = new JPanel(new BorderLayout(10, 10));
        panelBottom.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel panelOptions = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelOptions.add(new JLabel("Número"));
        spnNumero = new JSpinner(new SpinnerNumberModel(1, 1, 16, 1));
        panelOptions.add(spnNumero);

        rdbMovimentos = new JRadioButton("Movimentos Aleatórios");
        panelOptions.add(rdbMovimentos);

        panelBottom.add(panelOptions, BorderLayout.NORTH);

        JPanel panelConsola = new JPanel(new BorderLayout());
        panelConsola.add(new JLabel("Consola"), BorderLayout.NORTH);

        txtConsola = new JTextArea(8, 40);
        txtConsola.setEditable(false);
        JScrollPane scroll = new JScrollPane(txtConsola);
        panelConsola.add(scroll, BorderLayout.CENTER);

        panelBottom.add(panelConsola, BorderLayout.CENTER);

        add(panelBottom, BorderLayout.SOUTH);

        myInit();
        configurarEventos();
    }

    private void configurarEventos() {
    	myInit();
    	chkLigar.addActionListener(e -> {
    	    try {
    	        if (chkLigar.isSelected()) {
    	            dados.setRobotName(txtRobot.getText());
    	            dados.getRobo().OpenEV3(dados.getRoboName());
    	        } else {
    	            dados.getRobo().CloseEV3();
    	        }
    	    } catch (Exception ex) {
    	        JOptionPane.showMessageDialog(
    	            null,
    	            "Erro ao ligar/desligar o robô: " + ex.getMessage(),
    	            "Erro",
    	            JOptionPane.ERROR_MESSAGE
    	        );
    	        chkLigar.setSelected(false);
    	    }
    	});
    	
        btnFrente.addActionListener(e -> {
            try {
                dados.setDistancia(Integer.parseInt(txtDistancia.getText()));
                dados.getRobo().Reta(dados.getDistancia());
                txtConsola.append("Movimento: Frente (" + txtDistancia.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: distância inválida\n");
            }
        });

        btnEsquerda.addActionListener(e -> {
            try {
                dados.setRaio(Integer.parseInt(txtRaio.getText()));
                dados.setAngulo(Integer.parseInt(txtAngulo.getText()));
                dados.getRobo().CurvarEsquerda(dados.getRaio(), dados.getAngulo());
                txtConsola.append("Movimento: Esquerda (raio=" + txtRaio.getText() + ", ângulo=" + txtAngulo.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: raio/ângulo inválidos\n");
            }
        });

        btnDireita.addActionListener(e -> {
            try {
                dados.setRaio(Integer.parseInt(txtRaio.getText()));
                dados.setAngulo(Integer.parseInt(txtAngulo.getText()));
                dados.getRobo().CurvarDireita(dados.getRaio(), dados.getDistancia());
                txtConsola.append("Movimento: Direita (raio=" + txtRaio.getText() + ", ângulo=" + txtAngulo.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: raio/ângulo inválidos\n");
            }
        });

        btnTras.addActionListener(e -> {
            try {
                dados.setDistancia(Integer.parseInt(txtDistancia.getText()));
                dados.getRobo().Reta(-dados.getDistancia());
                txtConsola.append("Movimento: Marcha atrás (" + txtDistancia.getText() + ")\n");
            } catch (NumberFormatException ex) {
                txtConsola.append("Erro: distância inválida\n");
            }
        });
        
        spnNumero.addChangeListener(e -> {
        	int valor = (int) spnNumero.getValue();
        	dados.setRandomMoves(valor);
        });
        
        btnParar.addActionListener(e -> {
            dados.getRobo().Parar(true);
            txtConsola.append("Movimento: Parar\n");
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUIRobot frame = new GUIRobot();
            frame.setVisible(true);
        });
    }
}






