(ns olympic.views
  (:require
   [re-frame.core :as rf]
   [clojure.string :as str]
   [olympic.subs :as subs]
   [olympic.events :as event]
   [reagent-mui.material.form-control :refer [form-control]]
   [reagent-mui.material.input-label :refer [input-label]]
   [reagent-mui.material.button :refer [button]]
   [reagent-mui.material.select :refer [select]]
   [reagent-mui.material.menu-item :refer [menu-item]]
   [reagent-mui.material.box :refer [box]]
   [reagent-mui.material.skeleton :refer [skeleton]]
   [reagent-mui.material.table :refer [table]]
   [reagent-mui.material.table-head :refer [table-head]]
   [reagent-mui.material.table-body :refer [table-body]]
   [reagent-mui.material.table-cell :refer [table-cell]]
   [reagent-mui.material.table-row :refer [table-row]]))

(def sort-options ["gold" "total" "silver" "bronze"])

(defn get-sorted-data [sort-option data]
  (reverse
    (case sort-option
      "total" (sort-by (juxt :total :gold :silver :bronze) data)
      "gold" (sort-by (juxt :gold :silver :bronze :total) data)
      "silver" (sort-by (juxt :silver :gold :bronze :total) data)
      "bronze" (sort-by (juxt :bronze :gold :silver :total) data))))

(defn sort-by-select []
  (let [sort-by @(rf/subscribe [::subs/sort-by])]
    [form-control {:variant "outlined"
                   :style {:width "100px"}}
     [input-label "Sort by"]
     (into [select {:label "Sort by"
                    :id "sort-select"
                    :value sort-by
                    :on-change #(rf/dispatch [::event/set-sort-by (-> % .-target .-value)])}]
       (map (fn [opt]
              [menu-item {:value opt} opt])
         sort-options))]))

(defn busy-box []
  [box {:sx {:width "500px" :padding "50px"}}
        [skeleton]
        [skeleton {:animation "wave"}]
        [skeleton {:animation false}]])

(defn olympic-table []
  (let [data @(rf/subscribe [::subs/medal-data-with-total])
        sort-by @(rf/subscribe [::subs/sort-by])
        tdata (->> data
                (get-sorted-data sort-by)
                (take 10))]
    [table {:size "small" :sx {:width "500px"}}
     [table-head
      [table-row {:key 100}
       (->> ["" "" "" "gold" "silver" "bronze" "Total"]
         (map-indexed (fn [idx col]
                        [table-cell{:key (str "col"idx)
                                    :size "small"
                                    :variant "head"}
                         (if (#{"gold" "silver" "bronze"} col)
                           [:div {:class (str "circle " col)}]
                           col)])))]]
     [table-body
      (->> tdata
        (map-indexed (fn [idx {:keys [code gold silver bronze total]}]
                       [table-row {:key idx}
                        [table-cell (inc idx)]
                        [table-cell
                         [:img {:src (str "images/"
                                       (str/lower-case code)
                                       ".png")
                                :width "50px"
                                :height "30px"}]]
                        [table-cell code]
                        [table-cell gold]
                        [table-cell silver ]
                        [table-cell bronze]
                        [table-cell total]])))]]))

(defn olympic-medal
  "Has option to select the sort by functionality by chossing the
  sort-options.
  Display top 10 countries based on the sort option selected"
  []
  (let [name @(rf/subscribe [::subs/name])
        fetched? @(rf/subscribe [::subs/fetched?])
        busy? @(rf/subscribe [::subs/busy?])
        error-text @(rf/subscribe [::subs/error])]
    [:div {:id "olympic-widget"}
     [:h1 name]
     (when-not fetched?
       [button
        {:variant  "contained"
         :color    "primary"
         :on-click #(rf/dispatch [::event/fetch-data])}
        "Fetch medals"])
     (when (and error-text (not fetched?))
       [:h3 error-text])
     (if busy?
       [busy-box]
       (when fetched?
         [:<>
          [sort-by-select]
          [olympic-table]]))]))
