package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Dirty Checking
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션 시작

            Member member = new Member(20L, "dirtyChecking"); // 엔티티 생성
            em.persist(member); // 엔티티 영속

            Member findMember = em.find(Member.class, 20L); // 조회
            findMember.setName("cleanChecking"); // 수정
            //em.persist(findMember);

            // jpa의 변경감지를 통해 마치 컬렉션에서 객체를 수정하듯이 테이블을 수정할 수 있다.

            tx.commit(); // 커밋
        } catch (Exception e) {
            tx.rollback(); // 실패하면 롤백
        }finally {
            em.close();
        }
        emf.close();
    }
}
