(ns wc-api-clj.v3.orders
  (:require [wc-api-clj.core :as woo]))

(defn create-order
  "Create order(s) and it's needed basic authentication."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/orders")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-order-by-id
  "Retrieve order by ID and it need basic authentication."
  [{:keys [url consumer_key consumer_secret order exception insecure]}]
  (try (woo/get-req-auth {:siteurl url
                          :uri (str "/wp-json/wc/v3/orders/" order)
                          :username consumer_key
                          :password consumer_secret
                          :exception (not (not exception))
                          :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-orders
  "Retrieve all orders and it need basic authentication."
  [{:keys [url consumer_key consumer_secret exception insecure]}]
  (try (woo/get-req-auth {:siteurl url
                          :uri "/wp-json/wc/v3/orders/"
                          :username consumer_key
                          :password consumer_secret
                          :exception (not (not exception))
                          :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn update-order-by-id
  "Create order(s) and it's needed basic authentication."
  [{:keys [url consumer_key consumer_secret order body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/orders/" order)
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-order-by-id
  "Retrieve order by ID and it need basic authentication."
  [{:keys [url consumer_key consumer_secret order exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/orders/" order "?force=true")
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn orders-batch-update
  "Batch update order(s) and it need basic authentication."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/orders/batch")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
