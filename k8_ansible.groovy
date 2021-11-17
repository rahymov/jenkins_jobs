def podtemplate = '''
apiVersion: v1
kind: Pod
metadata:
  name: ansible
spec:
  containers:
  - image: ikambarov/ansible
    name: ansible
    args:
    - sleep
    - "10000000"
'''

podTemplate(label: 'ansible', name: 'ansible', namespace: 'tools', yaml: podtemplate, showRawYaml: false) {
    node("ansible"){
        container("ansible"){
            stage("Test"){
                sh "ansible --version"
            }
        }
    }
}