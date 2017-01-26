package pw.web.app.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="images")
@PrimaryKeyJoinColumn(name="resource_id")
public class ImageResource extends Resource implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name="data")
	private byte[] data;
	
	public ImageResource() {
		
	}
	
	@Override
	public int getId() {
		return super.getId();
	}
	
	@Override
	public void setId(int id) {
		super.setId(id);
	}
	
	@Override
	public String getName() {
		return super.getName();
	}
	
	@Override
	public void setName(String name) {
		super.setName(name);
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}
}
