package ma.fst.jawal.services.accounts;

import ma.fst.jawal.entities.Authority;
import ma.fst.jawal.entities.User;
import ma.fst.jawal.repositories.AuthorityRepository;
import ma.fst.jawal.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class AccountImp implements Account{
    private final UserRepository userRepository;
    private final AuthorityRepository authorityRepository;

    public AccountImp(UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

    @Override
    public void addUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public void addRole(Authority role) {
        authorityRepository.save(role);
    }

    @Override
    public void addRoleToUser(String login, String role) {
        User user = userRepository.findByLogin(login);
        Authority authority = authorityRepository.findByRole(role);
        if(user != null && authority != null)
            user.getAuthorities().add(authority);
    }

    @Override
    public void revokeRoleToUser(String login, String role) {
        Authority authority = null;
        User user = userRepository.findByLogin(login);
        for(Authority r: user.getAuthorities()){
            if(r.getRole().equals(role)) {
                authority = r;
            }
        }
        if(authority != null) {
            user.getAuthorities().remove(authority);
        }
    }

    @Override
    public User loadUserByUsername(String username) {
        return userRepository.findByLogin(username);
    }

    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }
}
