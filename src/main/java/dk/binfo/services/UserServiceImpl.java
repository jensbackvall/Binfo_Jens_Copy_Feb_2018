package dk.binfo.services;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.transaction.Transactional;

import dk.binfo.models.Role;
import dk.binfo.models.User;
import dk.binfo.repositories.RoleRepository;
import dk.binfo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 *  @author Patrick Klæbel
 *  @author Jens Bäckvall
 *  @author Steen Petersen
 *  @author Morten Olsen
 *
 */
@Service("userService")
public class UserServiceImpl implements UserService, UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public UserServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

	@Override
	public User findUserByEmail(String email) {

		return userRepository.findByEmail(email);
	}

	public User findUserById(int user_id) {
        try {
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("SELECT email FROM " +
                    "`user` WHERE user_id = ?;");
            sql.setInt(1, user_id);
            ResultSet result = sql.executeQuery();
            while (result.next()){
                String email = result.getString("email");
                User user = findUserByEmail(email);
                System.out.println(user);
                sql.close();
                result.close();
                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


	@Override
	@Transactional
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void register(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(true);
		user.setPhoneNumber(user.getPhoneNumber());
		Role userRole = roleRepository.findByRole("user");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
	}

	@Override
	@Transactional
	public User deleteUser(String email) {
		User deletedUser = userRepository.findByEmail(email);
		deletedUser.setRoles(null); // Deletes this user in user_role table
		userRepository.delete(deletedUser);
		return deletedUser;
	}

	@Override
	@Transactional
	public User update(User user){
		User updatedUser = userRepository.findByEmail(user.getEmail());
        int id = updatedUser.getUserId();
        updatedUser.setEmail(user.getEmail());
		updatedUser.setName(user.getName());
		updatedUser.setLastName(user.getLastName());
		updatedUser.setAddress(user.getAddress());
		updatedUser.setPostCode(user.getPostCode());
		updatedUser.setPhoneNumber(user.getPhoneNumber());
		updatedUser.setActive(user.isActive());
        updatedUser.setConnectList(user.isConnectList());
        updatedUser.setInternalList(user.isInternalList());
        updatedUser.setFamilyList(user.isFamilyList());
        updatedUser.setExternalList(user.isExternalList());
        if (!updatedUser.isConnectList()) {
            purgeUserSeniority(id, 1);
        }
        if (!updatedUser.isInternalList()) {
            purgeUserSeniority(id, 2);
        }
        if (!updatedUser.isFamilyList()) {
            purgeUserSeniority(id, 3);
        }
        if (!updatedUser.isExternalList()) {
            purgeUserSeniority(id, 4);
        }
		return updatedUser;
	}


	@Override
	public void adminRegisterUser(User user) {
		Random ran = new Random();
//		String Password = generateString(ran,"qwertyuiopasdfghjklzxcvbnm",8);
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setActive(user.isActive());
		user.setPhoneNumber(user.getPhoneNumber());
		Role userRole = roleRepository.findByRole("user");
		user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		userRepository.save(user);
        user.setConnectList(user.isConnectList());
        user.setInternalList(user.isInternalList());
        user.setFamilyList(user.isFamilyList());
        user.setExternalList(user.isExternalList());
        if (user.isConnectList()) {
            setUserSeniority(user.getUserId(), 1);
        }
        if (user.isInternalList()) {
            setUserSeniority(user.getUserId(), 2);
        }
        if (user.isFamilyList()) {
            setUserSeniority(user.getUserId(), 3);
        }
        if (user.isExternalList()) {
            setUserSeniority(user.getUserId(), 4);
        }
	}

	@Override
	@Transactional
	public User updateUserSettings(User user){
		User updateUser = userRepository.findByEmail(user.getEmail());
		if(user.getPassword().equalsIgnoreCase("") && user.getPhoneNumber() != null) {
			updateUser.setPhoneNumber(user.getPhoneNumber());
		}
		if(user.getPhoneNumber() == null || user.getPhoneNumber().equalsIgnoreCase("") && user.getPassword() != null) {
			updateUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		}
		if(user.getPhoneNumber() != null && user.getPassword() != null) {
			updateUser.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
			updateUser.setPhoneNumber(user.getPhoneNumber());
		}
		return updateUser;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(userName);
		List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
		return buildUserForAuthentication(user, authorities);
	}

	public void setUserSeniority(int id, int listPriority) {
            Calendar calendar = Calendar.getInstance();
            java.util.Date now = calendar.getTime();
            Timestamp thisDate = new Timestamp(now.getTime());
        try {
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("INSERT INTO " +
                    "list_and_seniority (user_id, list_priority, seniority) VALUES (?, ?, ?)");
            sql.setInt(1, id);
            sql.setInt(2, listPriority);
            sql.setTimestamp(3, thisDate);
            sql.executeUpdate();
            sql.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}

	public void purgeUserSeniority(int id, int listPriority) {
        try {
            PreparedStatement sql = jdbcTemplate.getDataSource().getConnection().prepareStatement("DELETE FROM " +
                    "list_and_seniority WHERE user_id = ? AND list_priority = ?");
            sql.setInt(1, id);
            sql.setInt(2, listPriority);
            sql.executeUpdate();
            sql.close();
            PreparedStatement sql2 = jdbcTemplate.getDataSource().getConnection().prepareStatement("UPDATE user SET " +
                    " ? = 0 WHERE user_id = ?");
            sql2.setInt(1, listPriority);
            sql2.setInt(2, id);
            sql2.executeUpdate();
            sql2.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
		for (Role role : userRoles) {
			roles.add(new SimpleGrantedAuthority(role.getRole()));
		}

		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>(roles);
		return grantedAuthorities;
	}

	private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.isActive(), true, true, true, authorities);
	}


	public static String generateString(Random rng, String characters, int length)
	{
		char[] text = new char[length];
		for (int i = 0; i < length; i++)
		{
			text[i] = characters.charAt(rng.nextInt(characters.length()));
		}
		return new String(text);
	}
}
