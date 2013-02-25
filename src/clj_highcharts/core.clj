(ns clj-highcharts.core
  (:require [clj-highcharts.json :as json]
            [clj-highcharts.fn :as i-fn]
            [clj-highcharts.executer :as executer])
  (:import java.io.ByteArrayOutputStream
           java.io.StringReader
           [org.apache.batik.transcoder.image JPEGTranscoder PNGTranscoder TIFFTranscoder]
           [org.apache.batik.transcoder TranscoderInput TranscoderOutput]))

(defn js-fn [^String function]
  (i-fn/js-fn function))

(defn json->svg [^String options]
  (executer/make-svg options))

(defn clj->svg [^clojure.lang.IPersistentMap options]
  (-> (json/encode options)
      (json->svg)))

(defn svg->image [transcoder svg output-stream]
  (.transcode transcoder
              (TranscoderInput. (StringReader. svg))
              (TranscoderOutput. output-stream))
  output-stream)

(defn svg->jpeg [svg output-stream]
  (svg->image (JPEGTranscoder.) svg output-stream))

(defn svg->png [svg output-stream]
  (svg->image (PNGTranscoder.) svg output-stream))

(defn svg->tiff [svg output-stream]
  (svg->image (TIFFTranscoder.) svg output-stream))
