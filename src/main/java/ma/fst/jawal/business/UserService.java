package ma.fst.jawal.business;

import ma.fst.jawal.entities.User;
import ma.fst.jawal.services.AccountImp;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping(value = {"api/userservice"})
@CrossOrigin
public class UserService {
	private final AccountImp accountService;

    public UserService(AccountImp accountService) {
        this.accountService = accountService;
    }
    @GetMapping(path = "/users/{login}")
    public User getUser(@PathVariable String login) {
        return accountService.loadUserByUsername(login);
    }
    @GetMapping(path = "/users")
    @PostAuthorize("hasAuthority('RESPONSABLE')")
    public List<User> getUsers() {
        return accountService.listUser();
    }

//	@GetMapping("/fournisseurs")
//	public List<FournisseurResponses> fournisseurs() {
//		List<FournisseurResponses> fs = new ArrayList<>();
// 		for(Fournisseur f: fournisseurRepository.findAll()) fs.add(new FournisseurResponses(f));
//		return fs;
//	}
	
//	@GetMapping("/userInfo")
//	public ResponseEntity<?> getUserInfo(Principal user){
//		User userObj=(User) userDetailsService.loadUserByUsername(user.getName());
//
//		UserInfo userInfo=new UserInfo();
//		userInfo.setFirstName(userObj.getNom());
//		userInfo.setLastName(userObj.getPrenom());
//		userInfo.setUserName(userObj.getLogin());
//		userInfo.setRoles(userObj.getAuthorities().toArray());
//
//		return ResponseEntity.ok(userInfo);
//	}
	
//	@RequestMapping(path = "/editPwd", method = RequestMethod.POST)
//	public Boolean editPsswd(@RequestBody ChangePasswordRequest loggedUser) {
//		User user= iUserRepository.findByLogin(loggedUser.getLogin());
//		if (user != null && checkIfValidOldPassword(user, loggedUser.getOrdPassword())) {
//			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//			String encodedPassword = encoder.encode(loggedUser.getNewPassword());
//			user.setPwd(encodedPassword);
//			user.setActive(true);
//			iUserRepository.save(user);
//			return true;
//		}
//		else {
//			return false;
//		}
//	}
	
//	@RequestMapping(path = "/editInfo", method = RequestMethod.GET)
//	public ResponseEntity<?> editInfo(HttpServletRequest req,@AuthenticationPrincipal UserDetails loggedUser) {
//		 return ResponseEntity.ok(iUserRepository.findByLogin(loggedUser.getUsername()));
//	}
//
//	@RequestMapping(path = "/editInfoPerso", method = RequestMethod.GET)
//	public void editInfoPerso(User user) {
//		user.setActive(true);
//		iUserRepository.save(user);
//	}
//
//	@RequestMapping(path = "/editPwd", method = RequestMethod.GET)
//	public ResponseEntity<?> editPwd(HttpServletRequest req,@AuthenticationPrincipal UserDetails loggedUser) {
//		return ResponseEntity.ok(iUserRepository.findByLogin(loggedUser.getUsername()));
//	}

//	private boolean checkIfValidOldPassword(User user,String ordPassword) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		return encoder.matches(ordPassword, user.getPwd());
//	}

//	@RequestMapping(path = "/users", method = RequestMethod.GET)
//	public List<UserResponse> users() {
//		List<UserResponse> usrs = new ArrayList<>();
//		List<String> login = new ArrayList<>();
//		for(Enseignant u: enseignantRepository.findAll()) {
//			if(u.getSupprimer() == false) usrs.add(new UserResponse(u));
//		}
//		for(User u: iUserRepository.findAll()) {
//			if(u.getSupprimer() == false) {
//				if(!u.getRole().equalsIgnoreCase("fournisseur") && !login.contains(u.getLogin())) {
//					usrs.add(new UserResponse(u));
//				}
//			}
//		}
//		return usrs;
//	}

//	@RequestMapping(path = "/usersbydepartements", method = RequestMethod.GET)
//	public List<UserInfo> usersbydepartements(Principal user) {
//		User usr = iUserRepository.findByLogin(user.getName());
//		List<UserInfo> usrs = new ArrayList<>();
//		for(User u: iUserRepository.findAll()) {
//			if(u.getSupprimer().equals(false)) {
//				if(u.getRole().equalsIgnoreCase("enseignant") || u.getRole().equalsIgnoreCase("administratif")) {
//					if (u.getDepartement().getId() == usr.getDepartement().getId()) {
//						UserInfo login = new UserInfo();
//						login.setUserName(u.getUsername());
//						usrs.add(login);
//					}
//				}
//			}
//		}
//		return usrs;
//	}

//	@RequestMapping(path = "/adduser", method = RequestMethod.POST)
//	public CodeStatus addUser(@RequestBody UserRequest userReq) {
//		String role = userReq.getRole();
//		if(role == null) {
//			return new CodeStatus(300);
//		}
//		else {
//			if(role.equalsIgnoreCase("enseignant")) {
//				Enseignant ens = userReq.getEnseignant();
//				codePasswordAndActive(ens);
//				iUserRepository.save(ens);
//			}
//			else if(role.equalsIgnoreCase("fournisseur")) {
//				if(iUserRepository.findByLogin(userReq.getFournisseur().getLogin()) == null) {
//					Fournisseur fournisseur = userReq.getFournisseur();
//					codePasswordAndActive(fournisseur);
//					iUserRepository.save(fournisseur);
//				}
//				else {
//					return new CodeStatus(201);
//				}
//			}
//			else {
//				User user = userReq.getUser();
//				codePasswordAndActive(user);
//				iUserRepository.save(user);
//			}
//		}
//		return new CodeStatus(200);
//	}

//	@RequestMapping(path = "/updateuser", method = RequestMethod.POST)
//	public CodeStatus updateuser(@RequestBody UserRequest userReq) {
//		String role = userReq.getRole();
//		if(role == null) {
//			return new CodeStatus(300);
//		}
//		else {
//			if(role.equalsIgnoreCase("enseignant")) {
//				Enseignant ens = userReq.getEnseignant();
//				Enseignant nv = enseignantRepository.findByLogin(ens.getLogin());
//				if(nv == null) {
//					return new CodeStatus(300);
//				}
//				else {
//					nv.setNom(ens.getNom());
//					nv.setPrenom(ens.getPrenom());
//					nv.setActive(ens.getActive());
//					nv.setNomLab(ens.getNomLab());
//					enseignantRepository.save(nv);
//				}
//
//			}
//			else {
//				User user = userReq.getUser();
//				User nv = iUserRepository.findByLogin(user.getLogin());
//				if(nv == null) {
//					return new CodeStatus(300);
//				}
//				else {
//					nv.setNom(user.getNom());
//					nv.setPrenom(user.getPrenom());
//					nv.setActive(user.getActive());
//					iUserRepository.save(nv);
//				}
//
//			}
//		}
//		return new CodeStatus(200);
//	}
//
//	private User codePasswordAndActive(User user) {
//		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//		user.setPwd(encoder.encode(user.getPwd()));
//		user.setActive(true);
//		return user;
//	}
//
//	@RequestMapping(path = "/editusers", method = RequestMethod.GET)
//	public ResponseEntity<?> editUser(String login) {
//		User user=iUserRepository.findByLogin(login);
//		Enseignant ens = enseignantRepository.findByLogin(login);
//        if(user.getRole().equals("Enseignant")){
//			return ResponseEntity.ok(ens);
//		}
//		return ResponseEntity.ok(user);
//	}
//
//	@RequestMapping(path = "/edituser", method = RequestMethod.POST)
//	public void editUser(UserRequest userReq) {
//
//		if(userReq.getUser().getRole().equals("Enseignant")) {
//			enseignantRepository.save(userReq.getEnseignant());
//		}
//		iUserRepository.save(userReq.getUser());
//	}
//
//	@RequestMapping(path = "/deleteuser", method = RequestMethod.POST)
//	public void deleteUser(@RequestBody codes login) {
//		User user=iUserRepository.findByLogin(login.getCode());
//		user.setActive(false);
//		user.setSupprimer(true);
//        iUserRepository.save(user);
//	}
//
//	public Boolean updateRessetPassword(String token,String email){
//		User user = iUserRepository.findByLogin(email);
//
//		if(user != null){
//			user.setRessetPasswordToken(token);
//			iUserRepository.save(user);
//			return true;
//		}else{
//			return false;
//		}
//	}

}
