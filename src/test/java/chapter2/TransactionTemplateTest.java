package chapter2;

import com.ibatis.sqlmap.client.SqlMapClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.fail;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/chapter2/ibatisTest.xml")
public class TransactionTemplateTest {
    @Autowired
    MemberDao dao;
    TransactionTemplate template;
    @Autowired
    SqlMapClient client;

    @Autowired
    public void setDataSourceTransactionManger(PlatformTransactionManager manager){
        template = new TransactionTemplate(manager);
    }

    @Before
    public void setUp() throws Exception {
        dao.deleteAll();
    }

    @Test
    public void insert() {
        template.execute((s) -> {
            dao.insert(new Member(1, "spring", 1.5));
            dao.insert(new Member(2, "framework", 2.5));

            return null;
        });

        verify(1, "spring", 1.5);
        verify(2, "framework", 2.5);
    }

    @Test
    public void insertRollback() {
        try {
            template.execute(s -> {
                dao.insert(new Member(1, "spring", 1.5));
                dao.insert(new Member(2, "framework", 2.5));
                throw new RuntimeException();
            });
        }catch(Exception e){
            //Ignore
        }

        int count = dao.findMembers().size();
        assertThat(count, is(0));
    }

    @Test
    public void insertAllRollback() {
        try {
            dao.insertAll(Arrays.asList(new Member(1, "spring", 1.5),
                                        new Member(2, "framework", 2.5)));
            fail("fail!");
        }catch (Exception e){
            //Ignore
        }

        assertThat(dao.findMembers().size(), is(0));
    }

    private void verify(int id, String name, double point) {
        Member member = dao.findMemberById(id);
        assertThat(member.name, is(name));
        assertThat(member.point, is(point));
    }
}
