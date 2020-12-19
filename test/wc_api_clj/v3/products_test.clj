(ns wc-api-clj.v3.products-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.v3.products :refer :all]
            [wc-api-clj.core-test :refer [credentials]]
            [wc-api-clj.core :refer :all]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Test for v3/products API ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest product-v3-api-create-read-update-delete-success-test
  (testing "Testing product v3 API for create, read, update and delete."
    (let [{:keys [id regular_price]}
          (-> {:url (:siteurl credentials)
               :consumer_key (:ckw credentials)
               :consumer_secret (:csw credentials)
               :exception false
               :insecure true
               :body (json/write-str (:product_to_create credentials))}
              (create-product))]
      (is (< 0 id))
      (is (= "21" regular_price))
      (is (< 0 (-> {:url (:siteurl credentials)
                    :consumer_key (:ckr credentials)
                    :consumer_secret (:csr credentials)
                    :exception true
                    :insecure true}
                   (get-products)
                   (first)
                   (:id))))
      (is (= id (-> {:url (:siteurl credentials)
                     :consumer_key (:ckr credentials)
                     :consumer_secret (:csr credentials)
                     :product id
                     :exception true
                     :insecure true}
                    (get-product-by-id)
                    (:id))))
      (is (= "13" (-> {:url (:siteurl credentials)
                       :consumer_key (:ckw credentials)
                       :consumer_secret (:csw credentials)
                       :product id
                       :body (json/write-str {:regular_price "13"})
                       :exception false
                       :insecure true}
                      (update-product-by-id)
                      (:regular_price))))
      (is (= id (-> {:url (:siteurl credentials)
                     :consumer_key (:ckw credentials)
                     :consumer_secret (:csw credentials)
                     :product id
                     :exception false
                     :insecure true}
                    (delete-product-by-id)
                    (:id)))))))

(deftest product-v3-api-batch-update-success-test
  (testing "Testing product v3 API for batch update."
    (let [resp (products-batch-operations {:url (:siteurl credentials)
                                           :consumer_key (:ckw credentials)
                                           :consumer_secret (:csw credentials)
                                           :exception false
                                           :insecure true
                                           :body (json/write-str {:create [{:codename "Test Batch Update"
                                                                            :type "simple"
                                                                            :regular_price "21.99"
                                                                            :description "Pellentesque habitant morbi tristique senectus et netus et."
                                                                            :short_description "Pellentesque habitant morbi tristique senectus."}]})})]
      (is (< 0 (:id (first (:create resp)))))
      (is (= "21.99" (:regular_price (first (:create resp)))))
      (is (= "simple" (:type (first (:create resp)))))
      (is (= "50.00" (-> {:url (:siteurl credentials)
                          :consumer_key (:ckw credentials)
                          :consumer_secret (:csw credentials)
                          :exception false
                          :insecure true
                          :body (json/write-str {:update [{:id (:id (first (:create resp)))
                                                           :regular_price "50.00"}]})}
                         (products-batch-operations)
                         (:update)
                         (first)
                         (:regular_price))))
      (is (= (:id (first (:create resp))) (-> {:url (:siteurl credentials)
                                               :consumer_key (:ckw credentials)
                                               :consumer_secret (:csw credentials)
                                               :exception false
                                               :insecure true
                                               :body (json/write-str {:delete [(:id (first (:create resp)))]})}
                                              (products-batch-operations)
                                              (:delete)
                                              (first)
                                              (:id)))))))
