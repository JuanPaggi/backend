mvn clean package
cd target
scp byo-backend-alpha.war tomcatdeploy@144.76.43.230:/home/tomcatdeploy/buscoyofrezco.war
ssh tomcatdeploy@144.76.43.230 -f /home/tomcatdeploy/deploy-byo.sh