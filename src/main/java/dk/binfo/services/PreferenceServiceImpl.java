package dk.binfo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

@Service("preferenceService")
public class PreferenceServiceImpl implements PreferenceService{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PreferenceServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public ArrayList<Integer> getPreferences(int userId) {
        ArrayList<Integer> apartmentIdList = new ArrayList<Integer>();
        try {
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("SELECT " +
                    "apartment_id FROM binfo_db.user_preferences WHERE user_id = ?");
            sql.setInt(1, userId);
            ResultSet result = sql.executeQuery();
            while (result.next()) {
                apartmentIdList.add(result.getInt("apartment_id"));
            }
            sql.close();
            result.close();
            return apartmentIdList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Integer> setPreferences(int userId) {

        return null;
    }
}
