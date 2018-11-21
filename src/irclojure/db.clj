(ns irclojure.db)

(use 'korma.db)
(require '[clojure.string :as str])

(defdb prod (postgres {:db "ircdb"
                       :user "irc"
                       :password "ircpass"}))

(use 'korma.core)

(declare irclogs channel nick message inserted)

(defentity irclogs
           (has-one channel)
           (has-one nick)
           (has-one message)
           (has-one inserted))

(defn write-log [msg]
  (insert irclogs (values msg)))
