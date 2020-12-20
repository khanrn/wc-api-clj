(ns wc-api-clj.rest-test
  (:require [clojure.test :refer :all]
            [wc-api-clj.core-test :refer [credentials]]
            [clojure.data.json :as json]
            [wc-api-clj.rest :refer :all]
            [wc-api-clj.rest :as wp]))

;;;;;;;;;;;;;;;;;;;;;;
;; Testing rest.clj ;;
;;;;;;;;;;;;;;;;;;;;;;

(deftest wp-rest-api-create-read-update-delete-success-test
  (testing "Testing WP REST API for create, read, update and delete."
    (let [resp (-> {:url (str (:siteurl credentials) "/wp-json/wp/v2/posts/")
                    :options {:basic-auth [(:username credentials)
                                           (:password credentials)]
                              :body (json/write-str (:post_to_create credentials))
                              :headers {"X-Api-Version" "2"}
                              :content-type :json
                              :throw-exceptions true
                              :insecure? true
                              :accept :json}}
                   (wp/wp-post)
                   (:body))]
      (is (= (:password (:post_to_create credentials)) (:password resp)))
      (is (= (:content (:post_to_create credentials)) (:raw (:content resp))))
      (is (< 0 (-> {:url (str (:siteurl credentials) "/wp-json/wp/v2/posts/")
                    :options {:basic-auth [(:username credentials)
                                           (:password credentials)]
                               :headers {"X-Api-Version" "2"}
                               :content-type :json
                               :throw-exceptions true
                               :insecure? true
                               :accept :json}}
                    (wp/wp-get)
                    (:body)
                    (count))))
      (is (= (:id resp) (-> {:url (str (:siteurl credentials) "/wp-json/wp/v2/posts/" (:id resp))
                             :options {:basic-auth [(:username credentials)
                                                    (:password credentials)]
                                       :headers {"X-Api-Version" "2"}
                                       :content-type :json
                                       :throw-exceptions true
                                       :insecure? true
                                       :accept :json}}
                            (wp/wp-get)
                            (:body)
                            (:id))))
      (is (= "draft" (-> {:url (str (:siteurl credentials) "/wp-json/wp/v2/posts/" (:id resp))
                          :options {:basic-auth [(:username credentials)
                                                 (:password credentials)]
                                    :body (json/write-str {:status "draft"})
                                    :headers {"X-Api-Version" "2"}
                                    :content-type :json
                                    :throw-exceptions true
                                    :insecure? true
                                    :accept :json}}
                         (wp/wp-post)
                         (:body)
                         (:status))))
      (is (= (:id resp) (-> {:url (str (:siteurl credentials) "/wp-json/wp/v2/posts/" (:id resp) "/?force=true")
                             :options {:basic-auth [(:username credentials)
                                                    (:password credentials)]
                                       :headers {"X-Api-Version" "2"}
                                       :content-type :json
                                       :throw-exceptions true
                                       :insecure? true
                                       :accept :json}}
                            (wp/wp-delete)
                            (:body)
                            (:previous)
                            (:id)))))))
