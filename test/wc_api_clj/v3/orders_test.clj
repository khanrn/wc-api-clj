(ns wc-api-clj.v3.orders-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.v3.orders :refer :all]
            [wc-api-clj.core-test :refer [credentials]]
            [wc-api-clj.core :refer :all]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Test for v3/orders API ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest order-v3-api-create-read-update-delete-success-test
  (testing "Testing order v3 API for create, read, update and delete."
    (let [{:keys [id shipping_total shipping_lines]}
          (-> {:url (:siteurl credentials)
               :consumer_key (:ckw credentials)
               :consumer_secret (:csw credentials)
               :exception false
               :insecure true
               :body (json/write-str (:order_to_create credentials))}
              (create-order))]
      (is (< 0 id))
      (is (= "10.00" shipping_total))
      (is (< 0 (-> {:url (:siteurl credentials)
                    :consumer_key (:ckr credentials)
                    :consumer_secret (:csr credentials)
                    :exception false
                    :insecure true}
                   (get-orders)
                   (first)
                   (:id))))
      (is (= id (-> {:url (:siteurl credentials)
                     :consumer_key (:ckr credentials)
                     :consumer_secret (:csr credentials)
                     :order id
                     :exception true
                     :insecure true}
                    (get-order-by-id)
                    (:id))))
      (is (= "13.77" (-> {:url (:siteurl credentials)
                          :consumer_key (:ckw credentials)
                          :consumer_secret (:csw credentials)
                          :order id
                          :body (json/write-str {:shipping_lines [{:id (:id (first shipping_lines))
                                                                   :total "13.77"}]})
                          :exception false
                          :insecure true}
                         (update-order-by-id)
                         (:shipping_total))))
      (is (= id (-> {:url (:siteurl credentials)
                     :consumer_key (:ckw credentials)
                     :consumer_secret (:csw credentials)
                     :order id
                     :exception false
                     :insecure true}
                    (delete-order-by-id)
                    (:id)))))))

(deftest order-v3-api-batch-update-success-test
  (testing "Testing order v3 API for batch update."
    (let [resp (orders-batch-operations {:url (:siteurl credentials)
                                         :consumer_key (:ckw credentials)
                                         :consumer_secret (:csw credentials)
                                         :exception false
                                         :insecure true
                                         :body (json/write-str {:create [(:order_to_create credentials)]})})]
      (is (< 0 (:id (first (:create resp)))))
      (is (= "10.00" (:shipping_total (first (:create resp)))))
      (is (= "john.doe@example.com" (:email (:billing (first (:create resp))))))
      (is (= "CA" (:state (:shipping (first (:create resp))))))
      (is (= "completed" (-> {:url (:siteurl credentials)
                              :consumer_key (:ckw credentials)
                              :consumer_secret (:csw credentials)
                              :exception false
                              :insecure true
                              :body (json/write-str {:update [{:id (:id (first (:create resp)))
                                                               :status "completed"}]})}
                             (orders-batch-operations)
                             (:update)
                             (first)
                             (:status))))
      (is (= (:id (first (:create resp))) (-> {:url (:siteurl credentials)
                                               :consumer_key (:ckw credentials)
                                               :consumer_secret (:csw credentials)
                                               :exception false
                                               :insecure true
                                               :body (json/write-str {:delete [(:id (first (:create resp)))]})}
                                              (orders-batch-operations)
                                              (:delete)
                                              (first)
                                              (:id)))))))
