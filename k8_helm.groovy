def podtemplate = '''
apiVersion: v1
kind: Pod
metadata:
  labels:
    type: k8-tools
spec:
  serviceAccountName: k8-tools-sa
  containers:
  - image: ikambarov/k8-tools
    name: k8-tools
    args:
    - sleep
    - "100000"
'''

podTemplate(label: 'k8-tools', name: 'k8-tools', namespace: 'tools', yaml: podtemplate, showRawYaml: false) {
    node("k8-tools"){
        container("k8-tools"){
            stage("Pull"){
              git 'https://github.com/rahymov/flask-chart.git'
            }
            stage("deploy"){
                sh '''
                    helm upgrade --install my .  -n default
                '''
            }
        }
    }
}