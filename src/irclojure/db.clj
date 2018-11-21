(ns irclojure.db)

;(pg/def-datasource irclogs-db {:host     "127.0.0.1"
;                                :port     5432
;                                :database "irclogs"
;                                :username "irc"
;                                :password "ircpass"
;                                :pooled   false })

(use 'korma.db)
(require '[clojure.string :as str])

(defdb prod (postgres {:db "ircdb"
                       :user "irc"
                       :password "ircpass"}))

(use 'korma.core)

(declare irclogs channel nick message)

(defentity irclogs
           (has-one channel)
           (has-one nick)
           (has-one message))

(defn write-log [msg]
  (insert irclogs (values msg)))
