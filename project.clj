(defproject charts "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.5.1"]
                 ;; [org.clojure/clojurescript "0.0-2173"]
                 [org.clojure/clojurescript "0.0-2173"
                  :exclusions [org.clojure/google-closure-library
                               org.clojure/google-closure-library-third-party]]
                 [org.clojure/google-closure-library "0.0-20140226-71326067"]
                 [om "0.5.2"]
                 ;; [com.keminglabs/c2 "0.2.3"]
                 ]

  :repositories [["gclosure"
                  "https://oss.sonatype.org/content/repositories/orgclojure-1208/"]
                 ["gclosure-third-party"
                  "https://oss.sonatype.org/content/repositories/orgclojure-1209/"]]

  :plugins [[lein-cljsbuild "1.0.2"]]

  :source-paths ["src"]

  :hooks [leiningen.cljsbuild]

  :cljsbuild {
    :builds [{:id "charts"
              :source-paths ["src"]
              :compiler {
                :output-to "charts.js"
                :output-dir "out"
                :optimizations :none
                :source-map true}}]})
