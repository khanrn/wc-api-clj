(ns wc-api-clj.v3.products
  (:require [wc-api-clj.core :as woo]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Products REST API v3 helper functions ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-product
  "Create product(s) and it's needed basic authentication."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/products")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-product-by-id
  "Retrieve product by ID and it need basic authentication."
  [{:keys [url consumer_key consumer_secret product exception insecure]}]
  (try (woo/get-req-auth {:siteurl url
                          :uri (str "/wp-json/wc/v3/products/" product)
                          :username consumer_key
                          :password consumer_secret
                          :exception (not (not exception))
                          :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-products
  "Retrieve all products and it need basic authentication."
  [{:keys [url consumer_key consumer_secret exception insecure]}]
  (try (woo/get-req-auth {:siteurl url
                          :uri "/wp-json/wc/v3/products/"
                          :username consumer_key
                          :password consumer_secret
                          :exception (not (not exception))
                          :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn update-product-by-id
  "Create product(s) and it's needed basic authentication."
  [{:keys [url consumer_key consumer_secret product body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/products/" product)
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-product-by-id
  "Retrieve product by ID and it need basic authentication."
  [{:keys [url consumer_key consumer_secret product exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/products/" product "?force=true")
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn products-batch-update
  "Batch update product(s) and it need basic authentication."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/products/batch")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
