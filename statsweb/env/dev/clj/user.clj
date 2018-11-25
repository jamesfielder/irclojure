(ns user
  (:require [statsweb.config :refer [env]]
            [clojure.spec.alpha :as s]
            [expound.alpha :as expound]
            [mount.core :as mount]
            [statsweb.figwheel :refer [start-fw stop-fw cljs]]
            [statsweb.core :refer [start-app]]
            [statsweb.db.core]
            [conman.core :as conman]
            [luminus-migrations.core :as migrations]))

(alter-var-root #'s/*explain-out* (constantly expound/printer))

(defn start []
  (mount/start-without #'statsweb.core/repl-server))

(defn stop []
  (mount/stop-except #'statsweb.core/repl-server))

(defn restart []
  (stop)
  (start))

(defn restart-db []
  (mount/stop #'statsweb.db.core/*db*)
  (mount/start #'statsweb.db.core/*db*)
  (binding [*ns* 'statsweb.db.core]
    (conman/bind-connection statsweb.db.core/*db* "sql/queries.sql")))

(defn reset-db []
  (migrations/migrate ["reset"] (select-keys env [:database-url])))

(defn migrate []
  (migrations/migrate ["migrate"] (select-keys env [:database-url])))

(defn rollback []
  (migrations/migrate ["rollback"] (select-keys env [:database-url])))

(defn create-migration [name]
  (migrations/create name (select-keys env [:database-url])))


