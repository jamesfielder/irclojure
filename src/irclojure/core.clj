(ns irclojure.core
  (:require
    [irclj.core :as ircb]))

(def channel "#test")
(def server "127.0.0.1")
(def port 6667)
(def bot-nick "bot")

(defn callback [irc args] (println irc args))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (def irc (ircb/connect server port bot-nick :callbacks {:privmsg callback}))


  (ircb/join irc channel)
  )
