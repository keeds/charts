(ns charts.core
  (:require
   [clojure.string :refer (join)]
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [goog.events :as events]
   )
  )


(enable-console-print!)


;; ============================================================================
;; Utility functions

(defn index [data]
  (map-indexed #(vec [(* %1 10) (* %2 10)]) data))

(defn path [xs]
  (->>
   (conj xs 0)
   (index)
   (map (fn [[x y]] (str "L " x " "y)))
   (join " ")
   (str "M 0 0 ")
   ))


;; ============================================================================
;; View functions


(defn circ
  [c owner]
  (let [[x y] c]
    (om/component
     (dom/circle
      #js {:id "point1"
            :cx x
            :cy y
            :r 3}))))

(defn points [xs]
  (reify
    om/IRender
    (render
     [state]
     (let [idx (index xs)]
       (apply dom/g nil
              (om/build-all circ idx))))))

(defn c
  [app owner]
  (om/component
   (dom/path
    #js {:id "path1"
         :d (path app)
         :stroke "red"
         :strokeWidth 2
         :fill "none"})))

(defn chart
  [app owner]
  (reify
    om/IRender
    (render
     [this]
     (apply dom/svg nil
            (concat
             (om/build-all c app)
             (om/build-all points app)))
     )))

(om/root
 chart
 [[1 5 2 9 10 2]
  [6 3 1 0  0 3]]
 {:target (. js/document (getElementById "om-svg"))})
