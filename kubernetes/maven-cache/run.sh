docker run --name archiva -p 33001:8080 -d --restart=always xetusoss/archiva:v2.2.8

# docker network connect kind archiva => not needed for dev. may be for k8s maven build