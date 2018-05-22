(ns coffe-machine-kata.core-test
  (:require [clojure.test :refer :all]
            [coffe-machine-kata.core :as coffee-machine]
            [coffe-machine-kata.coffee-maker :as coffee-maker]
            [greenpowermonitor.test-doubles :as td]))

(deftest making-beberages
  (testing "coffee"
    (td/with-doubles
      :spying [coffee-maker/send-to-maker!]

      (coffee-machine/choose-drink! :coffee)
      (coffee-machine/make!)

      (is (= [["C::"]] (td/calls-to coffee-maker/send-to-maker! ))
      ))
     )
  (testing "tea"
    (td/with-doubles
      :spying [coffee-maker/send-to-maker!]

      (coffee-machine/choose-drink! :tea)
      (coffee-machine/make!)

      (is (= [["T::"]] (td/calls-to coffee-maker/send-to-maker! ))
          )))
  (testing "hot choki"
    (td/with-doubles
      :spying [coffee-maker/send-to-maker!]

      (coffee-machine/choose-drink! :hot-choki)
      (coffee-machine/make!)

      (is (= [["H::"]] (td/calls-to coffee-maker/send-to-maker! ))
          )))

  (testing "hot choki with sugar"
    (td/with-doubles
      :spying [coffee-maker/send-to-maker!]

      (coffee-machine/choose-drink! :hot-choki)
      (coffee-machine/add-sugar!)
      (coffee-machine/make!)

      (is (= [["H:1:0"]] (td/calls-to coffee-maker/send-to-maker! ))
          )))

  (testing "hot choki with two sugars"
    (td/with-doubles
      :spying [coffee-maker/send-to-maker!]

      (coffee-machine/choose-drink! :hot-choki)
      (coffee-machine/add-sugar!)
      (coffee-machine/add-sugar!)
      (coffee-machine/make!)

      (is (= [["H:2:0"]] (td/calls-to coffee-maker/send-to-maker! ))
          )))
  )
  
