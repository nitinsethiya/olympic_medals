(ns olympic-api.handler
  (:require [cheshire.core :as json]
            [taoensso.timbre :as log]
            [reitit.ring :as ring]
            [ring.middleware.cors :refer [wrap-cors]]
            [muuntaja.core :as m]
            [reitit.ring.middleware.exception :refer [exception-middleware]]
            [reitit.ring.middleware.parameters :refer [parameters-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                                     format-request-middleware
                                                     format-response-middleware]]))
(defn get-medals-handler [_]
  (log/info "Received request to fetch medals")
  (let [medals (json/parse-string (slurp "data.json") true)]
    {:status (if medals 200 404)
     :body medals}))


(def routes
  ["/api"
   ["/medals" {:get get-medals-handler}]])

(defn handler []
  (wrap-cors (ring/ring-handler (ring/router
                                 routes
                                 {:data {:muuntaja   m/instance
                                         :middleware [parameters-middleware
                                                      format-negotiate-middleware
                                                      format-request-middleware
                                                      exception-middleware
                                                      format-response-middleware]}}))

             :access-control-allow-origin [#"http://localhost:8081"]
             :access-control-allow-credentials true
             :access-control-allow-methods [:get :post :put :delete]
             ))
