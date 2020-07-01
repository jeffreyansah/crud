package cruddao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import model.Stuff;

public class DaoStuff implements StuffDao {

	private DaoStuff() {

	}

	private static class SingletonHelper {
		private static final DaoStuff INSTANCE = new DaoStuff();
	}

	public static DaoStuff getInstance() {
		return SingletonHelper.INSTANCE;
	}

	@Override
	public Optional<Stuff> find(String id) throws SQLException {
		String sql = "SELECT STUFF_ID,NAME,DESCRIPTION,QUANTITY,LOCATION From STUFF Where STUFF_ID=?";
		int STUFF_ID = 0;
		int QUANTITY = 0;
		String NAME = " ";
		String DESCRIPTION = " ";
		String LOCATION = " ";
		Connection conn = DataSourceFactory.getInstance().getOracleDBConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, id);
		ResultSet resultSet = statement.executeQuery();
		if (resultSet.next()) {
			STUFF_ID = resultSet.getInt("STUFF_ID");
			NAME = resultSet.getString("NAME");
			DESCRIPTION = resultSet.getString("DESCRIPTION");
			QUANTITY = resultSet.getInt("QUANTITY");
			LOCATION = resultSet.getString("LOCATION");
		}
		return Optional.of(new Stuff(STUFF_ID, NAME, DESCRIPTION, QUANTITY, LOCATION));
	}

	@Override
	public List<Stuff> findAll() throws SQLException {
		List<Stuff> listStuff = new ArrayList<>();
		String sql = "SELECT STUFF_ID,NAME, DESCRIPTION, QUANTITY,LOCATION From STUFF Where STUFF_ID=?";
		Connection conn = DataSourceFactory.getInstance().getOracleDBConnection();
		Statement statement = conn.createStatement();
		ResultSet resultSet = statement.executeQuery(sql);
		while (resultSet.next()) {
			int id = resultSet.getInt("STUFF_ID");
			String name = resultSet.getString("NAME");
			String description = resultSet.getString("DESCRIPTION");
			int quantity = resultSet.getInt("QUANTITY");
			String location = resultSet.getString("LOCATION");
			Stuff stuff = new Stuff(id, name, description, quantity, location);
			listStuff.add(stuff);
		}

		return listStuff;
	}

	@Override
	public boolean save(Stuff o) throws SQLException {
		String sql = "INSERT into STUFF(NAME,DESCRIPTION,QUANTITY,LOCATION) VALUES( ?, ?, ?, ?) ";
		boolean rowInserted = false;
		Connection conn = DataSourceFactory.getInstance().getOracleDBConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, o.getName());
		statement.setString(2, o.getDescription());
		statement.setInt(3, o.getQuantity());
		statement.setString(4, o.getLocation());
		rowInserted = statement.executeUpdate() > 0;

		return rowInserted;
	}

	@Override
	public boolean update(Stuff o) throws SQLException {

		String sql = "UPDATE STUFF SET NAME =?, DESCRIPTION=?, QUANTITY=?, LOCATION=?";
		sql += "  WHERE STUFF_ID=?";
		boolean rowUpdateted = false;
		Connection conn = DataSourceFactory.getInstance().getOracleDBConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setString(1, o.getName());
		statement.setString(2, o.getDescription());
		statement.setInt(3, o.getQuantity());
		statement.setString(4, o.getLocation());
		rowUpdateted = statement.executeUpdate() > 0;

		return rowUpdateted;
	}

	@Override
	public boolean delete(Stuff o) throws SQLException {

		String sql = "DELETE FROM STUFF where STUFF_ID=?";
		boolean rowDeleted = false;
		Connection conn = DataSourceFactory.getInstance().getOracleDBConnection();
		PreparedStatement statement = conn.prepareStatement(sql);
		statement.setInt(1, o.getId());
		rowDeleted = statement.executeUpdate() > 0;
		return rowDeleted;
	}
}
