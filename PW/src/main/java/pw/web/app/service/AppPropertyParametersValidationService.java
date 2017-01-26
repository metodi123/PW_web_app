package pw.web.app.service;

import org.springframework.stereotype.Service;

import pw.web.app.exception.InvalidAppPropertyParametersException;
import pw.web.app.model.AppProperty;

@Service
public class AppPropertyParametersValidationService {

	public static final int SHORT_MAX_LENGTH = 20;
	public static final int LONG_MAX_LENGTH = 10000;
	
	public static void validateAppPropertyParameters(AppProperty appProperty) throws InvalidAppPropertyParametersException {
		validateParameter(appProperty.getKey(), SHORT_MAX_LENGTH, false);
		validateParameter(appProperty.getValue(), LONG_MAX_LENGTH, true);
	}

	public static void validateParameter(String parameter, int maxLength, boolean ignoreIsEmpty) throws InvalidAppPropertyParametersException {
		if(parameter.length() > maxLength) {
			throw new InvalidAppPropertyParametersException("Invalid parameter length");
		}
		if(ignoreIsEmpty == false) {
			if(parameter.isEmpty()) {
				throw new InvalidAppPropertyParametersException("Empty parameter entered");
			}
		}
	}
	
}
