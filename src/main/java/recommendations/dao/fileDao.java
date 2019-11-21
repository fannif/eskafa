
package recommendations.dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import recommendations.domain.Book;

public class fileDao implements readerDao<Book, String> {
    
    Scanner fileReader;
    File file;
    
    public fileDao() {
        file = new File("books.csv");
        try { 
            file.createNewFile(); // Create file if doesn't exist
        } catch (IOException ex) {
            Logger.getLogger(fileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            connectReader();
        } catch (Exception ex) {
            Logger.getLogger(fileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public Book findOne(String title) {
        try {
            connectReader();
        } catch (Exception ex) {
            Logger.getLogger(fileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            if (line.split(",")[1].equals(title)) {
                fileReader.close();
                return convert(line);
            }
        }
        fileReader.close();
        return null;
    }
    
    @Override
    public ArrayList<Book> findAll() {
        try {
            connectReader();
        } catch (Exception ex) {
            Logger.getLogger(fileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<Book> tips = new ArrayList<>();
        String line = "";                
        
        while(fileReader.hasNextLine()) {
            line = fileReader.nextLine();
            if (line.trim().equals("")) {
                continue;
            }
            tips.add(convert(line));
        }
        fileReader.close();
        return tips;
    }
    
    @Override
    public boolean save(Book tip) throws IOException {
        String newLine = "";

        newLine = newLine + tip.getAuthor() + ",";
        newLine = newLine + tip.getTitle() + ",";
        newLine = newLine + tip.getType()+ ",";
        newLine = newLine + tip.getISBN() + ",";
        
        int i = 0;
        
        for (String tag: tip.getTag()) {
            if (i == 0) {
                newLine = newLine + tag;
                i++;
                continue;
            }
            newLine = newLine + ";" + tag;            
        }
        
        newLine = newLine + ",";
        
        i = 0;
        
        for (String related: tip.getRelatedCourses()) {
            if (i == 0) {
                newLine = newLine + related;
                i++;
                continue;
            }
            newLine = newLine + ";" + related;
        }
        
        newLine = newLine + "," + tip.getComment() + ", ";
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("books.csv", true))) {
            writer.append(newLine);
            writer.append("\n");
        }
        
        return true;
    }
    
    // Deletes every book by given title
    @Override
    public void delete(String title) throws Exception {
        File temp = new File("temp.csv");
        try {
            connectReader();
        } catch (Exception ex) {
            Logger.getLogger(fileDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(temp))) {
            
            
            String line = "";
            
            while(fileReader.hasNextLine()) {
                if (line.trim().equals("")) {
                    continue;
                }
                if (line.split(",")[1].equals(title)) {
                    continue;
                }
                writer.write(line + "\n");
            }
        }
        fileReader.close();
        
        file.delete();
        temp.renameTo(new File("books.csv"));
    }
    
    // Converts line from file into book
    private Book convert(String line) {
        String[] parts = line.split(",");
        String[] tags = parts[4].split(";");
        String[] related = parts[5].split(";");
        ArrayList<String> bookTags = new ArrayList<>();
        ArrayList<String> bookRelated = new ArrayList<>();
        
        bookTags.addAll(Arrays.asList(tags));
        
        bookRelated.addAll(Arrays.asList(related));
        
        return new Book(parts[0], parts[1], parts[2], parts[3], bookTags, bookRelated, parts[6]);
    }
    
    private void connectReader() throws Exception {
         
        this.fileReader = new Scanner(new File("books.csv"));
    }
    
}
