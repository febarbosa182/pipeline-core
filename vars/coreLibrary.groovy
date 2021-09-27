import br.com.dynamic.assembler.Assembler

def call(body){

    Assembler assembler = new Assembler()

    def jobRunId = UUID.randomUUID().toString()

    podTemplate(
        yaml: """
apiVersion: v1
kind: Pod
metadata:
  labels:
    job-run-id: ${jobRunId}
    jenkins-component: agent
    jenkins/jenkins-jenkins-agent: "true"
spec:
  affinity:
    podAffinity:
      requiredDuringSchedulingIgnoredDuringExecution:
      - labelSelector:
          matchExpressions:
          - key: job-run-id
            operator: In
            values:
            - ${jobRunId}
        topologyKey: "kubernetes.io/hostname"
            """,
        workspaceVolume: dynamicPVC(requestsSize: "2Gi", storageClassName: "ceph-filesystem", accessModes: "ReadWriteMany")
    ) {
        node(POD_LABEL) {        
            stage('Assembler'){
                container("jnlp"){
                    assembler.compose(this)
                }
            }

            assembler.runSteps(this)
        }
    }
}