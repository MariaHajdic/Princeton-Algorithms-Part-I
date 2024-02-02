.PHONY: test
test: build
	go test -race ./...

.PHONY: benchmark
benchmark: build
	go test -bench=. ./...

.PHONY: build
build:
	go build -o bin/percolationSimulation cmd/main.go