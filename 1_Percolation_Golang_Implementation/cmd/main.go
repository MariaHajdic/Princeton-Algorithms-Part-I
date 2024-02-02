package main

import (
	"fmt"
	"log"
	"percolation/monteCarlo"
	"percolation/stats"
	"time"
)

func main() {
	var n, t int
	_, err := fmt.Scan(&n, &t)
	if err != nil {
		log.Fatalf("Invalid input: %s", err)
	}
	if n <= 0 || t <= 0 {
		log.Fatalf("Invalid input: %d %d", n, t)
	}

	start := time.Now()
	stats, err := stats.NewPercolationStats(n, t)
	if err != nil {
		log.Fatalf("Error starting experiment: %s", err)
	}

	monteCarlo.PerformSimulation(&stats)
	fmt.Println("Elapsed time: ", time.Since(start))

	fmt.Println("mean:", stats.Mean())
	fmt.Println("stddev:", stats.Stddev())
	fmt.Println("95% confidence interval: [", stats.ConfidenceLo(), ",", stats.ConfidenceHi(), "]")
}
