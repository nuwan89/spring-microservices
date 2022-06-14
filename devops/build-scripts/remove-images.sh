img=$1
tag=$2

reference=$(curl -v --silent -H "Accept: application/vnd.docker.distribution.manifest.v2+json" -X GET http://localhost:33000/v2/$img/manifests/$2 2>&1 | grep Docker-Content-Digest | awk '{print ($3)}')

curl -v --silent -H "Accept: application/vnd.docker.distribution.manifest.v2+json" -X DELETE http://localhost:33000/v2/$img/manifests/$reference

docker exec registry sh -c "bin/registry garbage-collect  /etc/docker/registry/config.yml"


