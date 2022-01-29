package morozov.ru.models;

public class FileInfo {
	
	private int id;
    private String name;
    private String firstFilePath;
    private String secondFilePath;
    
    public FileInfo() {
		super();
	}
    
	public FileInfo(int id, String name, String firstFilePath, String secondFilePath) {
		super();
		this.id = id;
		this.name = name;
		this.firstFilePath = firstFilePath;
		this.secondFilePath = secondFilePath;
	}

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getFirstFilePath() {
		return firstFilePath;
	}
	
	public void setFirstFilePath(String firstFilePath) {
		this.firstFilePath = firstFilePath;
	}
	
	public String getSecondFilePath() {
		return secondFilePath;
	}
	
	public void setSecondFilePath(String secondFilePath) {
		this.secondFilePath = secondFilePath;
	} 

}
