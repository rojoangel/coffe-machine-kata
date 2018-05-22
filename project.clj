(defproject coffe-machine-kata "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [greenpowermonitor/test-doubles "0.1.3-SNAPSHOT"] ]
  :main ^:skip-aot coffe-machine-kata.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
