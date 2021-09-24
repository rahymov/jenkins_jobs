if(env.BRANCH_NAME ==~ "dev-.*"){
    environment="dev"
    region="us-east-1"
}
else if(env.BRANCH_NAME ==~ "qa-.*"){
    environment="qa"
    region="us-east-2"
}
else{
    environment="prod"
    region="us-west-2"
}

node("worker1"){
    stage("Pull code"){
        checkout scm
    }

    image_name="$environment-apache-${UUID.randomUUID().toString()}"

    withEnv(["AWS_REGION=$region", "PACKER_AMI_NAME=$image_name"]) {
        withCredentials([usernamePassword(credentialsId: 'aws-key', passwordVariable: 'AWS_SECRET_ACCESS_KEY', usernameVariable: 'AWS_ACCESS_KEY_ID')]) {

            stage("Validate"){
                sh '''
                    packer validate apache.json
                '''
            }

            stage("Build"){
                sh '''
                    packer build apache.json
                '''
            }
        }
    }
}