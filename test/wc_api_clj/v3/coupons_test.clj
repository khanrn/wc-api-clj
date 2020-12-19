(ns wc-api-clj.v3.coupons-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.v3.coupons :refer :all]
            [wc-api-clj.core-test :refer [credentials]]
            [wc-api-clj.core :refer :all]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Test for v3/coupons API ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest coupon-v3-api-create-read-update-delete-success-test
  (testing "Testing coupon v3 API for create, read, update and delete."
    (let [{:keys [id amount]}
          (-> {:url (:siteurl credentials)
               :consumer_key (:ckw credentials)
               :consumer_secret (:csw credentials)
               :exception false
               :insecure true
               :body (json/write-str (:coupon_to_create credentials))}
              (create-coupon))]
      (is (< 0 id))
      (is (= "10.00" amount))
      (is (< 0 (-> {:url (:siteurl credentials)
                    :consumer_key (:ckr credentials)
                    :consumer_secret (:csr credentials)
                    :exception true
                    :insecure true}
                   (get-coupons)
                   (first)
                   (:id))))
      (is (= id (-> {:url (:siteurl credentials)
                     :consumer_key (:ckr credentials)
                     :consumer_secret (:csr credentials)
                     :coupon id
                     :exception true
                     :insecure true}
                    (get-coupon-by-id)
                    (:id))))
      (is (= "13.00" (-> {:url (:siteurl credentials)
                          :consumer_key (:ckw credentials)
                          :consumer_secret (:csw credentials)
                          :coupon id
                          :body (json/write-str {:amount "13"})
                          :exception false
                          :insecure true}
                         (update-coupon-by-id)
                         (:amount))))
      (is (= id (-> {:url (:siteurl credentials)
                     :consumer_key (:ckw credentials)
                     :consumer_secret (:csw credentials)
                     :coupon id
                     :exception false
                     :insecure true}
                    (delete-coupon-by-id)
                    (:id)))))))

(deftest coupon-v3-api-batch-update-success-test
  (testing "Testing coupon v3 API for batch update."
    (let [resp (coupons-batch-operations {:url (:siteurl credentials)
                                          :consumer_key (:ckw credentials)
                                          :consumer_secret (:csw credentials)
                                          :exception false
                                          :insecure true
                                          :body (json/write-str {:create [{:code "20off"
                                                                           :discount_type "percent"
                                                                           :amount "20"
                                                                           :individual_use true
                                                                           :exclude_sale_items true
                                                                           :minimum_amount "100.00"}]})})]
      (is (< 0 (:id (first (:create resp)))))
      (is (= "20.00" (:amount (first (:create resp)))))
      (is (= "20off" (:code (first (:create resp)))))
      (is (= "percent" (:discount_type (first (:create resp)))))
      (is (= "50.00" (-> {:url (:siteurl credentials)
                          :consumer_key (:ckw credentials)
                          :consumer_secret (:csw credentials)
                          :exception false
                          :insecure true
                          :body (json/write-str {:update [{:id (:id (first (:create resp)))
                                                           :minimum_amount "50.00"}]})}
                         (coupons-batch-operations)
                         (:update)
                         (first)
                         (:minimum_amount))))
      (is (= (:id (first (:create resp))) (-> {:url (:siteurl credentials)
                                               :consumer_key (:ckw credentials)
                                               :consumer_secret (:csw credentials)
                                               :exception false
                                               :insecure true
                                               :body (json/write-str {:delete [(:id (first (:create resp)))]})}
                                              (coupons-batch-operations)
                                              (:delete)
                                              (first)
                                              (:id)))))))
