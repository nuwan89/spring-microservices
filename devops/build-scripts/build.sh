mode=$1
full_image_name=$2
_tag=(${full_image_name//:/ })
image_name=${_tag[0]}
tag=${_tag[1]}

registry=localhost:33000/

echo "Build mode: $mode"
echo "Image: $full_image_name"

pushd ../

if [ $mode = "dev" ]; then
    # rm -rf .build
    # mkdir .build
    cp ../kubernetes/maven-cache/settings.xml .build/
    docker build -t ${registry}${full_image_name} . --network=host --progress=plain
else
    docker build -t ${registry}${full_image_name} . --progress=plain
fi

pushd .build

./remove-images.sh $image_name $tag

pushd ../
echo "Pushing image: $full_image_name"

docker push ${registry}${full_image_name}