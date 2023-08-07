(ns olympic.subs
  (:require
   [re-frame.core :as rf]))

(rf/reg-sub
 ::name
 (fn [db]
   (:name db)))

(rf/reg-sub
  ::sort-by
 (fn [db]
   (:sort-by db)))

(rf/reg-sub
  ::medal-data
  (fn [db]
    (:medal-data db)))

(rf/reg-sub
  ::fetched?
  (fn [db]
    (:fetched? db)))

(rf/reg-sub
  ::busy?
  (fn [db]
    (:busy? db)))

(rf/reg-sub
  ::error
  (fn [db]
    (:error-text db)))
