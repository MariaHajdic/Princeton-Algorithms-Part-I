package monteCarlo

import (
	"log"
	"math/rand"
	"percolation/stats"
	"percolation/system"
)

func PerformSimulation(stats *stats.PercolationStats) {
	for i := 0; i < stats.T; i++ {
		percInstance, err := system.NewCompositeSystem(stats.N)
		if err != nil {
			log.Fatalf("Could not initialize the experiment: %s", err)
		}

		for !percInstance.DoesPercolate {
			row := int(rand.Int31n(int32(stats.N)) + 1)
			col := int(rand.Int31n(int32(stats.N)) + 1)
			percInstance.OpenSite(row, col)
		}
		stats.TresholdValues[i] = float64(percInstance.OpenSites) / float64(stats.N*stats.N)
	}
}
