package chapter2;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/chapter2/ibatisTest.xml")
public class iBatisTest {
    @Autowired
    MemberDao dao;

    @Before
    public void setUp() throws Exception {
        dao.deleteAll();
    }

    @Test
    public void insert() {
        dao.insert(new Member(1, "spring", 1.5));
        List<Member> list = dao.findMembers();
        assertThat(list.size(), is(1));
    }

    @Test
    public void findMembers() {
        List<Member> list = dao.findMembers();
        assertThat(list.size(), is(0));

        dao.insert(new Member(1, "spring", 1.5));
        dao.insert(new Member(2, "framework", 2.5));

        list = dao.findMembers();
        assertThat(list.size(), is(2));
        verify(1, "spring", 1.5);
        verify(2, "framework", 2.5);
    }

    @Test
    public void deleteMembers() {
        dao.insert(new Member(1, "spring", 1.5));
        dao.insert(new Member(2, "framework", 2.5));
        dao.deleteAll();
        List<Member> list = dao.findMembers();
        assertThat(list.size(), is(0));
    }

    @Test
    public void findMemberById() {
        dao.insert(new Member(1, "spring", 1.5));
        Member member = dao.findMemberById(1);
        assertThat(member.id, is(1));
        assertThat(member.name, is("spring"));
        assertThat(member.point, is(1.5));
    }

    @Test
    public void findMembersForMap() {
        dao.insert(new Member(1, "spring", 1.5));
        dao.insert(new Member(2, "framework", 2.5));
        Map<Integer, Member> map = dao.findMembersForMap();
        assertThat(map.get(1).name, is("spring"));
        assertThat(map.get(2).name, is("framework"));
    }

    private void verify(int id, String name, double point){
        Member member = dao.findMemberById(id);
        assertThat(member.name, is(name));
        assertThat(member.point, is(point));
    }
}
