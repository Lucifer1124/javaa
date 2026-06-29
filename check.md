1.Retrieve and Upload Kubeconfig:Kubernetes Token Setup.Extract your cluster access config file (typically found at ~/.kube/config). Create a new Secret File credential item inside Jenkins, upload the config file, and give it the credential ID name kubeconfig-credentials.

2.Configure Registry Credentials:Docker Hub Authentication.Go to Jenkins Credentials Manager and save your Docker Hub username and security access token as a Username with Password object. Name the system ID tracker string dockerhub-credentials.

3.Provision the Pipeline Object:Jenkins Project Mapping.Create a new Pipeline item in Jenkins. Set the configuration source option to Pipeline script from SCM, choose Git, and point the destination path directly to your project repository branch.

4.Execute Initial Boot Build:Trigger Activation.Run the pipeline manually exactly once. Jenkins must read the Jenkinsfile from your repository during a baseline run before it can register the internal triggers { cron(...) } mechanism into its system scheduler scheduler.