import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Gravador {

    private My_Robot robot;
    private boolean imprimirConsola = true;

    private boolean ativo = false;
    private List<Comando> comandosGravados = new ArrayList<>();

    public Gravador(My_Robot robot) {
        this.robot = robot;
    }

    public void setImprimirConsola(boolean imprimir) {
        this.imprimirConsola = imprimir;
    }

    /* =========================
       CONTROLO DA GRAVAÇÃO
       ========================= */

    public void iniciar() {
        comandosGravados.clear();
        ativo = true;
        if (imprimirConsola)
            System.out.println("🎙 Gravação iniciada");
    }

    public void parar(String ficheiro) {
        ativo = false;
        guardarEmFicheiro(ficheiro);
        if (imprimirConsola)
            System.out.println("💾 Gravação terminada");
    }

    public boolean estaAtivo() {
        return ativo;
    }

    public synchronized void gravar(Comando c) {
        if (!ativo) return;

        comandosGravados.add(c);
        if (imprimirConsola) {
            System.out.println("Gravado: " + c.tipo + "," + c.valor1 + "," + c.valor2);
        }
    }

    public void guardarEmFicheiro(String ficheiro) {
        try (Writer writer = new BufferedWriter(new FileWriter(ficheiro))) {
            for (Comando c : comandosGravados) {
                writer.write(c.tipo.name() + "," + c.valor1 + "," + c.valor2 + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reproduzirComandos(String ficheiro) {
        try (BufferedReader reader = new BufferedReader(new FileReader(ficheiro))) {

            String linha;
            while ((linha = reader.readLine()) != null) {

                String[] partes = linha.split(",");
                Comando.Tipo tipo = Comando.Tipo.valueOf(partes[0]);
                int v1 = Integer.parseInt(partes[1]);
                int v2 = Integer.parseInt(partes[2]);

                robot.enviarComando(new Comando(tipo, v1, v2));
                //robot.enviarComando(new Comando(Comando.Tipo.ESPERAR, 1000));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void limparConsola() {
        for (int i = 0; i < 50; i++) System.out.println();
    }
}


