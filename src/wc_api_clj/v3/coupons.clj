(ns wc-api-clj.v3.coupons
  "Helper functions to communicate with the WooCommerce REST API's coupons endpoints.
  These functions need authentication by `consumer_key` and `consumer_secret`.</br>
  https://woocommerce.github.io/woocommerce-rest-api-docs/#coupons"
  (:require [wc-api-clj.core :as woo]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Coupons REST API v3 helper functions ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn create-coupon
  "Create coupon(s)."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:siteurl url
                      :uri "/wp-json/wc/v3/coupons"
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-coupon-by-id
  "Retrieve a coupon by the coupon's ID."
  [{:keys [url consumer_key consumer_secret coupon exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri (str "/wp-json/wc/v3/coupons/" coupon)
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-coupons
  "Retrieve all coupons."
  [{:keys [url consumer_key consumer_secret exception insecure]}]
  (try (woo/get-req {:siteurl url
                     :uri "/wp-json/wc/v3/coupons/"
                     :username consumer_key
                     :password consumer_secret
                     :exception (not (not exception))
                     :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn update-coupon-by-id
  "Update a coupon by the coupon's ID."
  [{:keys [url consumer_key consumer_secret coupon body exception insecure]}]
  (try (woo/post-req {:siteurl url
                      :uri (str "/wp-json/wc/v3/coupons/" coupon)
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-coupon-by-id
  "Delete a coupon by the coupon's ID."
  [{:keys [url consumer_key consumer_secret coupon exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/coupons/" coupon "?force=true")
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn coupons-batch-operations
  "CRUD multiple coupons in a batch."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:siteurl url
                      :uri "/wp-json/wc/v3/coupons/batch"
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
