package chapter2;

import java.util.List;
import java.util.Map;

public interface MemberDao {
    void insert(Member m);
    void insertAll(List<Member> members);
    void deleteAll();
    List<Member> findMembers();
    Member findMemberById(int id);
    Map<Integer, Member> findMembersForMap();
}
