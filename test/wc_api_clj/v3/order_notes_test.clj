(ns wc-api-clj.v3.order-notes-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.v3.orders :refer :all]
            [wc-api-clj.v3.order-notes :refer :all]
            [wc-api-clj.core-test :refer [credentials]]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Test for v3/order-notes API ;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftest order-note-v3-api-create-read-delete-success-test
  (testing "Testing order-note v3 API for create, read and delete."
    (let [order (-> {:url (:siteurl credentials)
                     :consumer_key (:ckw credentials)
                     :consumer_secret (:csw credentials)
                     :exception false
                     :insecure true
                     :body (json/write-str (:order_to_create credentials))}
                    (create-order))]
      (is (< 0 (:id order)))
      (let [note (-> {:url (:siteurl credentials)
                      :consumer_key (:ckw credentials)
                      :consumer_secret (:csw credentials)
                      :exception false
                      :order (:id order)
                      :insecure true
                      :body (json/write-str {:note "a order note"})}
                     create-order-note)]
        (is (< 0 (:id note)))
        (is (< 0 (-> {:url (:siteurl credentials)
                      :consumer_key (:ckr credentials)
                      :consumer_secret (:csr credentials)
                      :order (:id order)
                      :exception false
                      :insecure true}
                     get-order-notes
                     first
                     :id)))
        (is (= (:id note) (-> {:url (:siteurl credentials)
                               :consumer_key (:ckr credentials)
                               :consumer_secret (:csr credentials)
                               :order (:id order)
                               :note (:id note)
                               :exception false
                               :insecure true}
                              get-order-note-by-id
                              :id)))
        (is (= (:id note) (-> {:url (:siteurl credentials)
                               :consumer_key (:ckw credentials)
                               :consumer_secret (:csw credentials)
                               :order (:id order)
                               :note (:id note)
                               :exception false
                               :insecure true}
                              delete-order-note-by-id
                              :id))))
      (is (= (:id order) (-> {:url (:siteurl credentials)
                              :consumer_key (:ckw credentials)
                              :consumer_secret (:csw credentials)
                              :order (:id order)
                              :exception false
                              :insecure true}
                             delete-order-by-id
                             :id))))))
