FROM jenkins/jenkins:lts
USER root
RUN apt-get -y update && \
    apt-get -y install \
     apt-transport-https \
     ca-certificates \
     curl \
     gnupg2 \
     software-properties-common && \
     curl -fsSL https://download.docker.com/linux/$(. /etc/os-release; echo "$ID")/gpg \
          | apt-key add - &&\
   add-apt-repository \
     "deb [arch=amd64] https://download.docker.com/linux/$(. /etc/os-release; echo "$ID") \
      $(lsb_release -cs) \
      stable"
RUN apt-get  -y update && apt-get -y install docker-ce && adduser jenkins root && adduser jenkins docker
RUN groupadd  -g 993 dockerce && adduser jenkins dockerce
USER jenkins
