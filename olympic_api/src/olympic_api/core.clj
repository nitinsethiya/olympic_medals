(ns olympic-api.core
  (:require
   [org.httpkit.server :as server]
   [olympic-api.handler :refer [handler]]))

(defn -main
  "Starts the main server"
  [& args]
  (server/run-server (handler) {:port 8080
                                :thread 128
                                :handler  handler
                                :max-ws   (* 4 1024 1024)
                                :max-line (* 2 8192)
                                :max-body (* 50 1024 1024)}))
