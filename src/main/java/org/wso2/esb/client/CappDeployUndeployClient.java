package org.wso2.esb.client;

import org.apache.axis2.AxisFault;
import org.apache.axis2.Constants;
import org.apache.axis2.client.Options;
import org.wso2.carbon.application.mgt.stub.ApplicationAdminExceptionException;
import org.wso2.carbon.application.mgt.stub.ApplicationAdminStub;
import org.wso2.carbon.application.mgt.stub.upload.CarbonAppUploaderStub;
import org.wso2.carbon.application.mgt.stub.upload.types.carbon.UploadedFileItem;

import javax.activation.DataHandler;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class does admin service call and deploy/undeploy carbon application
 */
public class CappDeployUndeployClient {

    private String carbonAppUploaderAdminServiceUrl = "https://localhost:9443/services/CarbonAppUploader?wsdl";
    private String applicationAdminServiceUrl = "https://localhost:9443/services/ApplicationAdmin?wsdl";
    private CarbonAppUploaderStub carbonAppUploaderStub = null;
    private ApplicationAdminStub applicationAdminStub = null;
    private List<UploadedFileItem> uploadServiceTypeList;

    /**
     * CappDeployUndeployClient constructor
     *
     * @param username username of valid user to invoke admin service
     * @param password password of valid user to invoke admin service
     * @throws AxisFault
     */
    public CappDeployUndeployClient(String username, String password) throws AxisFault {
        //initialize and authenticate carbon app uploader stub
        carbonAppUploaderStub = new CarbonAppUploaderStub(this.carbonAppUploaderAdminServiceUrl);
        Options options = carbonAppUploaderStub._getServiceClient().getOptions();
        options.setProperty(Constants.Configuration.ENABLE_MTOM, Constants.VALUE_TRUE);
        options.setManageSession(true);
        //Increase the time out when sending large attachments
        options.setTimeOutInMilliSeconds(10000);
        AuthenticateStubUtil.authenticateStub(username, password, carbonAppUploaderStub);
        uploadServiceTypeList = new ArrayList<>();
        //initialize and authenticate application admin stub
        applicationAdminStub = new ApplicationAdminStub(this.applicationAdminServiceUrl);
        AuthenticateStubUtil.authenticateStub(username, password, applicationAdminStub);
    }

    /**
     * Add multiple carbon app to upload
     *
     * @param dataHandler Data handler object of capp
     * @param fileName    capp name
     * @param fileType    capp type
     */
    public void addUploadedFileItem(DataHandler dataHandler, String fileName, String fileType) {
        UploadedFileItem uploadedFileItem = new UploadedFileItem();
        uploadedFileItem.setDataHandler(dataHandler);
        uploadedFileItem.setFileName(fileName);
        uploadedFileItem.setFileType(fileType);
        uploadServiceTypeList.add(uploadedFileItem);
    }

    /**
     * Deploy all added capp
     *
     * @throws RemoteException
     */
    public void deployCarbonApp() throws RemoteException {
        UploadedFileItem[] uploadServiceTypes = new UploadedFileItem[uploadServiceTypeList.size()];
        uploadServiceTypes = uploadServiceTypeList.toArray(uploadServiceTypes);
        carbonAppUploaderStub.uploadApp(uploadServiceTypes);
        System.out.println("Carbon app deployed successfully!");
    }

    /**
     * Undeploy given capp
     *
     * @param carbonAppNameWithoutVersion capp name without version
     * @throws ApplicationAdminExceptionException
     * @throws RemoteException
     */
    public void undeployCarbonApp(String carbonAppNameWithoutVersion) throws ApplicationAdminExceptionException,
            RemoteException {
        applicationAdminStub.deleteApplication(carbonAppNameWithoutVersion);
        System.out.println("Carbon app undeployed successfully!");
    }

    /**
     * Get capp name without version
     *
     * @param carbonAppName capp name with version
     * @return capp name without version
     */
    public String getAppNameWithoutVersion(String carbonAppName) {
        return carbonAppName.substring(0, carbonAppName.lastIndexOf("_"));
    }
}
