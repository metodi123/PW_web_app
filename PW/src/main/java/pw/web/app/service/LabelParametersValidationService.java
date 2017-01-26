package pw.web.app.service;

import org.springframework.stereotype.Service;

import pw.web.app.exception.InvalidLabelParametersException;
import pw.web.app.model.Label;

@Service
public class LabelParametersValidationService {

	public static final int MAX_LENGTH = 20;
	
	public static void validateLabelParameters(Label label) throws InvalidLabelParametersException {
		validateParameter(label.getText(), MAX_LENGTH);
		validateParameter(label.getColor().getName(), MAX_LENGTH);
		validateParameter(label.getColor().getValue(), MAX_LENGTH);
	}

	public static void validateParameter(String parameter, int maxLength) throws InvalidLabelParametersException {
		if(parameter.length() > maxLength) {
			throw new InvalidLabelParametersException("Invalid parameter length");
		}
		if(parameter.isEmpty()) {
			throw new InvalidLabelParametersException("Empty parameter entered");
		}
	}
	
}
