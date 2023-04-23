package LojaEletronicos;

public class SmartWatch extends Equipamento {
	
	private static final long serialVersionUID = 1L;
	private String tipoPulseira;
	
	
	public SmartWatch (String nome, String marca, String modelo, String tipoPulseira) {
		super(nome, marca, modelo);
		this.setTipoPulseira(tipoPulseira);
		this.tamanhoTela = "tela pequena";
	}


	public String getTipoPulseira() {
		return tipoPulseira;
	}
	public void setTipoPulseira(String tipoPulseira) {
		this.tipoPulseira = tipoPulseira;
	}
	
	public String toString() {
		return super.toString() + "Tipo de pulseira:"+ getTipoPulseira() +"\n";		
	}
	
}
