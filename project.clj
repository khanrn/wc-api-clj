(defproject wc-api-clj "0.1.0-SNAPSHOT"
  :description "WooCommerce REST API wrapper written in Clojure."
  :url "https://github.com/codemascot/wc-api-clj"
  :scm {:name "git"
        :url "https://github.com/codemascot/wc-api-clj"}
  :license {:name "EPL-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [clj-http "3.1.0"]
                 [org.clojure/data.json "1.0.0"]]
  :repl-options {:init-ns wc-api-clj.core})
