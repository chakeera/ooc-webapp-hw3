import Service.SecurityService;
import Servlet.ServletRouter;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;

import java.io.File;

public class Webapp {

    public static void main(String[] args) throws Exception{

        File docBase = new File("src/main/webapp/");
        docBase.mkdirs();
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8082);

        //TODO: declare read db here

        SecurityService securityService = new SecurityService();
        ServletRouter servletRouter = new ServletRouter();
        servletRouter.setSecurityService(securityService);
        Context ctx;
        try {
            ctx = tomcat.addWebapp("", docBase.getAbsolutePath());
            servletRouter.init(ctx);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
        }

    }
}