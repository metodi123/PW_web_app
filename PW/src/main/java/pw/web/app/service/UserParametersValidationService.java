package pw.web.app.service;

import org.springframework.stereotype.Service;

import pw.web.app.exception.InvalidUserParametersException;
import pw.web.app.model.Admin;

@Service
public class UserParametersValidationService {

	public static final int LONG_PARAMETERS_MAX_LENGTH = 35;
	public static final int SHORT_PARAMETERS_MAX_LENGTH = 25;
		
	public static void validateUserParameters(Admin admin, boolean ignoreUsernameAndPasswordValidation) throws InvalidUserParametersException {
		if(ignoreUsernameAndPasswordValidation == false) {
			validateParameter(admin.getUsername(), LONG_PARAMETERS_MAX_LENGTH);
			validateParameter(admin.getPassword(), LONG_PARAMETERS_MAX_LENGTH);
		}
		validateParameter(admin.getFirstName(), SHORT_PARAMETERS_MAX_LENGTH);
		validateParameter(admin.getLastName(), SHORT_PARAMETERS_MAX_LENGTH);
	}
	
	public static void validateParameter(String parameter, int maxLength) throws InvalidUserParametersException {
		if(parameter.length() > maxLength) {
			throw new InvalidUserParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidUserParametersException("Empty parameter entered");
		}
	}
	
}
