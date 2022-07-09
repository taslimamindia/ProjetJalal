package ma.fst.jawal.services.accounts;

import ma.fst.jawal.entities.Authority;
import ma.fst.jawal.entities.User;

import java.util.List;

public interface Account {
    void addUser(User user);
    void addRole(Authority role);
    void addRoleToUser(String login, String role);
    void revokeRoleToUser(String login, String role);
    User loadUserByUsername(String username);
    List<User> listUser();
}
