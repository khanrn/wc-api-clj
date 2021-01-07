(defproject org.clojars.codemascot/wc-api-clj "0.1.3"
  :description "WooCommerce REST API client written in Clojure."
  :url "https://github.com/codemascot/wc-api-clj"
  :scm {:name "git"
        :url "https://github.com/codemascot/wc-api-clj"}
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.1.0"]
                 [org.clojure/data.json "1.0.0"]
                 [commons-validator/commons-validator "1.7"]]
  :plugins [[lein-codox "0.10.7"]]
  :codox {:project {:name "wc-api-clj"}
          :output-path "docs/html"
          :metadata {:doc "TODO: Add docstring"
                     :doc/format :markdown}}
  :repl-options {:init-ns wc-api-clj.core})
