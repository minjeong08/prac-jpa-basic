package hellojpa;

import jakarta.persistence.*;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setHomeAddress(new Address("homeCity", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("oldCity1", "street", "12300"));
            member.getAddressHistory().add(new Address("oldCity2", "street", "10023"));

            em.persist(member);

            em.flush();
            em.clear();

            System.out.println("=====================");
            Member findMember = em.find(Member.class, member.getId());

            Address a = findMember.getHomeAddress();
            findMember.setHomeAddress(new Address("newCity2", a.getStreet(), a.getZipcode()));

            findMember.getFavoriteFoods().remove("치킨");
            findMember.getFavoriteFoods().add("한식");

            findMember.getAddressHistory().remove(new Address("oldCity1", "street", "12300"));
            findMember.getAddressHistory().add(new Address("new111", "street1", "233333343"));
            System.out.println("=====================");

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }
}
