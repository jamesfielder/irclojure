(ns statsweb.middleware.formats
  (:require [cognitect.transit :as transit]
            [luminus-transit.time :as time]
            [muuntaja.core :as m]
            [clj-time.coerce :as coerce])
  (:import [com.fasterxml.jackson.datatype.jdk8 Jdk8Module]
           [java.time LocalDateTime]))

(def local-data-writer
  (transit/write-handler
    (constantly "m")
    (fn [v] (-> ^java.time.ZonedDateTime v .toInstant .toEpochMilli))
    (fn [v] (-> ^java.time.ZonedDateTime v .toInstant .toEpochMilli .toString))))

;(def wrap-format-options
;  (update
;    m/default-options
;    :formats
;    merge
;    {"application/transit+json"
;     {:decoder [(partial formats/make-transit-decoder :json)]
;      :encoder [#(muuntajaformats/make-transit-encoder
;                   :json
;                   (merge
;                     %
;                     {:handlers {java.time.ZonedDateTime local-data-writer}}))]}}))

(def instance
  (m/create
    (-> m/default-options
        (assoc-in
          [:formats "application/json" :opts :modules]
          [(Jdk8Module.)])
        ;(assoc-in
        ;  [:formats "application/transit+json" :encode-opts]
        ;  {:handlers {java.time.ZonedDateTime local-data-writer}})
        ;(update-in
        ;  [:formats "application/transit+json" :decoder-opts]
        ;  (partial merge time/time-deserialization-handlers))
        ;(update-in
        ;  [:formats "application/transit+json" :encoder-opts]
        ;  (partial merge time/time-serialization-handlers))
        )))
