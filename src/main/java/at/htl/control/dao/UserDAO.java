package at.htl.control.dao;

import at.htl.entity.MyUser;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;

@ApplicationScoped
public class UserDAO {

    @Inject
    EntityManager em;

    @Transactional
    public void persistUser(MyUser myUser) {
        em.persist(myUser);
    }

}
