(ns wc-api-clj.util
  "Utility functions for the library."
  (:require [clojure.data.json :as json]
            [clojure.string :as str])
  (:import [org.apache.commons.validator.routines RegexValidator UrlValidator]))

(defmacro varargs
  "Make a properly-tagged Java interop varargs argument. This is basically the same as `into-array` but properly tags
  the result.
    (u/varargs String)
    (u/varargs String [\"A\" \"B\"])"
  {:style/indent 1, :arglists '([klass] [klass xs])}
  [klass & [objects]]
  (vary-meta `(into-array ~klass ~objects)
             assoc :tag (format "[L%s;" (.getCanonicalName ^Class (ns-resolve *ns* klass)))))

(defn is-url
  "Is `url` a valid HTTP/HTTPS URL string?"
  ^Boolean [url]
  (let [validator (UrlValidator. (varargs String ["http" "https"])
                                 (RegexValidator. "^\\p{Alnum}+([\\.|\\-]\\p{Alnum}+)*(:\\d*)?")
                                 UrlValidator/ALLOW_LOCAL_URLS)]
    (.isValid validator (str url))))
