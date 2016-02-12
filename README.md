#Carbon Application Deploy/Undeploy Client

Supported java version 1.7

This client can used as external java program to connecting to up and running WSO2 enterprise service bus and
deploy/undeploy given carbon application.

 Things to modify in existing code before proceed.
 1. Modify host name in carbonAppUploaderAdminServiceUrl and applicationAdminServiceUrl variables in
 CappDeployUndeployClient class.
 2. Pass valid admin username and password to initialize CappDeployUndeployClient and authenticate admin
 service stubs.
 3. Refer to code snippet written under main() method in Main class on how to invoke deploy/undeploy method in
 CappDeployUndeployClient by providing carbon application.
