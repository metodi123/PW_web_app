package pw.web.app.dao;

import java.util.List;

import pw.web.app.model.Label;

public interface LabelDataAccess {
	public Label getLabel(int id) throws Exception;
	
	public List<Label> getAllLabels() throws Exception;

	public void createLabel(Label label) throws Exception;
	
	public void updateLabel(Label label) throws Exception;
	
	public void deleteLabel(int id) throws Exception;
}
