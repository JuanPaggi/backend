mvn clean package
cd target
scp byo-alpha.war deployer@138.68.59.180:/home/deployer/buscoyofrezco.war
ssh deployer@138.68.59.180 /home/deployer/deploy-byo.sh