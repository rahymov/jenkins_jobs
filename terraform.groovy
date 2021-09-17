node("worker1"){
  stage("version"){
    sh "terraform version"
  }
}