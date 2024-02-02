package benchmarking

import (
	"fmt"
	"percolation/monteCarlo"
	"percolation/stats"
	"testing"

	"github.com/stretchr/testify/require"
)

func BenchmarkExperiment(b *testing.B) {
	for n, t := range map[int]int {
		100:  200,
		2:   1000,
		1000: 500,
	} {
		b.Run(fmt.Sprintf("%d grid, %d experiments", n, t), func(b *testing.B)) {
			for i := 0; i < b.N; i++ {
				s, err := stats.NewStats(n, t)
				require.NoError(b, err)
				monteCarlo.PerformSimulation(&s)
			}
		}
	}
}
