package io.fabric8.quickstarts.camel.brms;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.thoughtworks.xstream.XStream;

import org.drools.core.command.impl.GenericCommand;
import org.drools.core.command.runtime.BatchExecutionCommandImpl;
import org.drools.core.command.runtime.rule.FireAllRulesCommand;
import org.drools.core.command.runtime.rule.InsertObjectCommand;
import org.kie.api.command.BatchExecutionCommand;
import org.kie.api.runtime.ExecutionResults;
import org.kie.internal.runtime.helper.BatchExecutionHelper;
import org.kie.server.api.marshalling.MarshallingFormat;
import org.kie.server.api.model.ServiceResponse;
import org.kie.server.client.KieServicesConfiguration;
import org.kie.server.client.KieServicesFactory;
import org.kie.server.client.RuleServicesClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class DecisionServerHelper {

    /**
     * The REST client used to communicate with the decision server.
     */
    @Autowired
    private RuleServicesClient client;

    private XStream xStream = BatchExecutionHelper.newXStreamMarshaller();

    public Person sendPerson(Person person) throws Exception {
        String xStreamXml = wrapSingle(person, "bean", "fireCommand");
        ServiceResponse<ExecutionResults> executeResponse = client.executeCommandsWithResults("CanDrink", xStreamXml);
        Object res = executeResponse.getResult().getValue("bean");
        return (Person) res;
    }

    private String wrapSingle(Object o, String objectId, String commandId) {
        InsertObjectCommand insertObjectCommand = new InsertObjectCommand(o, objectId);
        FireAllRulesCommand fireAllRulesCommand = new FireAllRulesCommand(commandId);

        List<GenericCommand<?>> commands = new ArrayList<GenericCommand<?>>();
        commands.add(insertObjectCommand);
        commands.add(fireAllRulesCommand);
        BatchExecutionCommand command = new BatchExecutionCommandImpl(commands);

        String xStreamXml = xStream.toXML(command); // actual XML request
        return xStreamXml;
    }

}
