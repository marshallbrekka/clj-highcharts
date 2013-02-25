(ns clj-highcharts.executer
  (:require [clojure.java.io :as io]
            [clojure.string :as string])
  (:import org.mozilla.javascript.ScriptableObject
           [org.mozilla.javascript Context ContextAction ContextFactory]
           [org.mozilla.javascript.tools.shell Main]
           [java.io.ByteArrayOutputStream]))

(def ^:dynamic *scope* nil)

(defn get-scope [context]
  (.initStandardObjects context))

(defn init-context [context]
  (doto context
    (.setLanguageVersion Context/VERSION_1_6)
    (.setOptimizationLevel -1)))

(defn attach-js
  ([context file-name]
     (attach-js context (io/resource file-name) file-name))
  ([context resource-url file-name]
     (let [r (io/reader resource-url)]
       (.compileReader context *scope* r file-name 1 nil))))

(defn create-scriptable [context]
  (->> ["env.rhino-1.2.js"
        "jquery-1.4.3.min.js"
       ; "prepare-dom-highcharts-2.1.2.js"
        "highcharts-2.1.2.src.js"
        "exporting-2.1.2.src.js"
        "svg-renderer-highcharts-2.1.2.js"
        "add-BBox.js"
        "formatWrapper.js"]
       (map #(attach-js context %))
       (map #(.exec % context *scope*))
       (dorun)))

(defn run-js [graph-options]
  (ScriptableObject/callMethod
   nil
   *scope*
   "renderSVGFromJson"
   (object-array [nil (str  "(" graph-options ")" )])))

(defrecord Action [graph-options]
  ContextAction
  (run [_ ^Context context]
    (binding [*scope* (get-scope context)]
      (init-context context)
      (create-scriptable context)
      (run-js (:graph-options _)))))

(defn run-action [action]
  (-> (Main/shellContextFactory)
      (.call action)))

(defn make-svg [graph-options]
  (-> graph-options
      (Action.)
      (run-action)
      (str)
      (string/split #"<svg" 2)
      (last)
      (string/split #"</div>" 2)
      (first)
      (->> (str "<svg"))))
