package com.logicbig.example;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.imageio.ImageIO;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.jdbc.JdbcTestUtils;

import com.demo.Address;
import com.demo.AppConfig;
import com.demo.JdbcTemplatePersonDao;
import com.demo.Person;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class PersonDaoTests {

    @Autowired
    private JdbcTemplatePersonDao personDao;

    @Test
    public void saveBloBAsText() throws IOException, ClassNotFoundException {
        JdbcTestUtils.deleteFromTables(personDao.getJdbcTemplate(), "PERSON");

       
        String name ="test Blob";
        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream);
        outputStream.writeObject(name);
        //insert
        Person person = Person.create("Dana", "Whitley", "464 Gorsuch Drive",arrayOutputStream.toByteArray());
        long generatedId = personDao.save(person);
        System.out.println("Generated Id: " + generatedId);

        //read
        Person loadedPerson = personDao.load(generatedId);
        byte[] image = loadedPerson.getImage();
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
        ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
        String resultName =(String)objectInputStream.readObject();
         
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
        
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(loadedPerson.getImage());
        BufferedImage newImage = ImageIO.read(inStreambj);
        ImageIO.write(newImage, "jpg", new File("outputImage.jpg") );
    }
    
    @Test
    public void saveBlobAsObject() throws IOException, ClassNotFoundException {
    	
    	Address address = new Address(1, "hyderabad", "Permenet");
    	 ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
         ObjectOutputStream outputStream = new ObjectOutputStream(arrayOutputStream);
         outputStream.writeObject(address);
         
       //insert
         Person person = Person.create("Dana", "Whitley", "464 Gorsuch Drive",arrayOutputStream.toByteArray());
         long generatedId = personDao.save(person);
         System.out.println("Generated Id: " + generatedId);

         //read
         Person loadedPerson = personDao.load(generatedId);
         byte[] image = loadedPerson.getImage();
         ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(image);
         ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
         Address resAddress = (Address)objectInputStream.readObject();
          
         System.out.println("Loaded Person: " + loadedPerson);
         System.out.println(resAddress.toString());
         Assert.assertNotNull(loadedPerson);
         Assert.assertTrue("Dana".equals(loadedPerson.getFirstName()));

    	
    	
    }
    
}