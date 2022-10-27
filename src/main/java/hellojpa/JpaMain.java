package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션 시작

            Member member1 = new Member(1L,"hello");
            em.persist(member1); // 1차캐시에 저장
            Member findMember = em.find(Member.class,1L); // 1차캐시에서 조회

            tx.commit(); // 커밋

//            데이터베이스에서 조회
//            Member findMember1 = em.find(Member.class,2L); DB에서 조회
//            Member findMember2 = em.find(Member.class,2L); 1차캐시에서 조회
        } catch (Exception e) {
            tx.rollback(); // 실패하면 롤백
        }finally {
            em.close();
        }
        emf.close();
    }
}
