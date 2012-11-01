# clj-highcharts

A clojure wrapper for highcharts, using the one2team highcharts server exporter.

## Usage

```clojure
[clj-highcharts "0.1"]

;; In your ns statement:
(ns myns
  (:use [clj-highcharts.core]))
```

### Creating an SVG
```clojure
(def my-chart
  {:chart {:width 400
           :height 400}
   :title "made with clj-highcharts"
   :series [{:data [[0 10] [1 30] [2 40] [3 25]]}]})

(def svg (get-svg my-chart))
(spit "my-graph.svg" svg)
```
