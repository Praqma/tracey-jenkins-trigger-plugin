package org.jenkinsci.tracey.filter;

import hudson.Extension;
import hudson.model.Describable;
import hudson.model.Descriptor;
import jenkins.model.Jenkins;
import org.kohsuke.stapler.DataBoundConstructor;

import java.util.logging.Logger;

/**
 *
 */
public class PayloadJSONRegexFilter extends net.praqma.tracey.broker.impl.filters.PayloadJSONFilter implements Describable<PayloadJSONRegexFilter> {
    private static final Logger LOG = Logger.getLogger(PayloadJSONRegexFilter.class.getName());

    @DataBoundConstructor
    public PayloadJSONRegexFilter(String pattern) {
        super(pattern);
    }

    @Override
    public Descriptor<PayloadJSONRegexFilter> getDescriptor() {
        return Jenkins.getInstance().getDescriptorOrDie(getClass());
    }

    @Extension
    public static class PayloadJSONRegexFilterDescriptor extends Descriptor<PayloadJSONRegexFilter> {

        public static String DEFAULT_REGEX = ".*";

        @Override
        public String getDisplayName() {
            return "JSON Regex filter";
        }

    }
}
