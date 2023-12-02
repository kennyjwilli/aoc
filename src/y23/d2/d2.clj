(ns y23.d2.d2
  (:require
    [clojure.string :as str]
    [sc.api]))

(defn parse-line
  [line]
  (let [[_ game cubes-line] (re-find #"Game (\d+): (.*)" line)
        cube-sets (str/split cubes-line #"; ")]
    (for [cube-set cube-sets
          :let [cubes (str/split cube-set #", ")]
          cube cubes
          :let [[_ n color] (re-find #"(\d+) (\w+)" cube)]]
      {:n     (parse-long n)
       :color color
       :game  (parse-long game)})))

(comment
  (def games (mapcat parse-line (str/split-lines (slurp "src/y23/d2/input.txt"))))
  (def game->color->max
    (reduce (fn [acc {:keys [n color game]}]
              (update-in acc [game color] (fnil max 0) n)) {} games))

  (->> game->color->max
    (filter (fn [[game {:strs [red green blue]}]]
              (and (<= red 12) (<= green 13) (<= blue 14))))
    (map first)
    (reduce +))
  => 2348

  (reduce + (map (comp #(apply * %) vals) (vals game->color->max)))
  => 76008
  )
