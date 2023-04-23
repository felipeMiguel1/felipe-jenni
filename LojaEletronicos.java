package LojaEletronicos;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;





public class LojaEletronicos {
	
	private ArrayList<Equipamento> equipamento;
	
	public LojaEletronicos() {		
		this.equipamento = new ArrayList<Equipamento>();
	}
	
	public void adicionarEquipamento(Equipamento equip) {
		this.equipamento.add(equip);		
	}
	
	public void listarEquipamento() {
		for (Equipamento equip:equipamento) {
			System.out.println(equip.toString());
		}
	}
	
	public void excluirEquipamento(Equipamento equip) {
		if(this.equipamento.contains(equip)) {
			this.equipamento.remove(equip);
			System.out.println("[Equipamento " + equip.toString() + "excluido com sucesso!]\n");
		}
		else {
			System.out.println("[Esse Equipamento não existe!]\n");
		}
	}
	
	public void excluirTodosEquipamentos() {
		equipamento.clear();
		System.out.println("[Todos Equipamento excluidos com sucesso!]\n");
	}
	 
	public void salvarEquipamento() {
		ObjectOutputStream outputStream = null;
		try {
			outputStream = new ObjectOutputStream (new FileOutputStream("c:\\temp\\petStore.dados"));
			for(Equipamento equip:equipamento) {
				outputStream.writeObject(equip);
			}
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
				if (outputStream != null ) {
					outputStream.flush();
					outputStream.close();
				}
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	public void recuperarEquipamento() {
		ObjectInputStream inputStream = null;
		try {
			inputStream	= new ObjectInputStream (new FileInputStream ("c:\\temp\\petStore.dados"));
			Object obj = null;
			while((obj = inputStream.readObject ()) != null) {
				if (obj instanceof SmartPhone)  
					this.equipamento.add((SmartPhone)obj);
				else if (obj instanceof SmartWatch)  
					this.equipamento.add((SmartWatch)obj);
				else if (obj instanceof NoteBook)  
					this.equipamento.add((NoteBook)obj);
				
			}
		}catch (EOFException ex) {     // when EOF is reached
			System.out.println ("End of file reached");
		}catch (ClassNotFoundException ex) {
			ex.printStackTrace();
		}catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}catch (IOException ex) {
			ex.printStackTrace();
		}finally{
			try {
				if (inputStream != null ) {
					inputStream.close();
					System.out.println("Equipamentos recuperados com sucesso!\n");
				}
			}catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		LojaEletronicos loja1  = new LojaEletronicos();

		SmartPhone celular1    = new SmartPhone("celular1","Sansung", "S20", 2);
		SmartPhone celular2 = new SmartPhone("celular2","apple", "iphone11", 1);
		SmartWatch  relogio1      = new SmartWatch ("relogio1","apple", "apple watch", "pulseira de borracha");
		SmartWatch  relogio2     = new SmartWatch ("relogio2","china", "wp32a5", "pulseira de couro");
		NoteBook  note1     = new NoteBook ("note1", "Dell", "Dell gamer","i7");
		NoteBook  note2     = new NoteBook ("note2", "positivo", "positivo note2","celeron");
		
		
		loja1.adicionarEquipamento(celular1);
		loja1.adicionarEquipamento(celular2);
		loja1.adicionarEquipamento(relogio1);
		loja1.adicionarEquipamento(relogio2);
		loja1.adicionarEquipamento(note1);
		loja1.adicionarEquipamento(note2);
		loja1.listarEquipamento();
		loja1.salvarEquipamento();
		loja1.excluirEquipamento(note2);
		loja1.listarEquipamento();
		loja1.excluirTodosEquipamentos();
		loja1.listarEquipamento();
		loja1.recuperarEquipamento();
		loja1.listarEquipamento();
	}


}
