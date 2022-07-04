package ma.fst.jawal.services;

import ma.fst.jawal.entities.Authority;
import ma.fst.jawal.entities.User;

import java.util.List;

public interface Account {
    public void addUser(User user);
    public void addRole(Authority role);
    public void addRoleToUser(String login, String role);
    public void revokeRoleToUser(String login, String role);
    public User loadUserByUsername(String username);
    public List<User> listUser();
}
