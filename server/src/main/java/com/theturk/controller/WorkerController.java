package com.theturk.controller;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.theturk.model.Worker;
import com.theturk.repository.WorkerRepository;

@RestController
@RequestMapping("/api/workers")
public class WorkerController {

    @Autowired
    private WorkerRepository workerRepository;
    

    private static final Logger logger = LoggerFactory.getLogger(WorkerController.class);

    @GetMapping("/all")
    public List<Worker> getAllWorkers() {
    	logger.debug("Returning all workers");
    	return workerRepository.findAll();
    }
    
    @DeleteMapping(path ={"/{id}"})
    @Transactional
    public boolean dleteWorker(@PathVariable("id") long id) {
    	logger.debug("deleting worker " + id);
    	
        Optional<Worker> worker = workerRepository.findById(id);
        if(worker != null){
        	workerRepository.delete(worker.get());
        	return true;
        }
        return false;
        
    }
//    
//    @PostMapping("/updateuser")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public boolean updateUser(@CurrentUser UserPrincipal currentUser, @RequestBody User user) {
//    	logger.debug("updating user " + user);
//
//    	Optional<User> existing = userRepository.findById(user.getId());
//    	if(existing != null) {
//    		
//    		existing.get().setName(user.getName());
//    		existing.get().setUsername(user.getUsername());
//    		existing.get().setEmail(user.getEmail());
//    		existing.get().setRole(user.getRole());
//    		existing.get().setUserType(user.getUserType());
//    		existing.get().setUserGroup(user.getUserGroup());
//    		existing.get().setPassword(passwordEncoder.encode(user.getPassword()));
//    		
//    		userRepository.save(existing.get());
//    		
//    		return true;
//    	}
//
//    	return false;
//    }
//    
//    @PostMapping("/adduser")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public User addUser(@CurrentUser UserPrincipal currentUser, @RequestBody User user) {
//    	logger.debug("adding new user " + user);
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
//
//    	return userRepository.save(user);
//    }
//
//    @GetMapping("/user/me")
//    @PreAuthorize("hasRole('USER')")
//    public UserSummary getCurrentUser(@CurrentUser UserPrincipal currentUser) {
//        UserSummary userSummary = new UserSummary(currentUser.getId(), currentUser.getUsername(), currentUser.getName());
//        return userSummary;
//    }
//    
//    @GetMapping("/user/checkUsernameAvailability")
//    public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
//        Boolean isAvailable = !userRepository.existsByUsername(username);
//        return new UserIdentityAvailability(isAvailable);
//    }
//
//    @GetMapping("/user/checkEmailAvailability")
//    public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
//        Boolean isAvailable = !userRepository.existsByEmail(email);
//        return new UserIdentityAvailability(isAvailable);
//    }
//    
//
//    @GetMapping("/allroles")
//    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
//    public List<Role> getAllUserRoles(@CurrentUser UserPrincipal currentUser) {
//    	logger.debug("Returning all user roles");
//    	return roleRepository.findAll();
//    }
}
