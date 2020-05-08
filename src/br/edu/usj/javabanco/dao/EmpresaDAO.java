package br.edu.usj.javabanco.dao;

import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import br.edu.usj.javabanco.entity.Empresa;

public class EmpresaDAO {

	public List<Empresa> listarEmpresas() {

		List<Empresa> empresas = new ArrayList<Empresa>();

		Connection conexao = Conexao.getConexao();

		try {
			Statement stm = conexao.createStatement();
			String sql = "SELECT * FROM EMPRESA";
			ResultSet rs = stm.executeQuery(sql);
			//
			while (rs.next()) {
				Empresa empresa = new Empresa();
				empresa.setId(rs.getInt("id"));
				empresa.setNome(rs.getString("nome"));
				empresa.setRamoAtuacao(rs.getString("ramo_atuacao"));
				empresa.setAnoFundacao(rs.getInt("ano_fundacao"));
				empresa.setCapital(rs.getFloat("capital"));
				empresas.add(empresa);
			}

		} catch (SQLException e) {

			System.err.println("Erro ao criar as empresas!");
			e.printStackTrace();
		}

		return empresas;
	}

	public boolean gravarEmpresa(Empresa e) {
		int resultado = 0;
		Connection conexao = Conexao.getConexao();
		String sql = "INSERT INTO EMPRESA VALUES(?,?,?,?,?)";

		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			//
			stm.setInt(1, e.getId());
			stm.setString(2, e.getNome());
			stm.setInt(3, e.getAnoFundacao());
			stm.setString(4, e.getRamoAtuacao());
			stm.setFloat(5, e.getCapital());

			resultado = stm.executeUpdate();

		} catch (SQLException e1) {
			System.err.println("Erro ao gravar a empresa!");
			e1.printStackTrace();
		}
		return resultado > 0;
	}

	public boolean excluirEmpresa(int codigo) {

		int resultado = 0;
		Connection conexao = Conexao.getConexao();
		String sql = "DELETE FROM EMPRESA WHERE id = ?";
		try {
			PreparedStatement stm = conexao.prepareStatement(sql);
			stm.setInt(1, codigo);
			resultado = stm.executeUpdate();
		} catch (SQLException e1) {

			System.err.println("Erro ao excluir a empresa!");
			e1.printStackTrace();
		}
		return resultado > 0;
	}
}
