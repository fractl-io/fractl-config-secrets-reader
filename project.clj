(defproject com.github.fractl-io/fractl-config-secrets-reader "0.1.0"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"]
                 [com.azure/azure-data-appconfiguration "1.3.0"]
                 [com.amazonaws/aws-java-sdk-secretsmanager "1.12.540"]]
  :main ^:skip-aot fractl-config-secrets-reader.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all
                       :jvm-opts ["-Dclojure.compiler.direct-linking=true"]}})
