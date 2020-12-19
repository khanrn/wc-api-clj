(ns wc-api-clj.rest
  "Basic functions to communicate with the **WordPress** REST API endpoints.
  These functions can be used for **WordPress** REST API communication as well."
  (:require [clj-http.client :as http]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;
;; GET request function ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn wp-get
  "Conduct a GET request to the server."
  [{:keys [url options]}]
  (let [{:keys [headers links body]} (http/get url options)]
    {:links links :headers headers :body (json/read-str body :key-fn keyword)}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; POST request function ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn wp-post
  "Conduct a POST request to the server."
  [{:keys [url options]}]
  (let [{:keys [headers links body]} (http/post url options)]
    {:links links :headers headers :body (json/read-str body :key-fn keyword)}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; DELETE request function ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn wp-delete
  "Conduct a DELETE request to the server."
  [{:keys [url options]}]
  (let [{:keys [headers links body]} (http/delete url options)]
    {:links links :headers headers :body (json/read-str body :key-fn keyword)}))
