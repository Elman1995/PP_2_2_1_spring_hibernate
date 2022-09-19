package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user, Car car) {
      sessionFactory.getCurrentSession().save(car);
      user.setUsCar(car);
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      //TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("from User");
      TypedQuery<User> query=sessionFactory.getCurrentSession().createQuery("FROM User as user INNER JOIN FETCH user.usCar as car");
      return query.getResultList();
   }

   @Override
   @SuppressWarnings("unchecked")
   public List<User> searchUsersByCar(String model, int series) {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("FROM User as user WHERE user.usCar.model = :model AND user.usCar.series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      return query.getResultList();
   }

}
