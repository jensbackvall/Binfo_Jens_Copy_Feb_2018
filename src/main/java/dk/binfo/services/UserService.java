package dk.binfo.services;

import dk.binfo.models.User;

import java.util.List;
/**
 *  @author Patrick Klæbel
 *  @author Jens Bäckvall
 *  @author Steen Petersen
 *  @author Morten Olsen
 *
 */
public interface UserService {
	User findUserByEmail(String email);
	User findUserById(int user_id);
	User updateUserSettings(User user);
	User deleteUser(String email);
	User update(User user);
//	User updateSeniority(User user);
	List<User> findAll();
	void setUserSeniority(int id, int listPriority);
	void purgeUserSeniority(int id, int listPriority);
	void register(User user);
	void adminRegisterUser(User user);
}
