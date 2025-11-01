package com.cooksys.server;

import com.cooksys.server.entities.Team;
import com.cooksys.server.repositories.TeamRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cooksys.server.embeddables.Credentials;
import com.cooksys.server.embeddables.Profile;
import com.cooksys.server.entities.Announcement;
import com.cooksys.server.entities.Company;
import com.cooksys.server.entities.Project;
import com.cooksys.server.entities.User;
import com.cooksys.server.repositories.AnnouncementRepository;
import com.cooksys.server.repositories.CompanyRepository;
import com.cooksys.server.repositories.ProjectRepository;
import com.cooksys.server.repositories.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Component
public class Seeder implements CommandLineRunner {

	private CompanyRepository companyRepository;
	private UserRepository userRepository;
	private AnnouncementRepository announcementRepository;
	private TeamRepository teamRepository;
	private ProjectRepository projectRepository;
	
	public Seeder(CompanyRepository companyRepository, UserRepository userRepository,
				  AnnouncementRepository announcementRepository, TeamRepository teamRepository,
				  ProjectRepository projectRepository) {
		this.companyRepository = companyRepository;
		this.userRepository = userRepository;
		this.announcementRepository = announcementRepository;
		this.teamRepository = teamRepository;
		this.projectRepository = projectRepository;
	}
	
	@Override
	@Transactional
	public void run(String... args) throws Exception {

		// create teams 1 and 2
		Team team1 = new Team();
		team1.setName("Blue");
		team1.setDescription("Blue Team is awesome");
		teamRepository.save(team1);

		Team team2 = new Team();
		team2.setName("Green");
		team2.setDescription("Green Team is awesome");
		teamRepository.save(team2);

	    Company company1 = new Company();
	    company1.setName("FedEx");
	    company1.setDescription("Test company 1");
	    companyRepository.saveAndFlush(company1);
	    
	    Company company2 = new Company();
	    company2.setName("Cook Systems");
	    company2.setDescription("Test company 2");
	    companyRepository.saveAndFlush(company2);
	    
	    Company company3 = new Company();
	    company3.setName("Google");
	    company3.setDescription("Test company 3");
	    companyRepository.saveAndFlush(company3);

		//set companies for teams 1 and 2
		team1.setCompany(company1);
		team2.setCompany(company2);
	    
	    User worker = new User();
	    worker.setProfile(new Profile()); // Initialize Profile
	    worker.getProfile().setFirstName("Worker first");
	    worker.getProfile().setLastName("Worker last");
	    worker.getProfile().setEmail("worker@worker.com");
	    worker.getProfile().setPhone("000-000-0000");

	    worker.setCredentials(new Credentials()); // Initialize Credentials
	    worker.getCredentials().setUsername("worker");
	    worker.getCredentials().setPassword(BCrypt.hashpw("password", BCrypt.gensalt(12)));
	    worker.setCompanies(new ArrayList<>());
	    worker.getCompanies().add(company1);
	    worker.setActive(true);
	    worker.setAdmin(false);
	    worker.setStatus("ACTIVE");
	    userRepository.saveAndFlush(worker);

	    User admin = new User();
	    admin.setProfile(new Profile()); // Initialize Profile
	    admin.getProfile().setFirstName("Admin first");
	    admin.getProfile().setLastName("Admin last");
	    admin.getProfile().setEmail("admin@admin.com");
	    admin.getProfile().setPhone("000-000-0000");

	    admin.setCredentials(new Credentials()); // Initialize Credentials
	    admin.getCredentials().setUsername("admin");
	    admin.getCredentials().setPassword(BCrypt.hashpw("password", BCrypt.gensalt(12)));

	    admin.setActive(true);
	    admin.setAdmin(true);
	    admin.setStatus("ACTIVE");
	    
	    userRepository.saveAndFlush(admin);

		// add users to teams
		worker.getTeams().add(team1);
		team1.getUsers().add(worker);

		admin.getTeams().add(team2);
		team2.getUsers().add(admin);

		// save teams and users
		userRepository.save(admin);
		userRepository.save(worker);

		teamRepository.saveAndFlush(team1);
		teamRepository.saveAndFlush(team2);
	    
	    // Create announcements for FedEx by Admin
	    Announcement announcement1 = new Announcement();
	    announcement1.setTitle("New Shipping Policy");
	    announcement1.setMessage("We are excited to announce a new shipping policy that will improve delivery times.");
	    announcement1.setCompany(company1); // Set company to FedEx
	    announcement1.setAuthor(admin); // Set author to Admin
	    announcementRepository.saveAndFlush(announcement1); // Save the announcement

	    Announcement announcement2 = new Announcement();
	    announcement2.setTitle("Holiday Schedule");
	    announcement2.setMessage("Please note our holiday schedule for the upcoming season.");
	    announcement2.setCompany(company1); // Set company to FedEx
	    announcement2.setAuthor(admin); // Set author to Admin
	    announcementRepository.saveAndFlush(announcement2); // Save the announcement

	    // Add three more announcements for FedEx
	    Announcement announcement3 = new Announcement();
	    announcement3.setTitle("New Employee Training");
	    announcement3.setMessage("We are introducing a new training program for all employees starting next month.");
	    announcement3.setCompany(company1); // Set company to FedEx
	    announcement3.setAuthor(admin); // Set author to Admin
	    announcementRepository.saveAndFlush(announcement3); // Save the announcement

	    Announcement announcement4 = new Announcement();
	    announcement4.setTitle("Safety Protocol Update");
	    announcement4.setMessage("Please review the updated safety protocols that will take effect immediately.");
	    announcement4.setCompany(company1); // Set company to FedEx
	    announcement4.setAuthor(admin); // Set author to Admin
	    announcementRepository.saveAndFlush(announcement4); // Save the announcement

	    Announcement announcement5 = new Announcement();
	    announcement5.setTitle("Quarterly Performance Review");
	    announcement5.setMessage("Join us for the quarterly performance review meeting next week.");
	    announcement5.setCompany(company1); // Set company to FedEx
	    announcement5.setAuthor(admin); // Set author to Admin
	    announcementRepository.saveAndFlush(announcement5); // Save the announcement
	    
	    Project project1 = new Project();
	    project1.setActive(true);
	    project1.setName("Test project 1");
	    project1.setDescription("This is a test description");
	    project1.setTeam(team1);
	    projectRepository.saveAndFlush(project1);


	    
	    Project project2 = new Project();
	    project2.setActive(false);
	    project2.setName("Test project 2");
	    project2.setDescription("This is a test description");
	    project2.setTeam(team1);
	    projectRepository.saveAndFlush(project2);

	    
	    
	    Project project3 = new Project();
	    project3.setActive(true);
	    project3.setName("Test project 3");
	    project3.setDescription("This is a test description");
	    project3.setTeam(team1);
	    projectRepository.saveAndFlush(project3);

	    

	    System.out.println(companyRepository.findAll());
	    System.out.println(userRepository.findAll());
		System.out.println(teamRepository.findAll());
	}}
	