(ns olympic.core
  (:require
   [reagent.dom :as rdom]
   [re-frame.core :as re-frame]
   [olympic.events :as events]
   [olympic.views :as views]
   [olympic.config :as config]))


(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root
  "mounts olympic medal component to the given id.
  Initializes the default sort by option"
  [container sort-by]
  (re-frame/clear-subscription-cache!)
  (let [root-el (.getElementById js/document container)]
    (re-frame/dispatch-sync [::events/set-sort-by sort-by])
    (rdom/unmount-component-at-node root-el)
    (rdom/render [views/olympic-medal sort-by] root-el)))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root "app" "gold"))
