package pw.web.app.dao;

import java.util.List;

import pw.web.app.model.LabelColor;

public interface LabelColorDataAccess {
		public LabelColor getLabelColor(String value) throws Exception;
		
		public LabelColor getLabelColor(int id) throws Exception;
		
		public List<LabelColor> getAllLabelColors() throws Exception;

		public void createLabelColor(LabelColor labelColor) throws Exception;
		
		public void updateLabelColor(LabelColor labelColor) throws Exception;
		
		public void deleteLabelColor(int id) throws Exception;
}
