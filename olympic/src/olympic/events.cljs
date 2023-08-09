(ns olympic.events
  (:require
   [re-frame.core :as rf]
   [olympic.db :as db]
   [olympic.service :as svc]))

(rf/reg-event-db
 ::initialize-db
 (fn [_ _]
   db/default-db))

(rf/reg-event-db
 ::set-busy?
 (fn [db [_ busy?]]
   (assoc-in db [:busy?] busy?)))

(rf/reg-event-db
 ::set-sort-by
 (fn [db [_ val]]
   (assoc-in db [:sort-by] val)))

(rf/reg-event-fx
  ::set-medals
  (fn [{:keys [db]} [_ data]]
    (let [error? (and (contains? data :success)
                   (not (:success data)))]
      {:db (if error?
             (assoc db :error-text
               (str "Error in fetching medal data "
                 "with error code - "  (:error-code data) )
               :fetched? false)
             (assoc db :medal-data data :fetched? true))
       :dispatch [::set-busy? false]})))

(rf/reg-fx
  :service/fetch-medals
  (fn [_]
    (svc/fetch-medals {:evt-success [::set-medals]
                       :evt-failure [::set-medals]})))

(rf/reg-event-fx
  ::fetch-data
  (fn [_ _]
    {:service/fetch-medals true
     :dispatch [::set-busy? true]}))
