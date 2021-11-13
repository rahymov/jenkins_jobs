node("docker"){
    stage("Pull"){
        git 'https://github.com/ikambarov/Flaskex-docker'
    }
    
    
    
    withCredentials([usernameColonPassword(credentialsId: 'dockerhub', variable: ''), usernamePassword(credentialsId: 'dockerhub', passwordVariable: 'HUBPASS', usernameVariable: 'HUBUSERNAME')]) {
        
        stage("Build"){
        sh '''
            docker build -t $HUBUSERNAME/flaskex:2.0.0 .
        '''
        }
        stage("Push"){
        sh '''
            docker login -u $HUBUSERNAME -p $HUBPASS
            docker push $HUBUSERNAME/flaskex:2.0.0
        '''
        }
        
        stage("Deploy"){
           sh '''
             docker run -d -p 5000:5000 $HUBUSERNAME/flaskex:2.0.0
           '''
        }
    }
}
