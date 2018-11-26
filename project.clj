(defproject com.wsscode/fuzzy "1.0.0"
  :description "Fuzzy search algorithm for Clojurescript"
  :url "https://github.com/wilkerlucio/fuzzy-cljs"
  :license {:name "MIT" :url "https://opensource.org/licenses/MIT"}

  :plugins [[lein-tools-deps "0.4.1"]]
  :middleware [lein-tools-deps.plugin/resolve-dependencies-with-deps-edn]
  :lein-tools-deps/config {:config-files [:install :user :project]}

  :jar-exclusions [#"public/.*" #"^workspaces/.*" #"\.DS_Store" #"main" #"test"])
