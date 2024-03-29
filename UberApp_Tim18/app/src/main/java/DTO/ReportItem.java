package DTO;


public class ReportItem {
    private String name;
    private String description;
    private int image;

    public ReportItem(String name, String description, int image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImagePath() {
        return image;
    }

    public void setImagePath(int imagePath) {
        this.image = image;
    }
}
