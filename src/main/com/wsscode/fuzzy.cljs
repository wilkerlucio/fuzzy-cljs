(ns com.wsscode.fuzzy
  (:require [com.wsscode.fuzzyjs :as fuzzy]
            [cljs.spec.alpha :as s]))

(s/def ::sort? boolean?)
(s/def ::string string?)
(s/def ::search-input string?)
(s/def ::options (s/coll-of (s/keys :req [::string])))

(defn match-one
  "Matches ::search-input with ::string from map. When match is valid the map is
  augmented with the keys ::match? ::match-score and ::match-hl.

  ```clojure
  (fuzzy/match-one {::fuzzy/search-input \"a\"} {::fuzzy/string \"abc\"})
  ; => {::fuzzy/string      \"abc\"
  ;     ::fuzzy/match?      true
  ;     ::fuzzy/match-score 8
  ;     ::fuzzy/match-hl    \"<b>a</b>bc\"}
  ```"
  [{::keys [search-input]} {::keys [string] :as opt}]
  (let [[match? score hl] (fuzzy/fuzzy_match search-input string)]
    (if match?
      (assoc opt ::match? match? ::match-score score ::match-hl hl))))

(defn fuzzy-match
  "Search for all matches of ::search-input in ::options. Options must be a collection
  of maps, the map key ::string will be used to make the lookup. By default the
  results will be sorted by score, use ::sort? false to keep original order."
  [{::keys [options search-input sort?]
    :or    {sort? true}
    :as    input}]
  (if (seq search-input)
    (let [fuzzy   (partial match-one input)
          compare #(compare %2 %)]
      (as-> options <>
        (keep fuzzy <>)
        (cond->> <>
          sort?
          (sort-by ::match-score compare))))
    options))
