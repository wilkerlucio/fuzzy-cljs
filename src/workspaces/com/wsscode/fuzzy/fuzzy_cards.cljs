(ns com.wsscode.fuzzy.fuzzy-cards
  (:require [nubank.workspaces.core :as ws]
            [com.wsscode.fuzzy :as fuzzy]
            [cljs.test :refer-macros [is are run-tests async testing]]))

(ws/deftest test-fuzzy-match-one
  (is (= (fuzzy/match-one {::fuzzy/search-input "a"}
           {::fuzzy/string "abc"})
         {:com.wsscode.fuzzy/string      "abc"
          :com.wsscode.fuzzy/match?      true
          :com.wsscode.fuzzy/match-score 8
          :com.wsscode.fuzzy/match-hl    "<b>a</b>bc"})))

(ws/deftest test-fuzzy-match
  (is (= (fuzzy/fuzzy-match {::fuzzy/options [{::fuzzy/string "abc"}
                                              {::fuzzy/string "cda"}
                                              {::fuzzy/string "bcea"}]
                             ::fuzzy/search-input "a"})
         [{:com.wsscode.fuzzy/string      "abc"
           :com.wsscode.fuzzy/match?      true
           :com.wsscode.fuzzy/match-score 8
           :com.wsscode.fuzzy/match-hl    "<b>a</b>bc"}
          {:com.wsscode.fuzzy/string      "cda"
           :com.wsscode.fuzzy/match?      true
           :com.wsscode.fuzzy/match-score -8
           :com.wsscode.fuzzy/match-hl    "cd<b>a</b>"}
          {:com.wsscode.fuzzy/string      "bcea"
           :com.wsscode.fuzzy/match?      true
           :com.wsscode.fuzzy/match-score -12
           :com.wsscode.fuzzy/match-hl    "bce<b>a</b>"}]))

  (is (= (fuzzy/fuzzy-match {::fuzzy/options [{::fuzzy/string "abc"}
                                              {::fuzzy/string "bcea"}
                                              {::fuzzy/string "cda"}]
                             ::fuzzy/search-input "a"
                             ::fuzzy/sort? false})
         [{:com.wsscode.fuzzy/string      "abc"
           :com.wsscode.fuzzy/match?      true
           :com.wsscode.fuzzy/match-score 8
           :com.wsscode.fuzzy/match-hl    "<b>a</b>bc"}
          {:com.wsscode.fuzzy/string      "bcea"
           :com.wsscode.fuzzy/match?      true
           :com.wsscode.fuzzy/match-score -12
           :com.wsscode.fuzzy/match-hl    "bce<b>a</b>"}
          {:com.wsscode.fuzzy/string      "cda"
           :com.wsscode.fuzzy/match?      true
           :com.wsscode.fuzzy/match-score -8
           :com.wsscode.fuzzy/match-hl    "cd<b>a</b>"}])))
