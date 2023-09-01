(ns fractl-config-secrets-reader.azure
  (:require [fractl-config-secrets-reader.protocol :as proto])
  (:import [com.azure.data.appconfiguration ConfigurationClientBuilder]))

(defn- paged-iterable->list [paged-iterable]
  (loop [iterator (.iterator paged-iterable)
         acc []]
    (if (.hasNext iterator)
      (recur iterator (conj acc (.next iterator)))
      acc)))

(defn- get-config-list [connection-string]
  (let [client (-> (ConfigurationClientBuilder.)
                   (.connectionString connection-string)
                   (.buildClient))
        settings-list (paged-iterable->list (.listConfigurationSettings client nil))]
    (apply merge
           (map
            (fn [setting]
              {(name (.getKey setting))
               (.getValue setting)})
            settings-list))))

(defn- get-config [config]
  (get-config-list (-> config :secret-config :connection-string)))

(defn- fetch-fractl-config [config]
  (let [connection-string (-> config :secret-config :connection-string)
        client (-> (ConfigurationClientBuilder.)
                   (.connectionString connection-string)
                   (.buildClient))
        fractl-config (.getConfigurationSetting client "FRACTL_CONFIG" nil)]
    (merge (read-string (.getValue fractl-config)) config)))

(deftype AzureAppConfig [config]
  proto/ConfigSecretManager
  (fetch-config [_] (fetch-fractl-config config)))
