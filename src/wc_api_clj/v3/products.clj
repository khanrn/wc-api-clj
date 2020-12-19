(ns wc-api-clj.v3.products
  "Helper functions to communicate with the WooCommerce REST API's products endpoints.
  These functions need authentication by `consumer_key` and `consumer_secret`.</br>
  https://woocommerce.github.io/woocommerce-rest-api-docs/#products"
  (:require [wc-api-clj.core :as woo]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Products REST API v3 helper functions ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-product
  "Create product(s)."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/products")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-product-by-id
  "Retrieve product by the product's ID."
  [{:keys [url consumer_key consumer_secret product exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri (str "/wp-json/wc/v3/products/" product)
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-products
  "Retrieve all products."
  [{:keys [url consumer_key consumer_secret exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri "/wp-json/wc/v3/products/"
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn update-product-by-id
  "Update a product by the product's ID."
  [{:keys [url consumer_key consumer_secret product body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/products/" product)
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-product-by-id
  "Delete a product by the porduct's ID."
  [{:keys [url consumer_key consumer_secret product exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/products/" product "?force=true")
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn products-batch-operations
  "CRUD multiple products in a batch."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/products/batch")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
