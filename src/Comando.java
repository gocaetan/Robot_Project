public class Comando {
	enum Tipo{
		RETA,
		PARAR,
		CE,
		CD,
		ESPERAR,
		LER_SENSOR
	}
	public Tipo tipo;
	public int valor1;
	public int valor2;
	public Integer resultado = null; // saída (sensor lido)
	
	public Comando(Tipo tipo, int valor1, int valor2)
	{
		this.tipo = tipo;
		this.valor1 = valor1;
		this.valor2 = valor2;
	}
	
	public Comando(Tipo tipo, int valor1)
	{
		this(tipo, valor1, 0);
	}
	
	 public Comando(Tipo tipo)
	 {
		 this(tipo, 0, 0);
	 }
	 
}