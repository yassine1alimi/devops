package tn.esprit.spring.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tn.esprit.spring.entities.Contrat;
import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Entreprise;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.ContratRepository;
import tn.esprit.spring.repository.DepartementRepository;
import tn.esprit.spring.repository.EmployeRepository;
import tn.esprit.spring.repository.TimesheetRepository;

@Service
public class EmployeServiceImpl implements IEmployeService {
	public static final Logger l = Logger.getLogger(EntrepriseServiceImpl.class);
	@Autowired
	EmployeRepository employeRepository;
	@Autowired
	DepartementRepository deptRepoistory;
	@Autowired
	ContratRepository contratRepoistory;
	@Autowired
	TimesheetRepository timesheetRepository;

	public Employe authenticate(String login, String password) {
		l.info("authenticate user with login : "+login+ "password : "+ password);
		return employeRepository.getEmployeByEmailAndPassword(login, password);
	}

	public int addOrUpdateEmploye(Employe employe) {
		l.info("START addOrUpdateEmploye ");
		employeRepository.save(employe);
		return employe.getId();
	}


	public void mettreAjourEmailByEmployeId(String email, int employeId) {
		Employe employe = employeRepository.findById(employeId).get();
		l.info("mettreAjourEmailByEmployeId with email :  " + email + "and employee : " + employeId);
		employe.setEmail(email);
		employeRepository.save(employe);

	}

	@Transactional	
	public void affecterEmployeADepartement(int employeId, int depId) {
				l.info("START affecterEmployeADepartement with employeId : "+employeId + "and depId : "+depId);

		Departement depManagedEntity = deptRepoistory.findById(depId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
			l.trace("Début Test : verifier si le departement na aucun employe");

		if(depManagedEntity.getEmployes() == null){
			l.trace("Entrer Test : le departement na aucun employe");
			List<Employe> employes = new ArrayList<Employe>();
			employes.add(employeManagedEntity);
			depManagedEntity.setEmployes(employes);
		}else{
			l.trace("Entrer Test : le departement a des employes");
			depManagedEntity.getEmployes().add(employeManagedEntity);
		}

		// à ajouter? 
		deptRepoistory.save(depManagedEntity); 

	}
	@Transactional
	public void desaffecterEmployeDuDepartement(int employeId, int depId)
	{
		l.info("START desaffecterEmployeDuDepartement with employeId : "+employeId + "and depId : "+depId);

		Departement dep = deptRepoistory.findById(depId).get();

		int employeNb = dep.getEmployes().size();
		for(int index = 0; index < employeNb; index++){
			if(dep.getEmployes().get(index).getId() == employeId){
				l.trace("Entrer Test : le departement a l'employes avec employeId : "+employeId);
				dep.getEmployes().remove(index);
					l.info("remove employe from department done");
				break;//a revoir
			}
		}
	} 
	
	// Tablesapce (espace disque) 

	public int ajouterContrat(Contrat contrat) {
		l.info("Starting Add contract");
		contratRepoistory.save(contrat);
		l.info("Contract added");
		return contrat.getReference();
	}

	public void affecterContratAEmploye(int contratId, int employeId) {
		l.info("Starting affecterContratAEmploye");

		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		l.trace("Find ContactByid and  EmployeById");
		contratManagedEntity.setEmploye(employeManagedEntity);
		l.info("affecterContratAEmploye Done");
		contratRepoistory.save(contratManagedEntity);

	}

	public String getEmployePrenomById(int employeId) {
		l.info("Starting getEmployePrenomById with id : "+employeId);
		Employe employeManagedEntity = employeRepository.findById(employeId).get();
		return employeManagedEntity.getPrenom();
	}
	 
	public void deleteEmployeById(int employeId)
	{
		l.info("Starting deleteEmployeById with id : "+employeId);
		Employe employe = employeRepository.findById(employeId).get();

		//Desaffecter l'employe de tous les departements
		//c'est le bout master qui permet de mettre a jour
		//la table d'association
		for(Departement dep : employe.getDepartements()){
			dep.getEmployes().remove(employe);
		}

		employeRepository.delete(employe);
	    l.info(" deleteEmployeById with id : "+employeId + "is Done");
	}

	public void deleteContratById(int contratId) {
		l.info("Starting deleteContratById with id : "+contratId);

		Contrat contratManagedEntity = contratRepoistory.findById(contratId).get();
		contratRepoistory.delete(contratManagedEntity);
		l.info("deleteContratById with id : "+contratId + "is Done");

	}

	public int getNombreEmployeJPQL() {
		l.info("Starting getNombreEmployeJPQL");
		l.info("getNombreEmployeJPQL is : " + employeRepository.countemp());
		return employeRepository.countemp();
	}

	public List<String> getAllEmployeNamesJPQL() {
		l.info("Starting getAllEmployeNamesJPQL");
		return employeRepository.employeNames();

	}

	public List<Employe> getAllEmployeByEntreprise(Entreprise entreprise) {
		l.info("Starting getAllEmployeByEntreprise");
		return employeRepository.getAllEmployeByEntreprisec(entreprise);
	}

	public void mettreAjourEmailByEmployeIdJPQL(String email, int employeId) {
		l.info("Starting mettreAjourEmailByEmployeIdJPQL with email : "+email + "and employeId : "+employeId);

		employeRepository.mettreAjourEmailByEmployeIdJPQL(email, employeId);

	}
	public void deleteAllContratJPQL() {
				l.info("Starting deleteAllContratJPQL");

		employeRepository.deleteAllContratJPQL();
	}

	public float getSalaireByEmployeIdJPQL(int employeId) {
						l.info("Starting getSalaireByEmployeIdJPQL with employeId : "+employeId);

		return employeRepository.getSalaireByEmployeIdJPQL(employeId);
	}

	public Double getSalaireMoyenByDepartementId(int departementId) {
		l.info("Starting getSalaireMoyenByDepartementId with departementId : "+departementId);
		return employeRepository.getSalaireMoyenByDepartementId(departementId);
	}

	public List<Timesheet> getTimesheetsByMissionAndDate(Employe employe, Mission mission, Date dateDebut,
			Date dateFin) {
		return timesheetRepository.getTimesheetsByMissionAndDate(employe, mission, dateDebut, dateFin);
	}

	public List<Employe> getAllEmployes() {
						l.info("Starting getAllEmployes");
		return (List<Employe>) employeRepository.findAll();
	}

}
