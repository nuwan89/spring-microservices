pushd ../
rm -rf .build
mkdir .build
# cp ../devops/build-scripts/build.sh ../devops/build-scripts/maven-build.sh .build/
cp ../devops/build-scripts/*.sh .build/
pushd .build./build.sh dev "order-manager:0.0.1"
