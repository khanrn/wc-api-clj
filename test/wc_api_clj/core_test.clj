(ns wc-api-clj.core-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.core :refer :all]
            [clojure.data.json :as json]))

;;;;;;;;;;;;;;;;;;;;;;;;;
;; Testing Credentials ;;
;;;;;;;;;;;;;;;;;;;;;;;;;

(def credentials {:siteurl "https://wcapicljtest.local"
                  :uri "/wp-json/wc/v3"
                  :product_to_create {:name "Test Product"
                                      :type "simple"
                                      :regular_price "21"}
                  :coupon_to_create {:code "16off"
                                     :discount_type "percent"
                                     :amount "10"
                                     :individual_use true
                                     :exclude_sale_items true
                                     :minimum_amount "100.00"}
                  :order_to_create {:payment_method "bacs"
                                    :payment_method_title "Direct Bank Transfer"
                                    :set_paid true
                                    :billing {:email "john.doe@example.com"
                                              :first_name "John"
                                              :address_2 ""
                                              :phone "(555) 555-5555"
                                              :address_1 "969 Market"
                                              :city "San Francisco"
                                              :postcode "94103"
                                              :state "CA"
                                              :last_name "Doe"
                                              :country "US"}
                                    :shipping {:first_name "John"
                                               :last_name "Doe"
                                               :address_1 "969 Market"
                                               :address_2 ""
                                               :city "San Francisco"
                                               :state "CA"
                                               :postcode "94103"
                                               :country "US"}
                                    :line_items [{:product_id 31
                                                  :quantity 2}
                                                 {:product_id 12
                                                  :variation_id 35
                                                  :quantity 1}]
                                    :shipping_lines [{:method_id "flat_rate"
                                                      :method_title "Flat Rate"
                                                      :total "10.00"}]}
                  :refund_to_create {:amount "5"}
                  :ckr "ck_86e637eef49ca8f2c864ad7b84dc17b0a7b78750"
                  :csr "cs_0890a046f5b9a30825970a65d8ab8e9c7fa0deb6"
                  :ckw "ck_469b67ce2e12bd1a1b290abf4b1c108e61b4f71c"
                  :csw "cs_a745666f060dd8b03d71bd2f09aabdfdea980442"
                  :ckrw "ck_c0847c624575f61d5a91050f60e185a0594be3e1"
                  :csrw "cs_6728bda01bd36173d6af0d171839347c88d3a43d"
                  :post_to_create {:date "2015-05-05T10:00:00"
                                   :slug "test-post-slug"
                                   :password "12$45"
                                   :content "Test Post Content"
                                   :excerpt "Test Post Excerpt"
                                   :title "Test Post Title"
                                   :status "publish"}
                  :username "admin"
                  :password "admin"})

;;;;;;;;;;;;;;;;;;;;;;
;; Testing core.clj ;;
;;;;;;;;;;;;;;;;;;;;;;

(deftest core-api-functions-success-test
  (testing "Testing core API functions."
    (let [resp (post-req {:siteurl (:siteurl credentials)
                          :uri "/wp-json/wc/v3/products"
                          :username (:ckw credentials)
                          :password (:csw credentials)
                          :body (json/write-str (:product_to_create credentials))
                          :exception true
                          :insecure true})]
      (is (< 0 (:id resp)))
      (is (= (:regular_price (:product_to_create credentials)) (:regular_price resp)))
      (is (= (:id resp) (-> {:siteurl (str (:siteurl credentials))
                             :uri (str "/wp-json/wc/v3/products/")
                             :query (:id resp)
                             :username (:ckr credentials)
                             :password (:csr credentials)
                             :exception true
                             :insecure true}
                            (get-req)
                            (:id))))
      (is (= "13" (-> {:siteurl (:siteurl credentials)
                       :uri (str "/wp-json/wc/v3/products/" (:id resp))
                       :username (:ckw credentials)
                       :password (:csw credentials)
                       :body (json/write-str {:regular_price "13"})
                       :exception true
                       :insecure true}
                      (post-req)
                      (:regular_price))))
      (is (= (:id resp) (-> {:siteurl (str (:siteurl credentials))
                             :uri (str "/wp-json/wc/v3/products/" (:id resp) "/?force=true")
                             :username (:ckw credentials)
                             :password (:csw credentials)
                             :exception true
                             :insecure true}
                            (delete-req)
                            (:id)))))))
