(ns irclojure.core
  (:require
    [irclj.core :as ircb]
    [irclojure.db :as db]
    [clojure.set :refer :all]))

(def channel "#test")
(def server "127.0.0.1")
(def port 6667)
(def bot-nick "bot")

(defn map-irc-msg [msg]
  (select-keys
    (rename-keys msg {:target :channel, :text :message})
    [:nick :channel :message]))

(defn callback
  [irc args]
  (do
    (println args)
    (db/write-log (map-irc-msg args))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (def irc (ircb/connect server port bot-nick :callbacks {:privmsg callback}))


  (ircb/join irc channel))
