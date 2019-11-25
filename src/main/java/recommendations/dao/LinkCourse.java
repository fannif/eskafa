package recommendations.dao;


public class LinkCourse {
    private Integer id;
    private Integer linkId;
    private Integer courseId;

    public LinkCourse(Integer id, Integer linkid, Integer courseId) {
        this.id = id;
        this.linkId = linkid;
        this.courseId = courseId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLinkId() {
        return linkId;
    }

    public void setLinkId(Integer linkId) {
        this.linkId = linkId;
    }

    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }
}
