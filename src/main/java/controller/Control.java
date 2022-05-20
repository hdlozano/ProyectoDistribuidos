package controller;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.ComprarM;
import model.Pasaporte;
import model.User;
public class Control {
	int id;
	final String URL = "jdbc:mysql://Localhost:3306/proyecto?useSSL=false&useTimezone=true&serverTimezone=UTC";
	PreparedStatement sentencia;
	ResultSet rs;
	Statement statement = null;
	Conexion conexion;
	Connection con;
	
	User user;
	ComprarM compra = new ComprarM();
	GestionDB db;
	private Pasaporte pasaporte1;
	
	public Control() {
		
	}

	public void calculateItem1(int quantityInem1) {
		while (quantityInem1!=0) {
			int cons = busquedaIdBrazalete();//realizar consulta a la bd para ver el consecutivo
			pasaporte1 = new Pasaporte("nino", cons, "monta�a", "electronico", "chocones", "barco");
			registroventa(pasaporte1, this.id);
			quantityInem1--;
		}		
	}
	
	public void calculateItem2(int quantityInem2) {
		while (quantityInem2!=0) {
			//db.establecerConexion();
			compra.compraPas1(user);
			quantityInem2--;
		}
	}
	
	public void calculateItem3(int quantityInem3) {
		while (quantityInem3!=0) {
			
			//db.establecerConexion();
			compra.compraPas1(user);
			quantityInem3--;
		}
	}

	public void records(int id, String name, String email, String password, String birtday) {
		this.id=id;
		System.out.println(this.id);
		conexion = new Conexion(URL, "root", "toor");
		try {
			conexion.conectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con=conexion.getConexion();
		user = new User(id, name, email, password, birtday);
		//db.establecerConexion();
		SaveUser(user);
	}
	
	public void SaveUser(User user) {
		try {

			sentencia = con.prepareStatement("INSERT INTO usuario(id,name_user,email,password,birthday) VALUE(?,?,?,?,?)");
			sentencia.setLong(1, user.getId());
			sentencia.setString(2, user.getName());
			sentencia.setString(3, user.getEmail());
			sentencia.setString(4, user.getPassword());
			sentencia.setString(5, user.getBirtday());
			sentencia.executeUpdate();
			conexion.desconectar();
			System.out.println("Registros guardados");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void registroventa(Pasaporte pasaporte,int id) {
		System.out.println(this.id);
		try {
			sentencia = con.prepareStatement("INSERT INTO venta(name_pasport,usuario_id) VALUE(?,?)");
			sentencia.setString(1, pasaporte.getNamePasport());
			sentencia.setInt(2,this.id);
			sentencia.executeUpdate();
			conexion.desconectar();
			System.out.println("Registros guardados");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public int busquedaIdBrazalete() {
		conexion = new Conexion(URL, "root", "toor");
		try {
			conexion.conectar();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		con=conexion.getConexion();
		int numeroManilla=0;
		String query = "select id_Passport from venta where id_Passport=(select max(id_Passport) from venta)";
		try {
			statement = con.createStatement();
			rs = statement.executeQuery(query);
			while(rs.next()) {
				System.out.println(rs.getString("id_Passport"));
				numeroManilla = Integer.parseInt(rs.getString("id_Passport"));
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return numeroManilla;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	

}
