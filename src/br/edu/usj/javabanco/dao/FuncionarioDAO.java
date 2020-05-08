package br.edu.usj.javabanco.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import br.edu.usj.javabanco.entity.Empresa;
import br.edu.usj.javabanco.entity.Funcionario;

public class FuncionarioDAO {

	public List<Funcionario> listarFuncionarios() {
		List<Funcionario> lista = new ArrayList<Funcionario>();
		Connection conexao = Conexao.getConexao();
		try {
			Statement stm = conexao.createStatement();
			String sql = "SELECT * from FUNCIONARIO f JOIN EMPRESA e \n" + "ON f.EMPRESA=e.ID ORDER BY f.ID";
			ResultSet rs = stm.executeQuery(sql);

			while (rs.next()) {
				Funcionario funcionario = new Funcionario();
				funcionario.setId(rs.getInt("f.id"));
				funcionario.setNome(rs.getString("f.nome"));
				funcionario.setNascimento(rs.getDate("f.nascimento"));
				funcionario.setAnoInicioTrabalho(rs.getInt("f.ano_inicio_trabalho"));
				funcionario.setFuncao(rs.getString("f.funcao"));
				Empresa empresa = new Empresa();
				empresa.setId(rs.getInt("e.id"));
				empresa.setNome(rs.getString("e.nome"));
				empresa.setRamoAtuacao(rs.getString("e.ramo_atuacao"));
				empresa.setAnoFundacao(rs.getInt("e.ano_fundacao"));
				empresa.setCapital(rs.getFloat("e.capital"));
				funcionario.setEmpresa(empresa);

				lista.add(funcionario);
			}

		} catch (SQLException e) {
			System.err.println("Erro ao listar os funcionários!");
			e.printStackTrace();
		}

		return lista;
	}

	public boolean gravarFuncionario(Funcionario f) {
		int resultado = 0;
		Connection conexao = Conexao.getConexao();
		String sql = "INSERT INTO FUNCIONARIO VALUES(?,?,?,?,?,?)";
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, f.getId());
			stm.setString(2, f.getNome());
			stm.setDate(3, f.getNascimento());
			stm.setInt(4, f.getAnoInicioTrabalho());
			stm.setString(5, f.getFuncao());
			stm.setInt(6, f.getEmpresa().getId());

			resultado = stm.executeUpdate();
		} catch (SQLException e1) {
			System.err.println("Erro ao gravar funcionario!");
			e1.printStackTrace();
		}
		return resultado > 0;
	}

	public boolean excluirFuncionario(int codigo) {
		int resultado = 0;
		Connection conexao = Conexao.getConexao();
		String sql = "DELETE FROM FUNCIONARIO WHERE id = ?";
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, codigo);
			resultado = stm.executeUpdate();
		} catch (SQLException e1) {
			System.err.println("Erro ao excluir o funcionario!");
			e1.printStackTrace();
		}
		return resultado > 0;
	}
}
