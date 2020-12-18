(ns wc-api-clj.v3.coupons
  (:require [wc-api-clj.core :as woo]))

(defn create-coupon
  "Create coupon(s) and it need basic authentication."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/coupons")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-coupon-by-id
  "Retrieve coupon by ID and it need basic authentication."
  [{:keys [url consumer_key consumer_secret coupon exception insecure]}]
  (try (woo/get-req-auth {:siteurl url
                          :uri (str "/wp-json/wc/v3/coupons/" coupon)
                          :username consumer_key
                          :password consumer_secret
                          :exception (not (not exception))
                          :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn get-coupons
  "Retrieve all coupons and it need basic authentication."
  [{:keys [url consumer_key consumer_secret exception insecure]}]
  (try (woo/get-req-auth {:siteurl url
                          :uri "/wp-json/wc/v3/coupons/"
                          :username consumer_key
                          :password consumer_secret
                          :exception (not (not exception))
                          :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn update-coupon-by-id
  "Create coupon(s) and it's needed basic authentication."
  [{:keys [url consumer_key consumer_secret coupon body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/coupons/" coupon)
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn delete-coupon-by-id
  "Retrieve coupon by ID and it need basic authentication."
  [{:keys [url consumer_key consumer_secret coupon exception insecure]}]
  (try (woo/delete-req {:siteurl url
                        :uri (str "/wp-json/wc/v3/coupons/" coupon "?force=true")
                        :username consumer_key
                        :password consumer_secret
                        :exception (not (not exception))
                        :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))

(defn coupons-batch-update
  "Batch update coupon(s) and it need basic authentication."
  [{:keys [url consumer_key consumer_secret body exception insecure]}]
  (try (woo/post-req {:url (str url "/wp-json/wc/v3/coupons/batch")
                      :username consumer_key
                      :password consumer_secret
                      :body body
                      :exception (not (not exception))
                      :insecure (not (not insecure))})
       (catch clojure.lang.ExceptionInfo e (str (.getMessage e)))))
