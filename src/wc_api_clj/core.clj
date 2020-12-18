(ns wc-api-clj.core
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; GET request wrapper function(s) ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-req
  "GET request wrapper function without authentication."
  [{:keys [siteurl uri query insecure exception]}]
  (if (and siteurl uri)
    (:body (wp-get {:url (str siteurl uri query)
                    :options {:accept :json
                              :throw-exceptions (not (not exception))
                              :insecure? (not (not insecure))}}))
    (throw (Exception. "You should supply the function with your site url."))))

(defn get-req-auth
  "GET request wrapper function with authentication. Must need username/password or consumer key/secret."
  [{:keys [siteurl uri query username password insecure exception]}]
  (if (and siteurl uri username password)
    (:body (wp-get {:url (str siteurl uri query)
                    :options {:accept :json
                              :throw-exceptions (not (not exception))
                              :insecure? (not (not insecure))
                              :basic-auth [username password]}}))
    (throw (Exception. "You should supply the function with proper arguments"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; POST request function ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn wp-post
  "Conduct a POST request to the server."
  [{:keys [url options]}]
  (let [{:keys [headers links body]} (http/post url options)]
    {:links links :headers headers :body (json/read-str body :key-fn keyword)}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; POST request wrapper function(s) ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn post-req
  "POST request wrapper function with authentication. Must need username/password or consumer key/secret."
  [{:keys [url username password body insecure exception]}]
  (if (and url username password body)
    (:body (wp-post {:url url :options {:basic-auth [username password]
                                        :body body
                                        :headers {"X-Api-Version" "2"}
                                        :content-type :json
                                        :throw-exceptions (not (not exception))
                                        :insecure? (not (not insecure))
                                        :accept :json}}))
    (throw (Exception. "You should supply the function with all the arguments needed."))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; DELETE request function ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn wp-delete
  "Conduct a DELETE request to the server."
  [{:keys [url options]}]
  (let [{:keys [headers links body]} (http/delete url options)]
    {:links links :headers headers :body (json/read-str body :key-fn keyword)}))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; DELETE request wrapper function(s) ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn delete-req
  "GET request wrapper function with authentication. Must need username/password or consumer key/secret"
  [{:keys [siteurl uri query username password insecure exception]}]
  (if (and siteurl uri username password)
    (:body (wp-delete {:url (str siteurl uri query)
                       :options {:accept :json
                                 :throw-exceptions (not (not exception))
                                 :insecure? (not (not insecure))
                                 :basic-auth [username password]}}))
    (throw (Exception. "You should supply the function with all the arguments needed."))))
