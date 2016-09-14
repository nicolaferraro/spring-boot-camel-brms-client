package io.fabric8.quickstarts.camel.brms;

import org.apache.camel.spring.boot.FatJarRouter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;


@SpringBootApplication
@ImportResource({"classpath:spring/camel-context.xml"})
public class CamelBrmsApp extends FatJarRouter {

    public static void main(String[] args) {
        FatJarRouter.main(args);
    }

}
