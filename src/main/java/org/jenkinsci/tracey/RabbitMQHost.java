package org.jenkinsci.tracey;

import com.cloudbees.plugins.credentials.CredentialsMatchers;
import com.cloudbees.plugins.credentials.CredentialsProvider;
import com.cloudbees.plugins.credentials.common.StandardCredentials;
import com.cloudbees.plugins.credentials.common.StandardListBoxModel;
import com.cloudbees.plugins.credentials.common.StandardUsernamePasswordCredentials;
import com.cloudbees.plugins.credentials.domains.DomainRequirement;
import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import hudson.model.ItemGroup;
import hudson.security.ACL;
import hudson.util.FormValidation;
import hudson.util.ListBoxModel;
import net.praqma.tracey.broker.impl.rabbitmq.RabbitMQDefaults;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.kohsuke.stapler.AncestorInPath;
import org.kohsuke.stapler.DataBoundConstructor;
import org.kohsuke.stapler.QueryParameter;
import org.kohsuke.stapler.StaplerRequest;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

public class RabbitMQHost implements Describable<RabbitMQHost> {

    private String host;
    private String credentialId;
    private String description;
    private String hostId;
    private int rabbitMQPort = RabbitMQDefaults.PORT;//5672

    @DataBoundConstructor
    public RabbitMQHost(String host, String credentialId, String description, int rabbitMQPort, String hostId) {
        this.credentialId = credentialId;
        this.host = host;
        this.description = description;
        this.rabbitMQPort = rabbitMQPort;
        this.hostId = hostId;
    }

    @Override
    public Descriptor<RabbitMQHost> getDescriptor() {
        return new RabbitMQHostDescriptor();
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the credentialId
     */
    public String getCredentialId() {
        return credentialId;
    }

    /**
     * @param credentialId the credentialId to set. This is set using form binding
     *        with the provided credentials control.
     */
    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the rabbitMQPort
     */
    public int getRabbitMQPort() {
        return rabbitMQPort;
    }

    /**
     * @param rabbitMQPort the rabbitMQPort to set
     */
    public void setRabbitMQPort(int rabbitMQPort) {
        this.rabbitMQPort = rabbitMQPort;
    }

    /**
     * @return the hostId
     */
    public String getHostId() {
        return hostId;
    }

    /**
     * @param hostId the hostId to set
     */
    public void setHostId(String hostId) {
        this.hostId = hostId;
    }


    @Extension
    public static class RabbitMQHostDescriptor extends Descriptor<RabbitMQHost> {

        @Override
        public String getDisplayName() {
            return "RabbitMQ Host";
        }

        public static String generateRandomUUID() {
            return UUID.randomUUID().toString();
        }

        public ListBoxModel doFillCredentialIdItems(final @AncestorInPath ItemGroup<?> context) {
            final List<StandardCredentials> credentials = CredentialsProvider.lookupCredentials(StandardCredentials.class, context, ACL.SYSTEM, Collections.<DomainRequirement>emptyList());

            return new StandardListBoxModel()
                    .withEmptySelection()
                    .withMatching(CredentialsMatchers.anyOf(
                            CredentialsMatchers.instanceOf(StandardUsernamePasswordCredentials.class)
                    ), credentials);
        }

        @Override
        public boolean configure(StaplerRequest req, JSONObject json) throws FormException {
            save();
            return true;
        }

        public FormValidation doCheckDescription(@QueryParameter String description) {
            if (StringUtils.isBlank(description)) {
                return FormValidation.error("Description must be specified");
            }
            return FormValidation.ok();
        }

    }

}
