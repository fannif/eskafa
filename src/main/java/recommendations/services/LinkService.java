package recommendations.services;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import recommendations.domain.Link;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;

import recommendations.dao.ReaderDao;
import recommendations.domain.Course;
import recommendations.domain.Tag;

import java.net.URL;
import java.net.URLConnection;
import recommendations.io.IO;

public class LinkService {
    
    private IO io;
    private ReaderDao linkDao;
    
    public LinkService(ReaderDao dao, IO io) {
        this.io = io;
        this.linkDao = dao;
    }
    
    public boolean addLinkWithMeta(int id, String title, String url, String type, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) throws SQLException, IOException, MalformedURLException, URISyntaxException {
        
        String descriptionMetaData = getDescription(url);
        Link l = new Link(id, title, url, type, descriptionMetaData, tags, courses, comment);
        
        return linkDao.save(l);
    }
    
    public ArrayList<Link> listLinks() throws SQLException {
        return (ArrayList<Link>) linkDao.findAll();
    }
    
    public String getDescription(String url) throws IOException {
        
        if (!connected(url)) {
            String noConnectionDesc = askForDescription();
            return noConnectionDesc;
        }
        
        try {
            Document document = Jsoup.connect(url).get();
            String description = document.select("meta[name=description]").get(0).attr("content");
            return description;
        } catch (IndexOutOfBoundsException ex) {
            io.print("Metadata description not found!");
        }
        
        String desc = askForDescription();
        return desc;
    }
    
    public String fetchTitle(String url) {
        String title = "";
        try {
            Document doc = Jsoup.connect(url).get();
            title = doc.title();
        } catch (IOException ex) {
            io.print("No internet connection");
        }
        return title;
    }
    
    public void remove(String title) throws Exception {
        boolean go = true;
        String input = title;
        while (go) {
            if (input.equals("q")) {
                return;
            }
            if (linkDao.findOne(input) == null) {
                io.print("No such link found. Please check the spelling and try again: ");
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                linkDao.delete(input);
                go = false;
            }
        }
        io.print("The link has been successfully removed");
    }
    
    public String askForDescription() {
        io.print("Metadata description not found!");
        io.print("Enter description manually or press enter for no description:");
        String descr = io.read();
        return descr;
        
    }
    
    public boolean connected(String url) {
        try {
            URL check = new URL(url);
            URLConnection connection = check.openConnection();
            connection.connect();
            return true;
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
    }
    
    public void edit(String name) throws SQLException {
        boolean go = true;
        String input = name;
        Link link;
        while (go) {
            link = (Link) linkDao.findOne(input);
            if (input.equals("q")) {
                return;
            }
            if (link == null) {
                io.print("No such link found. Please check the spelling and try again: ");
                io.print("To return back to main menu, enter q");
                input = io.read();
            } else {
                System.out.println("Please update a field or fields. Press enter to skip the field.");
                io.print("Title: ");
                String title = io.read();
                io.print("Type: ");
                String type = io.read();
                ArrayList<Tag> tags = new ArrayList();
                ArrayList<Course> courses = new ArrayList();
                io.print("Add zero or more tags. Enter tags one at a time. Press 'enter'"
                        + "to continue: ");
                
                while (true) {
                    String tag = io.read();
                    if (tag.equals("")) {
                        break;
                    }
                    Tag newTag = new Tag(0, tag);
                    tags.add(newTag);
                }
                io.print("Add zero or more related courses. Enter courses one at a time. Press 'enter'"
                        + "to continue: ");
                
                while (true) {
                    String course = io.read();
                    if (course.equals("")) {
                        break;
                    }
                    Course newCourse = new Course(0, course);
                    courses.add(newCourse);
                }
                io.print("Add a comment: ");
                String comment = io.read();
                
                Link updated = updateLinkInformation(link, title, type, tags, courses, comment);
                if (linkDao.edit(updated)) {
                    io.print("The link information has been successfully updated.");
                } else {
                    System.out.println("Oops, something went wrong.");
                }
                go = false;
            }
        }
    }
    
    private Link updateLinkInformation(Link link, String title, String type, ArrayList<Tag> tags, ArrayList<Course> courses, String comment) {
        if (!title.isEmpty()) {
            link.setTitle(title);
        }
        
        if (!type.isEmpty()) {
            link.setType(type);
        }
        
        if (!tags.isEmpty()) {
            link.setTags(tags);
        }
        
        if (!courses.isEmpty()) {
            link.getCourses();
        }
        
        if (!comment.isEmpty()) {
            link.setComment(comment);
        }
        
        return link;
    }

    public String listLinkTitles() throws SQLException {
        StringBuilder sb = new StringBuilder();
        sb.append("Added links:"+"\n");
        sb.append("\n");
        for (Link link : this.listLinks()) {
            sb.append(String.format("%-5s %-5s\n", " ", link.getTitle()));
        }
        return sb.toString();
    }
}
