(ns clj-highcharts.fn)

(defrecord JsFn [script])

(defn js-fn [script]
  (JsFn. script))
