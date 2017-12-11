package httpclients;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author zhayangtao
 * 2017/12/5
 */
public class TestHttpClientsTest {

    @Test
    public void get() {
        TestHttpClients clients = new TestHttpClients();
        clients.get();
    }

    @Test
    public void post() {
        TestHttpClients clients = new TestHttpClients();
        clients.post();
    }
}