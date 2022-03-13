package com.example.Preproj223.dao;

import com.example.Preproj223.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {
    private List<User> userList;

    @Autowired
    SessionFactory localSessionFactoryBean;

    public void setUserList() {
        try (Session session = localSessionFactoryBean.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(new User("Tom", 24, "Brown", "tom@mail.ru"));
            session.save(new User("Bob", 52, "Brown", "bob@mail.ru"));
            session.save(new User("Mike", 18, "Brown", "mike@yahoo.com"));
            session.save(new User("Katy", 34, "Brown", "katy@gmail.com"));
            tx1.commit();
        }

    }

    public List<User> getAllUsers() {
        try (Session session = localSessionFactoryBean.openSession()) {
            return (ArrayList<User>) session.createQuery("From User ").list();
        }
    }

    public User show(int id) {
        return userList.stream().filter(userList -> userList.getId() == id).findAny().orElse(null);
    }

    public void save(User user) {
        try (Session session = localSessionFactoryBean.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
        }
    }

    public void update(int id, User updatedUser) {
        User userToBeUpdated = show(id);

        userToBeUpdated.setName(updatedUser.getName());
        userToBeUpdated.setAge(updatedUser.getAge());
        userToBeUpdated.setEmail(updatedUser.getEmail());
    }

    public void delete(int id) {
        userList.removeIf(p -> p.getId() == id);
    }

}
