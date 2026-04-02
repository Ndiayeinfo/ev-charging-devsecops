pipeline {
  agent any

  options {
    timestamps()
  }

  environment {
    MAVEN_CLI_OPTS = '-B -Dmaven.test.failure.ignore=false'
    MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
    GITLEAKS_VERSION = '8.21.2'
    MAVEN_IMAGE = 'maven:3.9.9-eclipse-temurin-17'
    GITLEAKS_IMAGE = 'zricethezav/gitleaks:v8.21.2'
  }

  stages {
    stage('Checkout') {
      steps {
        checkout scm
      }
    }

    stage('Build & Test (Maven)') {
      steps {
        script {
          docker.image(env.MAVEN_IMAGE).inside {
            sh "mvn -q -f common-ms/pom.xml install"
            sh "mvn -q -f station-ms/pom.xml test"
            sh "mvn -q -f session-ms/pom.xml test"
            sh "mvn -q -f billing-ms/pom.xml test"
          }
        }
      }
    }

    stage('Secrets scan (Gitleaks)') {
      steps {
        script {
          docker.image(env.GITLEAKS_IMAGE).inside("--entrypoint=''") {
            sh "gitleaks version"
            sh "gitleaks detect --source . --no-git --report-format sarif --report-path gitleaks.sarif --redact || true"
          }
        }
      }
      post {
        always {
          archiveArtifacts artifacts: 'gitleaks.sarif', allowEmptyArchive: true
        }
      }
    }

    stage('SCA (OWASP Dependency-Check)') {
      steps {
        script {
          docker.image(env.MAVEN_IMAGE).inside {
            sh "mvn -q -f common-ms/pom.xml install"
            sh "mvn -q -f station-ms/pom.xml org.owasp:dependency-check-maven:check -Dformat=HTML -DoutputDirectory=dependency-check/station-ms || true"
            sh "mvn -q -f session-ms/pom.xml org.owasp:dependency-check-maven:check -Dformat=HTML -DoutputDirectory=dependency-check/session-ms || true"
            sh "mvn -q -f billing-ms/pom.xml org.owasp:dependency-check-maven:check -Dformat=HTML -DoutputDirectory=dependency-check/billing-ms || true"
          }
        }
      }
      post {
        always {
          archiveArtifacts artifacts: 'dependency-check/**/*', allowEmptyArchive: true
        }
      }
    }

    stage('SAST / Qualité (SonarQube)') {
      when {
        expression { return env.SONARQUBE_ENABLED?.toBoolean() }
      }
      steps {
        script {
          withSonarQubeEnv('SonarQube') {
            withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
              docker.image(env.MAVEN_IMAGE).inside {
                sh "mvn -q -f common-ms/pom.xml install"
                sh "mvn ${env.MAVEN_CLI_OPTS} -f station-ms/pom.xml -Dsonar.login=$SONAR_TOKEN -Dsonar.projectKey=ev-charging-devsecops:station-ms verify sonar:sonar"
                sh "mvn ${env.MAVEN_CLI_OPTS} -f session-ms/pom.xml -Dsonar.login=$SONAR_TOKEN -Dsonar.projectKey=ev-charging-devsecops:session-ms verify sonar:sonar"
                sh "mvn ${env.MAVEN_CLI_OPTS} -f billing-ms/pom.xml -Dsonar.login=$SONAR_TOKEN -Dsonar.projectKey=ev-charging-devsecops:billing-ms verify sonar:sonar"
              }
            }
          }
        }
      }
    }
  }

  post {
    always {
      junit allowEmptyResults: true, testResults: '**/target/surefire-reports/*.xml'
    }
  }
}

