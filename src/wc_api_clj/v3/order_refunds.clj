(ns wc-api-clj.v3.order-refunds
  "Helper functions to communicate with the WooCommerce REST API's order refund endpoints.
  These functions need authentication by `consumer_key` and `consumer_secret`.</br>
  https://woocommerce.github.io/woocommerce-rest-api-docs/#order-refunds"
  (:require [wc-api-clj.core :as woo]
            [wc-api-clj.util :as util]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Order Notes REST API v3 wrapper functions ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-order-refund
  "Create an order refund."
  [{:keys [url consumer_key consumer_secret order api_refund body exception insecure]}]
  (try (woo/post-req {:siteurl url
                      :uri (str "/wp-json/wc/v3/orders/" order "/refunds" (util/edn-to-query-str {:api_refund api_refund}))
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-order-refund-by-id
  "Retrieve an order refund by the order and refund ID."
  [{:keys [url consumer_key consumer_secret order refund exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri (str "/wp-json/wc/v3/orders/" order "/refunds/" refund)
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-order-refunds
  "Retrieve all order refunds."
  [{:keys [url consumer_key consumer_secret order exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri (str "/wp-json/wc/v3/orders/" order "/refunds")
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-order-refund-by-id
  "Delete an order by the order and refund ID."
  [{:keys [url consumer_key consumer_secret order refund exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/orders/" order "/refunds/" refund (util/edn-to-query-str {:force true}))
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
