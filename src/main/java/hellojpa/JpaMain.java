package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * 쓰기 지연
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션 시작

            Member member1 = new Member(10L, "A");
            Member member2 = new Member(11L, "B");

            em.persist(member1);
            System.out.println("======================");
            em.persist(member2);
            System.out.println("======================");
            //persist() 할때마다 쿼리를 보내지 않고 SQL저장소에 모아서 한번에 보낸다.

            tx.commit(); // 커밋
        } catch (Exception e) {
            tx.rollback(); // 실패하면 롤백
        }finally {
            em.close();
        }
        emf.close();
    }
}
