(ns wc-api-clj.core
  "Wrapper functions around [[wc-api-clj.rest]] functions to communicate
  with the **WooCommerce** REST API endpoints."
  (:require [wc-api-clj.rest :as wp-rest]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; GET request wrapper function(s) ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn get-req
  "GET request wrapper function with authentication. Must need consumer key/secret."
  [{:keys [siteurl uri query username password insecure exception]}]
  (if (and siteurl uri username password)
    (:body (wp-rest/wp-get {:url (str siteurl uri query)
                            :options {:accept :json
                                      :throw-exceptions (not (not exception))
                                      :insecure? (not (not insecure))
                                      :basic-auth [username password]}}))
    (throw (Exception. "You should supply the function with proper arguments"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; POST request wrapper function(s) ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn post-req
  "POST request wrapper function with authentication. Must need consumer key/secret."
  [{:keys [siteurl uri username password body insecure exception]}]
  (if (and siteurl uri username password body)
    (:body (wp-rest/wp-post {:url (str siteurl uri)
                             :options {:basic-auth [username password]
                                       :body body
                                       :headers {"X-Api-Version" "2"}
                                       :content-type :json
                                       :throw-exceptions (not (not exception))
                                       :insecure? (not (not insecure))
                                       :accept :json}}))
    (throw (Exception. "You should supply the function with all the arguments needed."))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; DELETE request wrapper function(s) ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn delete-req
  "DELETE request wrapper function. Must need consumer key/secret."
  [{:keys [siteurl uri query username password insecure exception]}]
  (if (and siteurl uri username password)
    (:body (wp-rest/wp-delete {:url (str siteurl uri query)
                               :options {:accept :json
                                         :throw-exceptions (not (not exception))
                                         :insecure? (not (not insecure))
                                         :basic-auth [username password]}}))
    (throw (Exception. "You should supply the function with all the arguments needed."))))
