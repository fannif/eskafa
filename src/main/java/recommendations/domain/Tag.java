
package recommendations.domain;

import java.util.List;

public class Tag {
    
    private int id;
    private String name;

    public Tag(int id, String name) {
        this.name = name;
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
    
    @Override
    public String toString() {
        return name;
    }
    
}
