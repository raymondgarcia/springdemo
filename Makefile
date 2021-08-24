SHELL := /bin/bash

PROJECT_NAME=userservice
export PROJECT_NAME
LOCAL_IMAGE=$(shell docker images | grep $(PROJECT_NAME) | awk '{ print $$3 }')


.PHONY: remove-local-image
remove-local-image:
ifneq ($(LOCAL_IMAGE),)
	docker rmi $(LOCAL_IMAGE) --force
else
	$(info no local image to remove)
endif

.PHONY: build-local-docker
build-local-docker: remove-local-image
	docker build --build-arg JAR_FILE=build/libs/\*.jar -t springio/gs-spring-boot-docker  --tag $(PROJECT_NAME) .

.PHONY: run-local-docker
run-local-docker:
	docker rm --force $(PROJECT_NAME)
	docker run -d -p 5000:5000 --name $(PROJECT_NAME) $(PROJECT_NAME)

.PHONY: ssh-local-docker
ssh-local-docker:
	docker exec -it $(PROJECT_NAME) /bin/bash


.PHONY: run-local
run-local:
	./gradlew build
	java -jar build/libs/userservice-0.0.1-SNAPSHOT.jar

