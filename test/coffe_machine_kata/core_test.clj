(ns coffe-machine-kata.core-test
  (:require [clojure.test :refer :all]
            [coffe-machine-kata.core :as coffee-machine]
            [coffe-machine-kata.coffee-maker :as coffee-maker]
            [greenpowermonitor.test-doubles :as td]
            [coffe-machine-kata.beverages-catalog :as beverages-catalog]))

(defn reset-fixture [f]
  (coffee-machine/reset-machine!)
  (f))

; Here we register reset-fixture to be called for each test in the namespace
(use-fixtures :each reset-fixture)


(deftest coffee
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]
    :stubbing [beverages-catalog/price :constantly 0]

    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/make!)

    (is (= [["C::"]] (td/calls-to coffee-maker/send-to-maker!)))))

(deftest tea
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]
    :stubbing [beverages-catalog/price :constantly 0]

    (coffee-machine/choose-drink! :tea)
    (coffee-machine/make!)

    (is (= [["T::"]] (td/calls-to coffee-maker/send-to-maker!)))))

(deftest hot-choki
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]
    :stubbing [beverages-catalog/price :constantly 0]

    (coffee-machine/choose-drink! :hot-choki)
    (coffee-machine/make!)

    (is (= [["H::"]] (td/calls-to coffee-maker/send-to-maker!)))))

(deftest hot-choki-with-sugar
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]
    :stubbing [beverages-catalog/price :constantly 0]

    (coffee-machine/choose-drink! :hot-choki)
    (coffee-machine/add-sugar!)
    (coffee-machine/make!)

    (is (= [["H:1:0"]] (td/calls-to coffee-maker/send-to-maker!)))))

(deftest hot-choki-with-two-sugars
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]
    :stubbing [beverages-catalog/price :constantly 0]

    (coffee-machine/choose-drink! :hot-choki)
    (coffee-machine/add-sugar!)
    (coffee-machine/add-sugar!)
    (coffee-machine/make!)

    (is (= [["H:2:0"]] (td/calls-to coffee-maker/send-to-maker!)))))

(deftest not-enough-money-for-a-coffee
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]

    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/pay! 0.5M)
    (coffee-machine/make!)

    (is (= [["M:missing 0.1"]]
           (td/calls-to coffee-maker/send-to-maker!)))))

(deftest after-making-a-drink-the-machine-is-reset
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]

    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/pay! 0.6M)
    (coffee-machine/make!)
    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/make!)

    (is (= [["C::"]
            ["M:missing 0.6"]]
           (td/calls-to coffee-maker/send-to-maker!)))))

(deftest paying-a-coffee-inserting-money-several-times
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]

    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/pay! 0.5M)
    (coffee-machine/make!)
    (coffee-machine/pay! 0.1M)
    (coffee-machine/make!)
    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/make!)

    (is (= [["M:missing 0.1"]
            ["C::"]
            ["M:missing 0.6"]]
           (td/calls-to coffee-maker/send-to-maker!)))))

(deftest enough-money-for-a-coffee
  (td/with-doubles
    :spying [coffee-maker/send-to-maker!]

    (coffee-machine/choose-drink! :coffee)
    (coffee-machine/pay! 0.6M)
    (coffee-machine/make!)

    (is (= [["C::"]] (td/calls-to coffee-maker/send-to-maker!)))))