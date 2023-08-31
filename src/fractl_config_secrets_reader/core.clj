(ns fractl-config-secrets-reader.core
  (:require [fractl-config-secrets-reader.azure :as azure]
            [fractl-config-secrets-reader.protocol :as proto]
            [fractl-config-secrets-reader.aws :as aws]))

(defn- get-secret-manager [config]
  (case (-> config :secret-config :type)
    :azure-app-config (azure/->AzureAppConfig config)
    :aws-secret-config (aws/->AwsSecret config)
    nil))

(defn read-secret-config [config]
  (if-let [secret-manager (get-secret-manager config)]
    (proto/fetch-config secret-manager)
    config))
