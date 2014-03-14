(ns charts.core
  (:require
   [clojure.string :refer (join replace)]
   [om.core :as om :include-macros true]
   [om.dom :as dom :include-macros true]
   [goog.events :as events]
   )
  )


(enable-console-print!)


;; ============================================================================
;; Tests

(def test-data [[4 2 7 10 0 2 3]
                [7 3 1  5 5 8 9]])


;; ============================================================================
;; Constants

(def offset  25)
(def width  500)
(def height 300)
(def xstep (/ width 7))
(def ystep (/ height 10))


;; ============================================================================
;; Utility functions


(defn min-value [xs]
  (->>
   xs
   flatten
   (apply min)))


(defn max-value [xs]
  (->>
   xs
   flatten
   (apply max)))


(defn value-count [xs]
  (->>
   (map count xs)
   (max-value)))


;; (defn xstep [data offset width]
;;   (-> width
;;       (- (* 2 offset))
;;       (/ (value-count data))))


(defn index [data]
  (map-indexed #(vec [(+ (* %1 xstep) offset) (+ (* %2 ystep) offset)]) data))


(defn path [xs]
  (->>
   xs
   (index)
   (map (fn [[x y]] (str x " " y)))
   (join " L ")
   (str "M ")
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
            :r 4}))))

(defn points [xs]
  (reify
    om/IRender
    (render
     [state]
     (let [idx (index xs)]
       (apply dom/g nil
              (om/build-all circ idx))))))

(defn paths
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
             (om/build-all paths app)
             (om/build-all points app)))
     )))

(om/root
 chart
 test-data
 {:target (. js/document (getElementById "om-svg"))})
