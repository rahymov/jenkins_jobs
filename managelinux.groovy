node('worker1'){
    stage("Install Epel"){
        withCredentials([sshUserPrivateKey(credentialsId: 'masterkey', keyFileVariable: 'SSHKEY', usernameVariable: 'SSHUSERNAME')]) {
            sh '''
                ssh -o StrictHostKeyChecking=no -i $SSHKEY root@137.184.97.229 "yum install epel-release -y"
            '''
        }
    }
}