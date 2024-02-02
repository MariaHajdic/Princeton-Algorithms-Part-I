package stats

import (
	"errors"
	"math"
)

type PercolationStats struct {
	TresholdValues []float64
	N, T           int
}

func NewPercolationStats(n, t int) (PercolationStats, error) {
	if n <= 0 || t <= 0 {
		return PercolationStats{}, errors.New("Invalid input")
	}
	ps := PercolationStats{
		TresholdValues: make([]float64, t),
		T:              t,
		N:              n,
	}
	return ps, nil
}

func (ps PercolationStats) Mean() float64 {
	if len(ps.TresholdValues) == 0 {
		return 0
	}
	var sum float64
	for _, t := range ps.TresholdValues {
		sum += t
	}
	return sum / float64(len(ps.TresholdValues))
}

func (ps PercolationStats) Stddev() float64 {
	var diffSum float64
	mean := ps.Mean()
	for i := 0; i < len(ps.TresholdValues); i++ {
		diffSum += math.Pow(ps.TresholdValues[i]-mean, 2)
	}
	return math.Sqrt(diffSum / float64(len(ps.TresholdValues)-1))
}

func (ps PercolationStats) ConfidenceLo() float64 {
	return ps.Mean() - 1.96*ps.Stddev()/math.Sqrt(float64(len(ps.TresholdValues)))
}

func (ps PercolationStats) ConfidenceHi() float64 {
	return ps.Mean() + 1.96*ps.Stddev()/math.Sqrt(float64(len(ps.TresholdValues)))
}
