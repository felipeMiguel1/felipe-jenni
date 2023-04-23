package LojaEletronicos;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.JOptionPane;



public class NovaLojaEletronicos {
	private ArrayList<Equipamento> equipamento;
	
	public NovaLojaEletronicos() {		
		this.equipamento = new ArrayList<Equipamento>();
	}
	
	public String[] leValores (String [] dadosIn){
		String [] dadosOut = new String [dadosIn.length];

		for (int i = 0; i < dadosIn.length; i++)
			dadosOut[i] = JOptionPane.showInputDialog  ("Entre com " + dadosIn[i]+ ": ");

		return dadosOut;
	}
	
	public SmartPhone lephone() {

		String [] valores = new String [4];
		String [] nomeVal = {"Nome", "marca", "modelo", "quantidadeChips"};
		valores = leValores (nomeVal);

		int quantidadeChips = this.retornaInteiro(valores[3]);

		SmartPhone smartPhone = new SmartPhone (valores[0], valores[1], valores[2], quantidadeChips);
		return smartPhone;		
	}
	
	public SmartWatch lewatch (){

		String [] valores = new String [4];
		String [] nomeVal = {"Nome", "marca", "modelo", "tipoPulseira"};
		valores = leValores (nomeVal);

		SmartWatch smartWatch = new SmartWatch (valores[0], valores[1], valores[2], valores[3]);
		return smartWatch;		
	}
	
	public NoteBook lenote (){

		String [] valores = new String [4];
		String [] nomeVal = {"Nome", "marca", "modelo", "processador"};
		valores = leValores (nomeVal);

		NoteBook noteBook = new NoteBook (valores[0], valores[1], valores[2], valores[3]);
		return noteBook;		
	}
	
	private boolean intValido(String s) {
		try {
			Integer.parseInt(s); // Metodo est�tico, que tenta tranformar uma string em inteiro
			return true;
		} catch (NumberFormatException e) { // Nao conseguiu tranformar em inteiro e gera erro
			return false;
		}
	}
	
	public int retornaInteiro(String entrada) { // retorna um valor inteiro
		int numInt;

		//Enquanto nao for possivel converter o valor de entrada para inteiro, permanece no loop
		while (!this.intValido(entrada)) {
			entrada = JOptionPane.showInputDialog(null, "Valor incorreto!\n\nDigite um numero inteiro.");
		}
		return Integer.parseInt(entrada);
	}
	
	public void salvaEquipamento (ArrayList<Equipamento> equipamento){
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream 
					(new FileOutputStream("c:\\temp\\petStore.dados"));
			for (int i=0; i < equipamento.size(); i++)
				outputStream.writeObject(equipamento.get(i));
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null,"Impossível criar arquivo!");
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {  //Close the ObjectOutputStream
			try {
				if (outputStream != null) {
					outputStream.flush();
					outputStream.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	@SuppressWarnings("finally")
	public ArrayList<Equipamento> recuperaEquipamento (){
		ArrayList<Equipamento> equipamentosTemp = new ArrayList<Equipamento>();

		ObjectInputStream inputStream = null;

		try {	
			inputStream = new ObjectInputStream
					(new FileInputStream("c:\\temp\\petStore.dados"));
			Object obj = null;
			while ((obj = inputStream.readObject()) != null) {
				if (obj instanceof Equipamento) {
					equipamentosTemp.add((Equipamento) obj);
				}   
			}          
		} catch (EOFException ex) { // when EOF is reached
			System.out.println("Fim de arquivo.");
		} catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		} catch (FileNotFoundException ex) {
			JOptionPane.showMessageDialog(null,"Arquivo com equipamentos nao existe!");
			ex.printStackTrace();
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {  //Close the ObjectInputStream
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
			return equipamentosTemp;
		}
	}
	public void menuNovalojaEletronicos (){

		String menu = "";
		String entrada;
		int    opc1, opc2;

		do {
			menu = "Controle Loja de Eletronicos\n" +
					"Opções:\n" + 
					"1. Entrar Equipamentos\n" +
					"2. Exibir Equipamentos\n" +
					"3. Limpar Equipamentos\n" +
					"4. Gravar Equipamentos\n" +
					"5. Recuperar Equipamentos\n" +
					"9. Sair";
			entrada = JOptionPane.showInputDialog (menu + "\n\n");
			opc1 = this.retornaInteiro(entrada);

			switch (opc1) {
			case 1:// Entrar dados
				menu = "Entrada de Equipamentos\n" +
						"Opções:\n" + 
						"1. SmartPhone\n" +
						"2. SmartWatch\n"+
						"3. NoteBook\n";

				entrada = JOptionPane.showInputDialog (menu + "\n\n");
				opc2 = this.retornaInteiro(entrada);

				switch (opc2){
				case 1: equipamento.add((Equipamento)lephone());
				break;
				case 2: equipamento.add((Equipamento)lewatch());
				break;
				case 3: equipamento.add((Equipamento)lenote());
				break;
				default: 
					JOptionPane.showMessageDialog(null,"Equipamento para entrada não escolhido!");
				}

				break;
			case 2: // Exibir dados
				if (equipamento.size() == 0) {
					JOptionPane.showMessageDialog(null,"Entre com equipamentos primeiramente");
					break;
				}
				String dados = "";
				for (int i=0; i < equipamento.size(); i++)	{
					dados += equipamento.get(i).toString() + "---------------\n";
				}
				JOptionPane.showMessageDialog(null,dados);
				break;
			case 3: // Limpar Dados
				if (equipamento.size() == 0) {
					JOptionPane.showMessageDialog(null,"Entre com equipamentos primeiramente");
					break;
				}
				equipamento.clear();
				JOptionPane.showMessageDialog(null,"Dados LIMPOS com sucesso!");
				break;
			case 4: // Grava Dados
				if (equipamento.size() == 0) {
					JOptionPane.showMessageDialog(null,"Entre com equipamentos primeiramente");
					break;
				}
				salvaEquipamento(equipamento);
				JOptionPane.showMessageDialog(null,"Dados SALVOS com sucesso!");
				break;
			case 5: // Recupera Dados
				equipamento = recuperaEquipamento();
				if (equipamento.size() == 0) {
					JOptionPane.showMessageDialog(null,"Sem dados para apresentar.");
					break;
				}
				JOptionPane.showMessageDialog(null,"Dados RECUPERADOS com sucesso!");
				break;
			case 9:
				JOptionPane.showMessageDialog(null,"Fim do aplicativo Loja de Equipamentos");
				break;
			}
		} while (opc1 != 9);
	}
	
	public static void main (String [] args){

		NovaLojaEletronicos loja1 = new NovaLojaEletronicos ();
		loja1.menuNovalojaEletronicos();
		
	}

}
