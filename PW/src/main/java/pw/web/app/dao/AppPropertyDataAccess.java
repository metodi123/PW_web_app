package pw.web.app.dao;

import java.util.List;

import pw.web.app.model.AppProperty;

public interface AppPropertyDataAccess {
	public AppProperty getAppProperty(String key) throws Exception;
	
	public List<AppProperty> getAllAppProperties() throws Exception;

	public void createAppProperty(AppProperty appProperty) throws Exception;
	
	public void updateAppProperty(AppProperty appProperty) throws Exception;
	
	public void deleteAppProperty(String key) throws Exception;
}
