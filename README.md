# Pipeline Core Shared Library

## Requisits

- helm v3.3.4+
- kubectl v1.19.3+
- minikube v1.14.0+

## Install and Configure

* This install will use jenkins 2.263.1 and jenkins helm chart 3.0.8

Add jenkins helm oficial repository:
```śh
helm repo add jenkins https://charts.jenkins.io
```

Start a minikube
```sh
minikube start --memory=4g
```

Install jenkins with shared libraries and publins installed
```sh
helm repo update
export JENKINS_CHART_VERSION=3.0.8
helm upgrade --install jenkins jenkins/jenkins --version $JENKINS_CHART_VERSION --values jenkins-config.yaml
```
It will take a while until it finish the startup

Foward Jenkins to your localhost on port 8080
```sh
kubectl --namespace default port-forward svc/jenkins 8080:8080
```
<b>
User: admin
Pass: admin
</b>

A job with a simple app and libraries call is set in home page but before execute is you <b>MUST<b>: Go in "Manage Jenkins">"In-process Script Approval" and Approve the pipeline script, or access through url http://localhost:8080/scriptApproval/ and Approve the pipeline script.

All set up, now just run the "Simple App" job and enjoy 🤩