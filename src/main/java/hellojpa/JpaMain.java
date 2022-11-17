package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * 다대일
 */

public class JpaMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin(); // 트랜잭션 시작

        try {
            /**
             * 저장
             */
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setName("MemberA");
            member.setTeam(team);
            em.persist(member);

            /**
             * 양방향 조회
             */

            // member -> team 조회 가능
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

            // team -> member 조회 가능
            List<Member> members = findTeam.getMembers();
            for (Member m : members) {
                System.out.println("m = "+m.getName());
            }





            tx.commit(); // 커밋
        } catch (Exception e) {
            tx.rollback(); // 실패하면 롤백
        }finally {
            em.close();
        }
        emf.close();
    }
}
