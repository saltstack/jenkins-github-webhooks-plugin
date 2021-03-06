package com.saltstack.jenkins.github.webhooks;

import hudson.Extension;
import hudson.Functions;
import hudson.model.Cause;
import hudson.model.Cause.UserIdCause;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.jenkinsci.plugins.buildtriggerbadge.provider.BuildTriggerBadgeProvider;


public class BranchesCause extends UserIdCause {
    /**
     * The name of the user who triggered the event from GitHub.
     */
    private HashMap sender_payload;

    public BranchesCause(HashMap sender_payload) {
        super()
        LOGGER.log(Level.INFO, "Instantiating branches cause with ${sender_payload} of type ${sender_payload.getClass()}")
        this.sender_payload = sender_payload;
    }

    @Override
    public String getUserId() {
        return this.sender_payload.id.toString()
    }

    @Override
    public String getUserName() {
        return this.sender_payload.login
    }

    @Override
    public String getShortDescription() {
        return "Started by GitHub branches create/delete webhook triggered by " + getUserName();
    }

    @Extension
    public static class BranchesCauseBadgeProvider extends BuildTriggerBadgeProvider {
        @Override
        public String provideIcon(Cause cause) {
            if (cause instanceof BranchesCause) {
                return hudson.Functions.getResourcePath() + '/plugin/buildtriggerbadge/images/github-push-cause.png';
            }
            return null;
        }
    }

    private static final Logger LOGGER = Logger.getLogger(BranchesCause.class.getName());
}
