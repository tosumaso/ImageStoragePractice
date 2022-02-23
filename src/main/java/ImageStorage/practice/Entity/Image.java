package ImageStorage.practice.Entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="image_storage")
public class Image {

	@Id
	@GeneratedValue
    Long id;

	//@Lob: データベースにバイナリデータを格納できる(画像のデータをテーブルに直接挿入する場合)
	@Lob
    byte[] content;

    String name;
    
    String location;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getContent() {
		return content;
	}

	public void setContent(byte[] content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Image(String name, String location) {
		super();
		this.name = name;
		this.location = location;
	}

	public Image() {
		
	}
    
    
}
