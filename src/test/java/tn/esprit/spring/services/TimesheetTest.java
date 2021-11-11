package tn.esprit.spring.services;

import static org.junit.Assert.assertEquals;

import java.util.Date;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.annotation.Order;
import org.springframework.test.context.junit4.SpringRunner;

import tn.esprit.spring.entities.Departement;
import tn.esprit.spring.entities.Employe;
import tn.esprit.spring.entities.Mission;
import tn.esprit.spring.entities.Role;
import tn.esprit.spring.entities.Timesheet;
import tn.esprit.spring.repository.TimesheetRepository;
import tn.esprit.spring.services.IEmployeService;
import tn.esprit.spring.services.IEntrepriseService;
import tn.esprit.spring.services.ITimesheetService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TimesheetTest {

    @Autowired
    private ITimesheetService sTimesheet;
    @Autowired
    private IEmployeService sEmploye;
    @Autowired
    private IEntrepriseService sEntreprise;
    @Autowired
    private TimesheetRepository timesheetRepo;

    /**
     * public void ajouterTimesheet(int missionId, int employeId, Date dateDebut,
     * Date dateFin);
     * 
     * public void validerTimesheet(int missionId, int employeId, Date dateDebut,
     * Date dateFin, int validateurId);
     * 
     */

    @Test
    @Order(1)
    public void testAjouterMission() {
        Mission mission = new Mission("Inspection", "sur terrain");
        int id = sTimesheet.ajouterMission((mission));

        Assert.assertNotNull(sTimesheet.getMissionById(id));
    }

    @Test
    @Order(2)
    public void testGetMissionById() {

        Mission mission1 = new Mission("Inspection", "sur terrain");
        int id = sTimesheet.ajouterMission(mission1);

        Mission mission2 = sTimesheet.getMissionById(id);
        Assert.assertNotNull(mission2);

        Mission mission3 = sTimesheet.getMissionById(213232);
        Assert.assertNull(mission3);
    };

    @Test
    @Order(3)
    public void testAffecterMissionADepartement() {
        Mission mission = new Mission("Inspection", "sur terrain");
        int idMission = sTimesheet.ajouterMission(mission);

        Departement departement = new Departement("Info");
        int idDepartement = sEntreprise.ajouterDepartement(departement);

        int idDepAffecte = sTimesheet.affecterMissionADepartement(idMission, idDepartement);
        Assert.assertEquals(idDepAffecte, idDepartement);

    }
/*
    @Test
    @Order(4)
    public void testAjouterTimesheet() {
        Mission mission = new Mission("Inspection", "sur terrain");

        Employe employe = new Employe("Saidi", "ahmed", "Ahmed.Saidi@esprit.tn", true, Role.INGENIEUR);
        Date dateDebut = new Date(System.currentTimeMillis());
        Date dateFin = new Date(System.currentTimeMillis());
        sTimesheet.ajouterTimesheet(mission.getId(), employe.getId(), dateDebut, dateFin);
    }
*/
    @Test
    @Order(4)
    public void testValiderTimesheet() {
        Mission mission = new Mission("Inspection", "sur terrain");
        sTimesheet.ajouterMission(mission);

        Employe ingenieur = new Employe("Saidi", "ahmed", "Ahmed.Saidi@esprit.tn", true, Role.INGENIEUR);
        Employe chef = new Employe("Saidi", "ahmed", "Ahmed.Saidi@esprit.tn", true, Role.CHEF_DEPARTEMENT);

        Date dateDebut = new Date(System.currentTimeMillis());
        Date dateFin = new Date(System.currentTimeMillis());

        sEmploye.addOrUpdateEmploye(ingenieur);
        sEmploye.addOrUpdateEmploye(chef);

        assertEquals(0,sTimesheet.validerTimesheet(mission.getId(), ingenieur.getId(), dateDebut, dateFin, chef.getId()));
        assertEquals(-1,
                sTimesheet.validerTimesheet(mission.getId(), ingenieur.getId(), dateDebut, dateFin, ingenieur.getId()));

    }
}
