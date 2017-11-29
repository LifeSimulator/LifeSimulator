package subversion_team.cs.brandeis.edu.lifesimulator;

public class Achievement {

    private int id;
    private String name;
    private String description;
    private String icon;
    private boolean earned;

    public Achievement() {
        name = "";
        description = "";
        icon = "";
        earned = false;
    }

    public Achievement(String name, String description, String icon, boolean earned) {
        this.name = name;
        this.description = description;
        this.icon = icon;
        this.earned = earned;
    }

    public int getID() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getIcon() {
        return icon;
    }

    public boolean hasEarned() {
        return earned;
    }

    public void setID(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public void setEarned(boolean earned) {
        this.earned = earned;
    }

    @Override
    public String toString() {
        return "Achievement # " + getID() + ": " + getName() + ", ? " + hasEarned();
    }
}