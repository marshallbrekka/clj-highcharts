(ns clj-highcharts.json
  (:require [clj-highcharts.fn]
            [cheshire.custom :as json]))

(json/add-encoder clj_highcharts.fn.JsFn
  (fn [s jg]
    (.writeString jg (:script s))))

(defn encode [clj]
  (json/encode* clj))
