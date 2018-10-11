package uci.cs297p.proj;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import uci.cs297p.proj.model.MovieMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjApplicationTests {

    @Autowired
    MovieMapper movieMapper;
    @Test
    public void contextLoads() {
    }

}
