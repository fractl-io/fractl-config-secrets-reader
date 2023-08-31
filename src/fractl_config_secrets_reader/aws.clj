(ns fractl-config-secrets-reader.aws
  (:require [fractl-config-secrets-reader.protocol :as proto])
  (:import [com.amazonaws.auth.profile ProfileCredentialsProvider]
           [com.amazonaws.services.secretsmanager AWSSecretsManagerClientBuilder]
           [com.amazonaws.services.secretsmanager.model GetSecretValueRequest]))

(def aws-credentials (ProfileCredentialsProvider.))

(def secrets-manager
  (-> (AWSSecretsManagerClientBuilder/standard)
      (.withCredentials aws-credentials)
      (.build)))

(defn fetch-aws-secret [config]
  (let [secret-name (-> config :secret-config :secret-name)
        get-secret-request (GetSecretValueRequest.)]
    (.setSecretId get-secret-request secret-name)
    (let [get-secret-response (.getSecretString (.getSecretValue secrets-manager get-secret-request))]
      (merge
       (read-string get-secret-response)
       config))))

(deftype AwsSecret [config]
  proto/ConfigSecretManager
  (fetch-config [_] (fetch-aws-secret config)))
