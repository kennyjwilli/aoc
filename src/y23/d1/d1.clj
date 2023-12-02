(ns y23.d1.d1
  (:require [clojure.string :as str]))

(def words {"one" 1 "two" 2 "three" 3 "four" 4 "five" 5 "six" 6 "seven" 7 "eight" 8 "nine" 9})
(def any-word (re-pattern (str/join "|" (keys words))))

(defn word-pattern
  [word]
  (re-pattern (str (subs word 0 (dec (count word))) (format "(?=%s)" (last word)))))

(defn replace-words
  [line]
  (if-let [w (re-find any-word line)]
    (replace-words (str/replace-first line (word-pattern w) (str (get words w))))
    line))

(defn find-calibration-value
  [line]
  (let [digits (->> line replace-words (filter (comp parse-long str)))]
    (parse-long (str (first digits) (last digits)))))

(comment
  (word-pattern "eight")

  (replace-words "eighthree")

  (str (butlast "asd"))
  (replace-words "eightwothree")
  (find-calibration-value "nvfive8hvdth6fgnfgh")
  (find-calibration-value "eighthree")

  (map find-calibration-value
    ["two1nine"
     "eightwothree"
     "abcone2threexyz"
     "xtwone3four"
     "4nineeightseven2"
     "zoneight234"
     "7pqrstsixteen"])
  ;; 29, 83, 13, 24, 42, 14, and 76.
  (->> "src/y23/d1/input.txt" slurp str/split-lines (map find-calibration-value) (reduce +))
  ;; 54473
  )
