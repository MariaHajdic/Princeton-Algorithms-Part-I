package system

import "errors"

const (
	open                = 4 // 100
	connected_to_top    = 2 // 010
	connected_to_bottom = 1 // 001
	conduit             = open | connected_to_top | connected_to_bottom
)

type cellState struct {
	state    byte
	id, size int
}

type System struct {
	OpenSites, n  int
	DoesPercolate bool
	cells         []cellState
}

func NewCompositeSystem(n int) (System, error) {
	if n < 1 {
		return System{}, errors.New("Value n < 1")
	}
	sz := n * n
	p := System{
		n:     n,
		cells: make([]cellState, sz),
	}
	for i := 0; i < sz; i++ {
		p.cells[i] = cellState{
			id:   i,
			size: 1,
		}
	}
	return p, nil
}

func (s System) position(row, col int) int {
	return (row-1)*int(s.n) + col - 1
}

func (s *System) root(i int) int {
	for i != s.cells[i].id {
		s.cells[i].id = s.cells[s.cells[i].id].id
		i = s.cells[i].id
	}
	return i
}

func (s *System) union(q, r int) {
	i, j := s.root(q), s.root(r)
	if i == j {
		return
	}
	if s.cells[i].size < s.cells[j].size {
		s.cells[i].id = j
		s.cells[j].size += s.cells[i].size
	} else {
		s.cells[j].id = i
		s.cells[i].size += s.cells[j].size
	}
	s.cells[i].state |= s.cells[j].state
	s.cells[j].state |= s.cells[i].state

	s.DoesPercolate = s.cells[i].state == conduit
}

func (s *System) OpenSite(row, col int) error {
	if row < 1 || row > s.n || col < 1 || col > s.n {
		return errors.New("Position out of bounds")
	}

	pos := s.position(row, col)
	if s.cells[pos].state&open != 0 {
		return nil
	}

	s.cells[pos].state |= open // 100
	if row == 1 {
		s.cells[pos].state |= connected_to_top // 010
	}
	if row == s.n {
		s.cells[pos].state |= connected_to_bottom // 001
	}

	s.OpenSites++

	if row > 1 && (s.cells[pos-s.n].state&open) != 0 { // above
		s.union(pos, pos-s.n)
	}
	if col < s.n && (s.cells[pos+1].state&open) != 0 { // right
		s.union(pos, pos+1)
	}
	if col > 1 && (s.cells[pos-1].state&open) != 0 { // left
		s.union(pos, pos-1)
	}
	if row < s.n && (s.cells[pos+s.n].state&open) != 0 { // below
		s.union(pos, pos+s.n)
	}

	s.DoesPercolate = s.cells[pos].state == conduit
	return nil
}
