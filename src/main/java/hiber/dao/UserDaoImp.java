package hiber.dao;

import hiber.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   public User getCarUser(String model, int series) {

      try (Session session = sessionFactory.openSession()) {
         String hql = "select u from User u where u.car.model = :model and u.car.series = :series";
         Query<User> query = session.createQuery(hql, User.class);
         query.setParameter("model", model);
         query.setParameter("series", series);
         return query.uniqueResult();
      } catch (Exception e) {
         e.printStackTrace();
         return null;
      }
   }
}