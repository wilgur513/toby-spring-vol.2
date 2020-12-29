package chapter2;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface MemberDao {
    void insert(Member m);
    void insertAll(List<Member> members);
    void deleteAll();

    @Transactional(readOnly = true)
    List<Member> findMembers();
    @Transactional(readOnly = true)
    Member findMemberById(int id);
    @Transactional(readOnly = true)
    Map<Integer, Member> findMembersForMap();
}
