

(ns clj-highcharts.core
  (:require [cheshire.core :as json])
  (:import java.io.ByteArrayOutputStream
           java.io.StringReader
           [org.apache.batik.transcoder.image JPEGTranscoder PNGTranscoder TIFFTranscoder]
           [org.apache.batik.transcoder TranscoderInput TranscoderOutput]
           org.marshallbrekka.highcharts.server.export.HighchartsSVGExporter
           org.one2team.highcharts.server.export.util.SVGRendererInternalJson))

(defn get-svg-output-stream [output-stream options]
  (-> (SVGRendererInternalJson.)
      (HighchartsSVGExporter.)
      (.render (json/generate-string options)
               nil
               output-stream)))

(defn get-svg [options]
  (-> (ByteArrayOutputStream.)
      (get-svg-output-stream options)
      (.toString)))

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
