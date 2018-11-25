(ns statsweb.routes.services
  (:require [muuntaja.middleware :as muuntaja]
            [reitit.ring.coercion :as rrc]
            [reitit.swagger :as swagger]
            [ring.util.http-response :refer :all]
            [ring.middleware.params :as params]
            [statsweb.db.core :refer [*db*] :as db]
            [clojure.set :refer :all]
            [clojure.algo.generic.functor :as gf]))

; Stuck here, how the hell do you build the map up to have the right types
; also lets be real, the lack of types here are very very annoying. You're
; talking to something with types but messing about at runtime casting them
(defn get-metric [metric-fn params]
  (do
    (let [renamed (rename-keys params {"limit" :limit, "interval" :interval})
          parsed (read-string (renamed :limit))
          merged (merge {:limit parsed} (select-keys renamed :interval))]
      (println renamed)
      (println parsed)
      (println merged)
      (metric-fn merged))))


(defn daily-handler [x]
  (ok
    (get-metric db/get-lines-per-day x)))

(defn nicks-handler [x]
  (ok
    (get-metric db/get-most-active-nicks x)))

(defn hour-handler [x]
  (ok
    (get-metric db/get-activity-by-hour (:query-params x))))

(defn service-routes []
  ["/api"
   {:middleware [params/wrap-params
                 muuntaja/wrap-format
                 swagger/swagger-feature
                 rrc/coerce-exceptions-middleware
                 rrc/coerce-request-middleware
                 rrc/coerce-response-middleware]
    :swagger {:id ::api
              :info {:title "my-api"
                     :description "using [reitit](https://github.com/metosin/reitit)."}
              :produces #{"application/json"
                          "application/edn"
                          ;"application/transit+json"
                          }
              :consumes #{"application/json"
                          "application/edn"
                          ;"application/transit+json"
                          }}}
   ["/swagger.json"
    {:get {:no-doc true
           :handler (swagger/create-swagger-handler)}}]
   ["/ping" {:get (constantly (ok {:message "ping"}))}]
   ["/pong" {:post (constantly (ok {:message "pong"}))}]
   ["/hours" {:get {:handler hour-handler}}]
   ["/nicks" {:get {:handler nicks-handler}}]
   ["/daily" {:get {:handler daily-handler}}]])
