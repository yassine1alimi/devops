package tn.esprit.spring.services;

import java.util.List;
import java.util.Optional;

import org.apache.log4j.Logger; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.repository.ContratRepository;

@Service
public class ContratServiceImpl implements IContratService {


	@Autowired
	ContratRepository contratRepository;
	public static final Logger l = Logger.getLogger(EntrepriseServiceImpl.class);


	public List<Contrat> getAllContrats() {
		return (List<Contrat>) contratRepository.findAll();
	}
	
	public Contrat addContrat(Contrat contrat)
	{
		return contratRepository.save(contrat); 
		
	}
	
	public Contrat updateContrat(Contrat con) {
		// TODO Auto-generated method stub
		return contratRepository.save(con);
	}
	

	public Contrat retrieveContrat(String id) {
		l.info("in  retrievecontrat id = " + id);
		Contrat e =  contratRepository.findById(Integer.parseInt(id)).orElse(null);
		l.info("contrat returned : " + e);
		return e; 
	

	}
	
	
	public void remove(String idContrat)
	{
		if ( ! contratRepository.findById(Integer.parseInt(idContrat)).equals(Optional.empty()) ){
			contratRepository.deleteById(Integer.parseInt(idContrat));
		}
		
	}

}
