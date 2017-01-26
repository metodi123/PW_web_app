package pw.web.app.service;

import java.security.NoSuchAlgorithmException;

import org.springframework.stereotype.Service;

import pw.web.app.dao.AdminDAO;
import pw.web.app.exception.InvalidUserParametersException;
import pw.web.app.model.Admin;
import pw.web.app.model.UserType;

@Service
public class UpdateProfileSettingsService {

	public static void updateUserPassword(String username, String password, UserType userType) throws InvalidUserParametersException, NoSuchAlgorithmException {
		
		UserParametersValidationService.validateParameter(password, UserParametersValidationService.LONG_PARAMETERS_MAX_LENGTH);
		
		String hashedPassword = null;
		
		hashedPassword = PasswordProcessingService.getHashedPassword(password, username);
		
		if(userType == UserType.Admin) {
			Admin admin = new Admin();
			
			AdminDAO adminDAO = new AdminDAO();
			
			admin = adminDAO.getUser(username);

			admin.setPassword(hashedPassword);
			
			adminDAO.updateUser(admin);
		}
	}
	
}
