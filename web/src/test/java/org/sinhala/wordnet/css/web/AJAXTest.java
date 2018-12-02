/*
 * <Paste your header here>
 */
package org.sinhala.wordnet.css.web;

import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.List;

public class AJAXTest {

    @Test
    public void testGetSynonyms() {
        Client client = ClientBuilder.newClient();
        WebTarget webTarget = client.target("http://localhost:8080")
                .path("Ajax/නොහැකි/synonyms");

        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        List<String> response = invocationBuilder.get(List.class);
        System.out.println(response);

        Assert.assertNotNull(response);
        Assert.assertTrue(response.size() > 0);
    }
}
