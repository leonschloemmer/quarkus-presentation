package at.htl.control.dao;

import at.htl.entity.User;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserDAO {

    @Inject
    EntityManager em;

    @Transactional
    public void persistUser(User user) {
        em.persist(user);
    }

}
