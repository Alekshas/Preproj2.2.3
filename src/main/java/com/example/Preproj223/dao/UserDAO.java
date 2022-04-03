package com.example.Preproj223.dao;

import com.example.Preproj223.models.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDAO {

    @Autowired
    SessionFactory sessionFactory;

    @PostConstruct
    public void setUserList() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(new User("Tom", "Brown", 24,  "tom@mail.ru"));
            session.save(new User("Bob", "Brown",52,  "bob@mail.ru"));
            session.save(new User("Mike", "Brown",18,  "mike@yahoo.com"));
            session.save(new User("Katy", "Brown",34,  "katy@gmail.com"));
            tx1.commit();
        }
        System.out.println(getAllUsers().toString());
    }

    @PreDestroy
    public void cleanUserList() {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(new User("Tom","Brown", 24,  "tom@mail.ru"));
            session.delete(new User("Bob", "Brown",52, "bob@mail.ru"));
            session.delete(new User("Mike", "Brown",18, "mike@yahoo.com"));
            session.delete(new User("Katy", "Brown",34, "katy@gmail.com"));
            tx1.commit();
        }
    }

    public void save(User user) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.save(user);
            tx1.commit();
        }
    }

    public List<User> getAllUsers() {
        try (Session session = sessionFactory.openSession()) {
            return (ArrayList<User>) session.createQuery("From User ").list();
        }
    }

    public User show(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(User.class, id);
        }
    }

    public void update(int id, User updatedUser) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            updatedUser.setId(id);
            session.update(updatedUser);
            tx1.commit();
        }
    }

    public void delete(int id) {
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(session.get(User.class, id));
            tx1.commit();
        }
    }

}
