(ns wc-api-clj.v3.order-notes
  "Helper functions to communicate with the WooCommerce REST API's order note endpoints.
  These functions need authentication by `consumer_key` and `consumer_secret`.</br>
  https://woocommerce.github.io/woocommerce-rest-api-docs/#order-notes"
  (:require [wc-api-clj.core :as woo]
            [wc-api-clj.util :as util]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Order Notes REST API v3 wrapper functions ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-order-note
  "Create an order note."
  [{:keys [url consumer_key consumer_secret order body exception insecure]}]
  (try (woo/post-req {:siteurl url
                      :uri (str "/wp-json/wc/v3/orders/" order "/notes")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-order-note-by-id
  "Retrieve an order note by the order and note ID."
  [{:keys [url consumer_key consumer_secret order note exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri (str "/wp-json/wc/v3/orders/" order "/notes/" note)
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-order-notes
  "Retrieve all order notes."
  [{:keys [url consumer_key consumer_secret order exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri (str "/wp-json/wc/v3/orders/" order "/notes")
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-order-note-by-id
  "Delete an order by the order and note ID."
  [{:keys [url consumer_key consumer_secret order note exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/orders/" order "/notes/" note (util/edn-to-query-str {:force true}))
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
