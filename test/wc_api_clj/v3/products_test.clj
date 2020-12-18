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
    (let [{:keys [id regular_price]} (create-product {:url (:siteurl credentials)
                                                      :consumer_key (:ckw credentials)
                                                      :consumer_secret (:csw credentials)
                                                      :exception false
                                                      :insecure true
                                                      :body (json/write-str (:product_to_create credentials))})]
      (is (< 0 id))
      (is (= "21" regular_price))
      (is (< 0 (count (get-products {:url (:siteurl credentials)
                                     :consumer_key (:ckr credentials)
                                     :consumer_secret (:csr credentials)
                                     :exception false
                                     :insecure true}))))
      (is (= id (:id (get-product-by-id {:url (:siteurl credentials)
                                         :consumer_key (:ckr credentials)
                                         :consumer_secret (:csr credentials)
                                         :product id
                                         :exception true
                                         :insecure true}))))
      (is (= "13" (:regular_price (update-product-by-id {:url (:siteurl credentials)
                                                         :consumer_key (:ckw credentials)
                                                         :consumer_secret (:csw credentials)
                                                         :product id
                                                         :body (json/write-str {:regular_price "13"})
                                                         :exception false
                                                         :insecure true}))))
      (is (= id (:id (delete-product-by-id {:url (:siteurl credentials)
                                            :consumer_key (:ckw credentials)
                                            :consumer_secret (:csw credentials)
                                            :product id
                                            :exception false
                                            :insecure true})))))))

(deftest product-v3-api-batch-update-success-test
  (testing "Testing product v3 API for batch update."
    (let [resp (products-batch-update {:url (:siteurl credentials)
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
      (is (= "50.00" (:regular_price (first (:update (products-batch-update {:url (:siteurl credentials)
                                                                             :consumer_key (:ckw credentials)
                                                                             :consumer_secret (:csw credentials)
                                                                             :exception false
                                                                             :insecure true
                                                                             :body (json/write-str {:update [{:id (:id (first (:create resp)))
                                                                                                              :regular_price "50.00"}]})}))))))
      (is (= (:id (first (:create resp))) (:id (first (:delete (products-batch-update {:url (:siteurl credentials)
                                                                                      :consumer_key (:ckw credentials)
                                                                                      :consumer_secret (:csw credentials)
                                                                                      :exception false
                                                                                      :insecure true
                                                                                      :body (json/write-str {:delete [(:id (first (:create resp)))]})})))))))))
