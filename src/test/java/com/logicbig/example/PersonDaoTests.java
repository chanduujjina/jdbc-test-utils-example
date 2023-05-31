package com.logicbig.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.demo.AppConfig;
import com.demo.JdbcTemplatePersonDao;
import com.demo.Person;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PersonDaoTests {

    @Autowired
    private JdbcTemplatePersonDao personDao;

    @Test
    public void saveBloBAsText() {
        JdbcTestUtils.deleteFromTables(personDao.getJdbcTemplate(), "PERSON");

       
        String name ="test Blob";
        //insert
        Person person = Person.create("Dana", "Whitley", "464 Gorsuch Drive",name.getBytes());
        long generatedId = personDao.save(person);
        System.out.println("Generated Id: " + generatedId);

        //read
        Person loadedPerson = personDao.load(generatedId);
        byte[] image = loadedPerson.getImage();
        String str=new String(image);  
        System.out.println("Loaded Person: " + loadedPerson);
        Assert.assertNotNull(loadedPerson);
        Assert.assertTrue("Dana".equals(loadedPerson.getFirstName()));

        
    }
    
    @Test
    public void saveBlobAsImage() throws IOException {
    	BufferedImage image = ImageIO.read(new File("Input-Output-Stream_base_diagram.jpg"));
    	ByteArrayOutputStream outStreamObj = new ByteArrayOutputStream();
    	ImageIO.write(image, "jpg", outStreamObj);
    	byte [] byteArray = outStreamObj.toByteArray();
    	
    	 //insert
        Person person = Person.create("Dana", "Whitley", "464 Gorsuch Drive",byteArray);
        long generatedId = personDao.save(person);
        System.out.println("Generated Id: " + generatedId);

        //read
        Person loadedPerson = personDao.load(generatedId);
        System.out.println("Loaded Person: " + loadedPerson);
        Assert.assertNotNull(loadedPerson);
        Assert.assertTrue("Dana".equals(loadedPerson.getFirstName()));
        
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(byteArray);
        BufferedImage newImage = ImageIO.read(inStreambj);
        ImageIO.write(newImage, "jpg", new File("outputImage.jpg") );
    }
    
}