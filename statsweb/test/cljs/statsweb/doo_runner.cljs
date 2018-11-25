(ns statsweb.doo-runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [statsweb.core-test]))

(doo-tests 'statsweb.core-test)

