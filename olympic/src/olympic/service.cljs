(ns olympic.service
  (:require
   [re-frame.core :as rf]
   [cljs.core.async :refer [<! timeout]]
   [cljs-http.client :as http])
  (:require-macros [cljs.core.async.macros :refer [go]]))

(def api-root "http://localhost:8080")

(defn run
  [{:keys [evt-success evt-failure endpoint]}]
  (let [uri (str api-root endpoint)
        headers {"Accept" "application/json"}]
    (go
      (<! (timeout 1000)) ;;deliberately putting a delay
      (let [{:keys [status body] :as res}
            (<! (http/get
                  uri {:headers headers}))]
        (if (= 200 status)
          (when evt-success
            (rf/dispatch (conj evt-success body)))
          (when evt-failure
            (rf/dispatch (conj evt-failure res))))))))

(defn fetch-medals [{:keys [evt-success evt-failure]}]
  (run {:evt-success evt-success
        :evt-failure evt-failure
        :endpoint "/api/medals"}))
