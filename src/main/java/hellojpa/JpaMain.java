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

            /**
             *  참조로 연관관계 조회
             */
            // Team 저장
            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            // Member 저장
            Member member = new Member();
            member.setName("MemberA");
//            member.setTeamId(team.getId());
            member.setTeam(team);
            em.persist(member);

            // member가 속한 team 조회 (객체 지향 모델링)
            Member findMember = em.find(Member.class, member.getId());
            Team findTeam = findMember.getTeam();

//            member가 속한 team 조회 (객체를 테이블에 맞추어 모델링)
//            Member findMember = em.find(Member.class, member.getId());
//            Long findTeamId = findMember.getTeamId();
//            Team findTeam = em.find(Team.class, findTeamId);

            tx.commit(); // 커밋
        } catch (Exception e) {
            tx.rollback(); // 실패하면 롤백
        }finally {
            em.close();
        }
        emf.close();
    }
}
