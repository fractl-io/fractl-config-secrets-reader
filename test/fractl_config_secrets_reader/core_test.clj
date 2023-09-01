(ns fractl-config-secrets-reader.core-test
  (:require [clojure.test :refer :all]
            [fractl-config-secrets-reader.core :refer :all]))

(def config-test-aws
  {:secret-config
   {:type :aws-secret-config 
    :secret-name "fractl-test-secrets"}})

(def config-test-azure
  {:secret-config
   {:type :azure-app-config
    :connection-string "Endpoint=https://fractl-test.azconfig.io;Id=ByfR;Secret=7cmhKJ6Lp2mN4goC75wrj+mFVnZz1O8+ixZrpmONg7o="}})

(deftest a-test
  (testing "AWS Fetch"
    (let [config (read-secret-config config-test-aws)]
      (println "Config after fetching AWS secret: " config)
      (is (map? config))))
  
  (testing "Azure Fetch"
    (let [config (read-secret-config config-test-azure)]
      (println "Config after fetching Azure secret: " config)
      (is (map? config)))))
