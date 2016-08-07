package animeta.resource;

import javax.ws.rs.client.Client;
import javax.ws.rs.core.Response;

import org.glassfish.hk2.api.ServiceLocator;
import org.hibernate.SessionFactory;
import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

import animeta.Animeta;
import animeta.AnimetaConfiguration;
import io.dropwizard.client.JerseyClientBuilder;
import io.dropwizard.testing.ResourceHelpers;
import io.dropwizard.testing.junit.DropwizardAppRule;

public class WorkResourceTest {
    @ClassRule
    public static final DropwizardAppRule<AnimetaConfiguration> RULE =
            new DropwizardAppRule<>(Animeta.class, ResourceHelpers.resourceFilePath("test-config.yml"));

    @Test
    public void testGet() throws Exception {
        ServiceLocator serviceLocator = ((Animeta) RULE.getApplication()).getServiceLocator();
        SessionFactory sessionFactory = serviceLocator.getService(SessionFactory.class);
        System.out.println(sessionFactory);

        Client client = new JerseyClientBuilder(RULE.getEnvironment()).build("");
        Response response = client.target("http://localhost:" + RULE.getLocalPort() + "/works/_/123").request().get();
        Assert.assertEquals(response.getStatus(), Response.Status.NOT_FOUND.getStatusCode());
    }
}
