package chapter2;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class MemberDao {
    SqlMapClientTemplate template;

    @Autowired
    public void setSqlMapClient(SqlMapClient client){
        template = new SqlMapClientTemplate(client);
    }

    public void insert(Member member){
        template.insert("insertMember", member);
    }

    public void deleteAll() {
        template.update("deleteMemberAll");
    }

    public List<Member> findMembers(){
        return template.queryForList("findMembers");
    }

    public Map<Integer, Member> findMembersForMap(){
        return template.queryForMap("findMembers", null, "id");
    }

    public Member findMemberById(int id) {
        return (Member) template.queryForObject("findMemberById", id);
    }
}
