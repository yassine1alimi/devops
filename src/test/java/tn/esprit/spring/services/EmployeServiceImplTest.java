package tn.esprit.spring.services;

import java.util.Date;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.services.IEmployeService;


@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeServiceImplTest {
	@Autowired
	IEmployeService es;
	

	
	@Test
	public void testAddEmploye() {
		Employe e = new Employe("mohamed","sayed","mohamedsayed.tourabi@esprit.tn",true,Role.ADMINISTRATEUR);
		int id = es.addOrUpdateEmploye(e);
		Assert.assertNotNull(es.getEmployePrenomById(id));
		
	}
	
	@Test
	public void testAddContrat() {
		Contrat c = new Contrat(new Date(),"typeContract",3000);
		int ref = es.ajouterContrat(c);
		Assert.assertNotNull(ref);
		
	}


	@Test
	public void testdeleteDepartementById() {
		Assert.assertNotNull(es.getNombreEmployeJPQL());
	}
	

}