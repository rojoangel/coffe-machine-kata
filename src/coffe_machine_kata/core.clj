(ns coffe-machine-kata.core
  (:require [coffe-machine-kata.coffee-maker :as coffee-maker])
  (:gen-class))

(def ^:private ^:const initial-state {:sugar 0})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def state (atom initial-state))

(defn choose-drink! [drink]
  (swap! state assoc :drink drink))

(defn- encode-drink [drink]
  (case drink
    :coffee "C"
    :tea "T"
    :hot-choki "H"))

(defn- encode-sugar
  [sugar]
  (if-not (= 0 sugar)
    (str ":" sugar ":0")
    "::"))

(defn- create-command
  [{:keys [drink sugar]}]
  (str (encode-drink drink)
       (encode-sugar sugar)))

(defn make! []
  (coffee-maker/send-to-maker! (create-command @state))
  (reset! state initial-state))

(defn add-sugar! []
  (swap! state update :sugar inc))
