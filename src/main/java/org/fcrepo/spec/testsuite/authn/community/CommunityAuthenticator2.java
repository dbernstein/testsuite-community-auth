package org.fcrepo.spec.testsuite.authn.community;

import org.fcrepo.spec.testsuite.TestParameters;
import org.fcrepo.spec.testsuite.authn.AuthenticationToken;
import org.fcrepo.spec.testsuite.authn.Authenticator;
import org.fcrepo.spec.testsuite.authn.DefaultAuthToken;

import java.net.URI;

import static org.fcrepo.spec.testsuite.TestParameters.PERMISSIONLESS_USER_PASSWORD_PARAM;
import static org.fcrepo.spec.testsuite.TestParameters.ROOT_CONTROLLER_USER_PASSWORD_PARAM;

public class CommunityAuthenticator2 implements Authenticator {
    public AuthenticationToken createAuthToken(final URI webid) {
        //get username by taking string after the last forward slash.
        final String webidStr = webid.toString();
        final String username = webidStr.substring(webidStr.lastIndexOf('/') + 1);
        final TestParameters params = TestParameters.get();
        final String password =
                webid.toString().equals(params.getRootControllerUserWebId()) ? params.getRootControllerUserPassword() :
                        params.getPermissionlessUserPassword();
        if (password == null) {
            throw new RuntimeException(
                    "When using the DefaultAuthenticator you must specify the optional password parameters in the command" +
                            " line, ie --" +
                            ROOT_CONTROLLER_USER_PASSWORD_PARAM + " and --" + PERMISSIONLESS_USER_PASSWORD_PARAM);
        }
        return new DefaultAuthToken(username, password);
    }
}

