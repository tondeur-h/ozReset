package ozreset;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.SolrPingResponse;

/**
 *
 * @author tondeur-h
 */
public class OZReset {

     //Objets SolR
    SolrClient client;
    SolrPingResponse ping;
    String urlString="http://nts129.ch-v.net:8983/solr/arnum";
     //constantes
    int STATUSOK=0;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new OZReset();
    }

    public OZReset() {
        try {
            client=new HttpSolrClient.Builder(urlString).build();
         
            ping=client.ping();
            if (test_ping()==STATUSOK)
            {
                reset();        
            }
        } catch (SolrServerException | IOException ex) {
            Logger.getLogger(OZReset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
     /*********************
     * tester si le status de la connexion est OK
     * @return 
     *********************/
    private int test_ping() 
    {
        int status=ping.getStatus();
        return status;
    }

    /***********************
     * reset le core solR
     ***********************/
    private void reset() {
        try {
            client.deleteByQuery( "*:*" );// delete everything!
            client.commit();
        } catch (SolrServerException | IOException ex) {
            Logger.getLogger(OZReset.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
