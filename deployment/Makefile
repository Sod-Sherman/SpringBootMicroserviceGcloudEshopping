#====create static ip address (if it created then apply it to k8s env)
gcloud-apply-static-ip:
	gcloud compute addresses create  web-static-ip --region us-central1

gcloud-delete-static-ip:
	gcloud compute addresses delete web-static-ip --region us-central1

gcloud-show-static-ip:
	gcloud compute addresses describe web-static-ip --region us-central1

kubectl-apply-ingress:
	kubectl apply -f basic-ingress.yaml

kubectl-delete-ingress:
	kubectl delete -f basic-ingress.yaml

docker-push-all:
	cd ../shared && mvn clean && mvn install
	cd ../auth_service && $(MAKE) docker-push
	cd ../orders_service && $(MAKE) docker-push
	cd ../pays_ba_service && $(MAKE) docker-push
	cd ../pays_cc_service && $(MAKE) docker-push
	cd ../pays_pp_service && $(MAKE) docker-push
	cd ../pays_service && $(MAKE) docker-push
	cd ../products_service && $(MAKE) docker-push
	cd ../ship_service && $(MAKE) docker-push
	cd ../stock_service && $(MAKE) docker-push
	cd ../users_service && $(MAKE) docker-push

kubectl-apply-all-services:
	kubectl apply -f ../configs/global-secrets.yaml
	kubectl apply -f ./mysql-deploy/mysql-pv.yaml
	kubectl apply -f ./mysql-deploy/mysql-local-secrets.yml
	kubectl apply -f ./mysql-deploy/mysql-deployment.yaml
	

	kubectl apply -f ../stock_service/manifests/
	kubectl apply -f ../pays_ba_service/manifests/
	kubectl apply -f ../pays_cc_service/manifests/
	kubectl apply -f ../pays_pp_service/manifests/
	kubectl apply -f ../pays_service/manifests/
	kubectl apply -f ../ship_service/manifests/

	kubectl apply -f ../products_service/manifests/mysql-products-service-secret.yaml
	kubectl apply -f ../products_service/manifests/

	kubectl apply -f ../orders_service/manifests/mysql-orders-service-secret.yaml
	kubectl apply -f ../orders_service/manifests/

	kubectl apply -f ../auth_service/manifests/mysql-auth-service-secret.yaml
	kubectl apply -f ../auth_service/manifests/

	kubectl apply -f ../users_service/manifests/mysql-users-service-secret.yaml
	kubectl apply -f ../users_service/manifests/

kubectl-delete-all-services: kubectl-delete-ingress
	kubectl delete -f ../auth_service/manifests/
	kubectl delete -f ../orders_service/manifests/
	kubectl delete -f ../pays_ba_service/manifests/
	kubectl delete -f ../pays_cc_service/manifests/
	kubectl delete -f ../pays_pp_service/manifests/
	kubectl delete -f ../pays_service/manifests/
	kubectl delete -f ../products_service/manifests/
	kubectl delete -f ../ship_service/manifests/
	kubectl delete -f ../stock_service/manifests/
	kubectl delete -f ../users_service/manifests/
	kubectl delete -f ./mysql-deploy/
	kubectl delete -f ../configs/global-secrets.yaml

kubectl-db-init-create:
	./mysql-deploy/initDb.sh

kubectl-db-delete:
	./mysql-deploy/removeDb.sh
