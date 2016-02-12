package org.wso2.esb.client;

import org.apache.axis2.AxisFault;
import org.apache.commons.fileupload.FileItemFactory;
import org.wso2.carbon.application.mgt.stub.ApplicationAdminExceptionException;
import org.wso2.carbon.utils.FileItemData;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import java.io.File;
import java.rmi.RemoteException;

/**
 * Main class does capp deploy and undeploy
 */
public class Main {

    protected static final String FILE_TYPE = "jar";

    /**
     * Main method
     *
     * @param args arguments
     * @throws RemoteException
     * @throws ApplicationAdminExceptionException
     */
    public static void main(String[] args) throws RemoteException, ApplicationAdminExceptionException, InterruptedException {
        //initialize capp deploy/undeploy client
        CappDeployUndeployClient client = new CappDeployUndeployClient("admin", "admin");
        //add carbon app to deploy
        String carbonAppFile1Path = "/home/indika/Downloads/CappFile1_1.0.0.car";
        String carbonAppFile1Name = "CappFile1_1.0.0.car";
        FileDataSource fileDataSource = new FileDataSource(new File(carbonAppFile1Path));
        DataHandler dataHandler = new DataHandler(fileDataSource);
        client.addUploadedFileItem(dataHandler, carbonAppFile1Name, FILE_TYPE);
        //deploy carbon app
        client.deployCarbonApp();
        //wait 30 seconds
        Thread.sleep(30000);
        //undeploy carbon app
        client.undeployCarbonApp(client.getAppNameWithoutVersion(carbonAppFile1Name));
    }
}
