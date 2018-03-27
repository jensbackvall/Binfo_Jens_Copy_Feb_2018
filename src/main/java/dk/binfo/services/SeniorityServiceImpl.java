package dk.binfo.services;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


@Service("seniority")
public class SeniorityServiceImpl implements SeniorityService {

    /**
     * A pointer we can use to refer to springs JdbcTemplate
     * so we can use the same database connection and can avoid
     * creating multiple connections that overlap eachother
     *
     * @author 		jensbackvall
     */
    private final JdbcTemplate jdbcTemplate;

    /**
     * An autowired method that gets the jdbcTemplate
     * from spring so we can use the same database
     * connection and can avoid creating multiple
     * connections that overlap each other
     *
     * @param  		jdbcTemplate The length of the waitinglist to be generated
     * @author 		jensbackvall
     */
    @Autowired
    public SeniorityServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Returns an int containing the seniority for
     * a specific list_priority of a user with a specific email.
     *
     * @param  		id The Id of the user currently logged in
     * @param  		list_priority The priority of the list to be generated
     * @return      ArrayList containing emails on the priority list sorted by seniority
     * @author 		jensbackvall
     */
/*    public Timestamp getSeniority(int id, int list_priority) {
        if (id < 0||list_priority < 1){
            return null;
        }
        Timestamp seniority = null;
        try {
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("SELECT seniority " +
                    "FROM list_and_seniority WHERE user_id=? AND list_priority=?;");
            sql.setInt(1, id);
            sql.setInt(2, list_priority);
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                seniority = result.getTimestamp("seniority");
                sql.close();
                result.close();
                return seniority;
            } else {
                return null;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return seniority;
    }*/

    public int getSeniority(int id, int list_priority) {
        if (id < 0||list_priority < 1){
            return 0;
        }
        int seniority = 0;
        try {
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("select count(*)\n" +
                    "from list_and_seniority\n" +
                    "where list_priority = ? and seniority <= (select seniority from list_and_seniority where user_id" +
                    " = ? and list_priority = ?)");
            sql.setInt(2, id);
            sql.setInt(1, list_priority);
            sql.setInt(3, list_priority);
            ResultSet result = sql.executeQuery();
            if (result.next()) {
                seniority = result.getInt("count(*)");
                sql.close();
                result.close();
                return seniority;
            } else {
                return 0;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return seniority;
    }
}
