package model;

import java.util.List;

import Entidades.Funcionario;
import database.Querys;

public class databaseManipulation {
	public static Funcionario addFuncionario(Funcionario func) {
		return Querys.addFuncionario(func);
	}
	public static void updateFuncionario(Funcionario func) {
		Querys.atualizarFuncionario(func);
	}
	public static void removeFuncionario(Funcionario func) {
		Querys.deletarFuncionario(func);
	}
	public static List<Funcionario> returnAll(){
		return Querys.returnAll();
	}
}
