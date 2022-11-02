package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * 양방향 매핑시 주의할점 1.
 *  연관관계의 주인에 값을 입력하지 않음.
 */
public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin(); // 트랜잭션 시작

            // 저장
            Member member = new Member();
            member.setName("MemberA");
            // member.setTeam(team);  연관관계의 주인에게 값을 입력해야한다.
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member); // mappedBy 읽기전용이기 때문에 데이터베이스에 반영이 안된다.
            em.persist(team);

            em.flush();
            em.clear();


            tx.commit(); // 커밋
        } catch (Exception e) {
            tx.rollback(); // 실패하면 롤백
        }finally {
            em.close();
        }
        emf.close();
    }
}
