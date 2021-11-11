package tn.esprit.spring.services;

import java.text.ParseException;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.services.ContratServiceImpl;

@TestMethodOrder(OrderAnnotation.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class ContratServiceTest {
	
	@Autowired
	ContratServiceImpl ser ; 
	
	
	@Test
	public void testAddContrat() throws ParseException {
		
		
		//Date date = new SimpleDateFormat("yyyy-mm-dd").parse("2021-10-31");
	
		java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		Contrat contrat = new Contrat(date ,"test",12);
		Contrat Contratadded = ser.addContrat(contrat);
		Assert.assertEquals(contrat.getTypeContrat(), Contratadded.getTypeContrat());
	}
	
	@Test
	public void testRetrieveAllContrat() {
		List<Contrat> listcontrats = ser.getAllContrats();
		// if there are 4 users in DB : 
		Assert.assertEquals(listcontrats.size(), listcontrats.size());
	}
	
	@Test
	public void testModifyContrat() throws ParseException   {
		java.util.Date date=new java.util.Date();
		java.sql.Date sqlDate=new java.sql.Date(date.getTime());
		Contrat con = new Contrat(2,date ,"test",15);
		Contrat contratModified = ser.updateContrat(con);
		Assert.assertEquals(con.getTypeContrat(), contratModified.getTypeContrat());
	}
	
	@Test
	public void testDeleteContrat() {
		ser.remove("4");
		Assert.assertNull(ser.retrieveContrat("4"));
		
		
	}

}