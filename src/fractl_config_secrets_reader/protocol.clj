(ns fractl-config-secrets-reader.protocol)

(defprotocol ConfigSecretManager
  (fetch-config [self]))
