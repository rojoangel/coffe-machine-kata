(ns coffe-machine-kata.core
  (:require [coffe-machine-kata.coffee-maker :as coffee-maker]
            [coffe-machine-kata.beverages-catalog :as beverages-catalog])
  (:gen-class))

(def ^:private ^:const initial-state {:sugar 0 :amount 0M})

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))

(def state (atom initial-state))

(defn reset-machine! []
  (reset! state initial-state))

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

(defn enough-money? [{:keys [drink amount]}]
  (>= amount (beverages-catalog/price)))

(defn create-error-message
  [{:keys [drink amount]}]
  (str "M:missing " (- (beverages-catalog/price) amount)))

(defn make! []
  (if (enough-money? @state)
    (do (coffee-maker/send-to-maker! (create-command @state))
        (reset-machine!))
    (coffee-maker/send-to-maker! (create-error-message @state))))

(defn add-sugar! []
  (swap! state update :sugar inc))

(defn pay! [amount]
  (swap! state update :amount #(+ amount %)))
