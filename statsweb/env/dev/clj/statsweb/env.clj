(ns statsweb.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [statsweb.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[statsweb started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[statsweb has shut down successfully]=-"))
   :middleware wrap-dev})
