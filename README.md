# Pipeline Core Shared Library

## Requisits
- helm v3.3.4+
- kubectl v1.19.3+
- minikube v1.14.0+
## Install and Configure
* This install will use jenkins 2.274 and jenkins helm chart 3.0.14
Clone "pipeline-core" repository
```sh
$ git clone https://github.com/febarbosa182/pipeline-core.git
```
Add jenkins helm oficial repository:
```śh
$ helm repo add jenkins https://charts.jenkins.io
```
Start a minikube 
```sh
$ minikube start --memory=4g
```
Create a clusterrolebinding for serviceaccounts
```sh
$ kubectl create clusterrolebinding serviceaccounts-cluster-admin \
  --clusterrole=cluster-admin \
  --group=system:serviceaccounts
```
> **_NOTE:_**  It's not recommended for production clusters, this is only for demo

Install jenkins with shared libraries and plugins installed, inside of "pipeline-core" repository folder:
```sh
$ helm repo update
$ export JENKINS_CHART_VERSION=3.0.14
$ helm upgrade --install jenkins jenkins/jenkins \
    --version $JENKINS_CHART_VERSION \
    --values jenkins-config.yaml
```
It can take a while until it finishes the startup

Foward Jenkins to your localhost on port 8080
```sh
$ kubectl --namespace default port-forward svc/jenkins 8080:8080
```
Now you can access your jenkins on http://localhost:8080
User: admin
Pass: admin

A Jenkins Job with name "simple-app" is set in home page and configured with the  shared libraries call, but before executing it, you **_MUST_** access menu option "Manage Jenkins">"In-process Script Approval" and approve the pipeline script, or access through url http://localhost:8080/scriptApproval/ and approve the pipeline script.

All set up, now just run the "Simple App" job and enjoy 🤩
After pipeline execution you can access de deployed application

Foward our simple application
```sh
$ kubectl --namespace default port-forward \
    svc/pipeline-simple-app 8081:80
```
Now you can access our simple aplication on http://localhost:8081