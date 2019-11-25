package recommendations.dao;

import recommendations.domain.Link;

public class LinkTag {

    private Integer id;
    private Integer linkId;
    private Integer tagId;

    public LinkTag(Integer id, Integer linkId, Integer tagId) {
        this.id = id;
        this.linkId = linkId;
        this.tagId = tagId;
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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }
}
