mkdir -p ../discovery-server/.build && cp build-scripts/*.sh ../discovery-server/.build/
echo './build.sh dev "eureka-server:0.0.1"' >> ../discovery-server/.build/dev-build.sh
mkdir -p ../account-manager/.build && cp build-scripts/*.sh ../account-manager/.build/
echo './build.sh dev "account-manager:0.0.1"' >> ../account-manager/.build/dev-build.sh
mkdir -p ../order-manager/.build && cp build-scripts/*.sh ../order-manager/.build/
echo './build.sh dev "order-manager:0.0.1"' >> ../order-manager/.build/dev-build.sh
mkdir -p ../spring-gateway/.build && cp build-scripts/*.sh ../spring-gateway/.build/
echo './build.sh dev "spring-gateway:0.0.1"' >> ../spring-gateway/.build/dev-build.sh