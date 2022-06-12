mode=$1
image_name=$2
registry=localhost:33000/

echo "Build mode: $mode"
echo "Image: $image_name"

pushd ../

if [ $mode = "dev" ]; then
    # rm -rf .build
    # mkdir .build
    cp ../kubernetes/maven-cache/settings.xml .build/
    docker build -t ${registry}${image_name} . --network=host --progress=plain
else
    docker build -t ${registry}${image_name} . --progress=plain
fi

docker push ${registry}${image_name}