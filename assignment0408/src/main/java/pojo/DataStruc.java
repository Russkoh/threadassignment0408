package pojo;

public class DataStruc {

	String name;
	String attributeName;
	String type;
	
	
	
	public DataStruc(String name, String attributeName, String type) {
		super();
		this.name = name;
		this.attributeName = attributeName;
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttributeName() {
		return attributeName;
	}
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
}
