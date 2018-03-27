package dk.binfo.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import org.codehaus.groovy.runtime.powerassert.SourceText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * A service we can use to make a waitinglist
 * the waitinglist generated can be based on
 * a priority or an apartment
 *
 * The waitinglist length can also be based on a
 * specific length input
 *
 * @author 		Stonie
 */
@Service("waitinglist")
public class Waitinglist {

	/**
	 * A pointer we can use to refer to springs JdbcTemplate
	 * so we can use the same database connection and can avoid
	 * creating multiple connections that overlap eachother
	 *
	 * @author 		Stonie
	 */
	private final JdbcTemplate jdbcTemplate;

	/**
	 * An autowired method that gets the jdbcTemplate
	 * from spring so we can use the same database
	 * connection and can avoid creating multiple
	 * connections that overlap eachother
	 *
	 * @param  		jdbcTemplate The length of the waitinglist to be generated
	 * @author 		Stonie
	 */
    @Autowired
    public Waitinglist(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	/**
	 * The main method, used to get an Arraylist
	 * containing sorted results based on both
	 * length and ApartmentId.
	 * <p>
	 * It uses almost all methods in the class to
	 * do this in the most optimized way.
	 *
	 * @param  		length The length of the waitinglist to be generated
	 * @param  		ApartmentId The id of the apartment
	 * @return      ArrayList containing emails on the waitinglist
	 * @author 		Stonie
	 */
	public ArrayList<String> getWaitinglist(int length,int ApartmentId){
		if(getPreferences(length,ApartmentId)==null){
			return null;
		}
		return checkPriority(length,getPreferences(length,ApartmentId),ApartmentId);
	}

	/**
	 * Returns the emails of neighbours of the ApartmentId
	 * using sql to get the apartments around the apartment
	 * and then uses sql again to get the emails of said
	 * apartments.
	 *
	 * @param  		ApartmentId The id of the apartment
	 * @return      ArrayList containing emails of neighbours
	 * @author 		Stonie
	 */
	public ArrayList<String> getNeighbourEmails(int ApartmentId){
		ArrayList<Integer> neighboursID = new ArrayList<Integer>();
		try {
			PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("SELECT neighbour FROM `apartment_neighbours` WHERE id_apartment=?;");
			sql.setInt(1, ApartmentId);
			ResultSet result = sql.executeQuery();
			while (result.next()) {
				int neighbour = result.getInt("neighbour");
				neighboursID.add(neighbour);
			}
			sql.close();
			result.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		ArrayList<String> emailsunsorted = new ArrayList<String>();
		if (neighboursID.isEmpty()){
			return emailsunsorted;
		}
		for (int neighbourID : neighboursID){
			try {
				PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("SELECT email FROM `user` WHERE my_apartment=?;");
				sql.setInt(1, neighbourID);
				ResultSet result = sql.executeQuery();
				if (result.next()){
					String email = result.getString("email");
					emailsunsorted.add(email);
				}
				sql.close();
				result.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (emailsunsorted.isEmpty()){
			return emailsunsorted;
		}
		ArrayList<String> emailssorted = new ArrayList<String>();
			try {
				String SQLString = "SELECT email FROM `list_and_seniority` WHERE email=? AND list_priority=1";
				for (int i = 1;i<emailsunsorted.size();i++){
					SQLString += " OR email=? AND list_priority=1";
				}
				String SQLEND = " ORDER BY seniority ASC;";
				SQLString += SQLEND;
				PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQLString);
				for (int i = 0;i<emailsunsorted.size();i++){
					sql.setString(i+1, emailsunsorted.get(i));
				}
				ResultSet result = sql.executeQuery();
				while (result.next()){
					String email = result.getString("email");
					emailssorted.add(email);
				}
				sql.close();
				result.close();
				return emailssorted;
			} catch (Exception e){
					e.printStackTrace();
			}
		return null;
	}

	/**
	 * Returns the emails on waitinglist for the ApartmentId
	 * using sql to get them.
	 * The list will not be sorted.
	 * <p>
	 * It will NOT contain the emails from the neighbour
	 * waitinglist, use the getNeighbourEmails method for that
	 *
	 * @param  		length The length of the waitinglist to be generated
	 * @param  		ApartmentId The id of the apartment
	 * @return      ArrayList containing emails on the waitinglist
	 * @author 		Stonie
	 */
	public ArrayList<String> getPreferences(int length,int ApartmentId){
		ArrayList<String> pref = new ArrayList<String>();
		try {
				PreparedStatement sql = jdbcTemplate.getDataSource().getConnection()
					.prepareStatement("SELECT email FROM `user_preferences` WHERE id_apartment=?;");
				sql.setInt(1, ApartmentId);
				ResultSet result = sql.executeQuery();
				if(!result.next()){
					sql.close();
					result.close();
					return null;
				}
				
				do {
					String email = result.getString("email");
					pref.add(email);
					if (pref.size() >= length){
						sql.close();
						result.close();
						return pref;
					}
				} while (result.next());
				
				sql.close();
				result.close();
				return pref;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	/**
	 * Sorts the emails based on seniority
	 * and returns the sorted list as an ArrayList
	 *
	 * @param  		length The length of the waitinglist to be generated
	 * @param  		emails An arraylist containing the emails to be sorted
	 * @param  		ApartmentId The id of the apartment
	 * @return      ArrayList containing emails sorted by seniority
	 * @author 		Stonie
	 */
	public ArrayList<String> checkPriority(int length, ArrayList<String> emails, int ApartmentId){

	    ArrayList<String> emailssorted = getNeighbourEmails(ApartmentId);

        try {
            String SQLString = "SELECT email FROM `list_and_seniority` WHERE email=? AND " +
                    "list_priority=1";
            for (int i = 1;i<emails.size();i++){
                SQLString += " OR email=? AND list_priority=1";
            }
            String SQLEND = " ORDER BY seniority ASC;";
            SQLString += SQLEND;
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQLString);
            for (int i = 0;i<emails.size();i++){
                sql.setString((i+1), emails.get(i));
            }
            ResultSet result = sql.executeQuery();
            while (result.next()){
                String email = result.getString("email");
                emailssorted.add(email);
            }
            sql.close();
            result.close();
        } catch (Exception e){
            e.printStackTrace();
        }

        try {
					String SQLString = "SELECT email FROM `list_and_seniority` WHERE email=? AND " +
                            "list_priority=2";
					for (int i = 1;i<emails.size();i++){
						SQLString += " OR email=? AND list_priority=2";
					}
					String SQLEND = " ORDER BY seniority ASC;";
					SQLString += SQLEND;
					PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQLString);
					for (int i = 0;i<emails.size();i++){
						sql.setString((i+1), emails.get(i));
					}
					ResultSet result = sql.executeQuery();
					while (result.next()){
						String email = result.getString("email");
						emailssorted.add(email);
					}
					sql.close();
					result.close();
				} catch (Exception e){
					e.printStackTrace();
				}
		try {
			String SQLString = "SELECT email FROM `list_and_seniority` WHERE email=? AND list_priority=3";
			for (int i = 1;i<emails.size();i++){
				SQLString += " OR email=? AND list_priority=3";
			}
			String SQLEND = " ORDER BY seniority ASC;";
			SQLString += SQLEND;
			PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQLString);
			for (int i = 0;i<emails.size();i++){
				sql.setString((i+1), emails.get(i));
			}
			ResultSet result = sql.executeQuery();
			while (result.next()){
				String email = result.getString("email");
				emailssorted.add(email);
			}
			sql.close();
			result.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		try {
			String SQLString = "SELECT email FROM `list_and_seniority` WHERE email=? AND list_priority=4";
			for (int i = 1;i<emails.size();i++){
				SQLString += " OR email=? AND list_priority=4";
			}
			String SQLEND = " ORDER BY seniority ASC;";
			SQLString += SQLEND;
			PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement(SQLString);
			for (int i = 0;i<emails.size();i++){
				sql.setString((i+1), emails.get(i));
			}
			ResultSet result = sql.executeQuery();
			while (result.next()){
				String email = result.getString("email");
				emailssorted.add(email);
			}
			sql.close();
			result.close();
		} catch (Exception e){
			e.printStackTrace();
		}
		int size = emailssorted.size();
		if (length<size){
			emailssorted.subList(length, size).clear();
		}
		return emailssorted;
	}

	/**
	 * Returns an ArrayList containing all the emails
	 * on a specific waitinglist based on priority and
	 * sorted by seniority.
	 *
	 * @param  		length The length of the waitinglist to be generated
	 * @param  		priority the priority of the list to be generated
	 * @return      ArrayList containing emails on the priority list sorted by seniority
	 * @author 		Stonie
	 */
	public ArrayList<Integer> getSingleWaitinglist(int length,int priority){
		if (priority>4||priority<1){
			return null;
		}
		ArrayList<Integer> id_sorted = new ArrayList<Integer>();
		try {
			PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("SELECT user_id FROM" +
					"`list_and_seniority` WHERE list_priority=? ORDER BY seniority ASC;");
			sql.setInt(1, priority);
			ResultSet result = sql.executeQuery();
			while (result.next()){
				int user_id = result.getInt("user_id");
                System.out.println("\n" + user_id + "added to list\n");
				id_sorted.add(user_id);
			}
			sql.close();
			result.close();
			return id_sorted;
		} catch (Exception e){
				e.printStackTrace();
		}
		return id_sorted;
	}
}
