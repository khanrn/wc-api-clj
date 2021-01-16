(ns wc-api-clj.v3.order-refunds-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.v3.orders :refer :all]
            [wc-api-clj.v3.order-refunds :refer :all]
            [wc-api-clj.core-test :refer [credentials]]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Test for v3/order-refunds API ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest order-refund-v3-api-create-read-delete-success-test
  (testing "Testing order-refund v3 API for create, read and delete."
    (let [order (-> {:url (:siteurl credentials)
                     :consumer_key (:ckw credentials)
                     :consumer_secret (:csw credentials)
                     :exception false
                     :insecure true
                     :body (json/write-str (:order_to_create credentials))}
                    (create-order))]
      (is (< 0 (:id order)))
      (let [refund (-> {:url (:siteurl credentials)
                        :consumer_key (:ckw credentials)
                        :consumer_secret (:csw credentials)
                        :exception false
                        :order (:id order)
                        :api_refund false
                        :insecure true
                        :body (json/write-str {:refund_to_create credentials})}
                       create-order-refund)]
        (is (< 0 (:id refund)))
        (is (< 0 (-> {:url (:siteurl credentials)
                      :consumer_key (:ckr credentials)
                      :consumer_secret (:csr credentials)
                      :order (:id order)
                      :exception false
                      :insecure true}
                     get-order-refunds
                     first
                     :id)))
        (is (= (:id refund) (-> {:url (:siteurl credentials)
                                 :consumer_key (:ckr credentials)
                                 :consumer_secret (:csr credentials)
                                 :order (:id order)
                                 :refund (:id refund)
                                 :exception false
                                 :insecure true}
                                get-order-refund-by-id
                                :id)))
        (is (= (:id refund) (-> {:url (:siteurl credentials)
                                 :consumer_key (:ckw credentials)
                                 :consumer_secret (:csw credentials)
                                 :order (:id order)
                                 :refund (:id refund)
                                 :exception false
                                 :insecure true}
                                delete-order-refund-by-id
                                :id))))
      (is (= (:id order) (-> {:url (:siteurl credentials)
                              :consumer_key (:ckw credentials)
                              :consumer_secret (:csw credentials)
                              :order (:id order)
                              :exception false
                              :insecure true}
                             delete-order-by-id
                             :id))))))
