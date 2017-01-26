package pw.web.app.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Component
@Entity
@Table(name="labels")
public class Label implements Serializable, Comparator<Label> {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private int id;
	
	@Column(name="text")
	private String text;
	
	@ManyToOne
    @JoinColumn(name="color_id")
	private LabelColor color;
	
	@ManyToMany(fetch=FetchType.EAGER, mappedBy="labels")  
	private Set<Post> posts = new HashSet<Post>();
	
	public Label() {
		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public LabelColor getColor() {
		return color;
	}

	public void setColor(LabelColor color) {
		this.color = color;
	}

	public Set<Post> getPosts() {
		return posts;
	}

	public void setPosts(Set<Post> posts) {
		this.posts = posts;
	}

	@Override
	public int compare(Label o1, Label o2) {
		return o2.getText().compareTo(o1.getText());
	}
	
}
