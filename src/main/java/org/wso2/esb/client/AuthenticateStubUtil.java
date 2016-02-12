package org.wso2.esb.client;

import org.apache.axis2.client.Stub;
import org.wso2.carbon.utils.CarbonUtils;

/**
 * This class is used to authenticate a web services so that it's stubs can be used.
 */
public class AuthenticateStubUtil {

    /**
     * Authenticate the given web service stub against the Product user manager. This
     * will make it possible to use the stub for invoking Product admin services.
     *
     * @param userName username of valid user
     * @param password password of valid user
     * @param stub     Axis2 service stub which needs to be authenticated
     */
    public static void authenticateStub(String userName, String password, Stub stub) {
        System.setProperty("javax.net.ssl.trustStore", "src/main/resources/security/wso2carbon.jks");
        System.setProperty("javax.net.ssl.trustStorePassword", "wso2carbon");
        System.setProperty("javax.net.ssl.trustStoreType", "JKS");
        CarbonUtils.setBasicAccessSecurityHeaders(userName, password, stub._getServiceClient());
    }

}
