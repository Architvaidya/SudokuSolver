import sys


def cross(A, B):
    "Cross product of elements in A and elements in B."
    return [a+b for a in A for b in B]

digits   = '0123456789ABCDEF'
rows     = 'ABCDEFGHIJKLMNOP'
cols     = digits
squares  = cross(rows, cols)
unitlist = ([cross(rows, c) for c in cols] +
            [cross(r, cols) for r in rows] +
            [cross(rs, cs) for rs in ('ABCD','EFGH','IJKL','MNOP') for cs in ('0123','4567','89AB','CDEF')])
units = dict((s, [u for u in unitlist if s in u])
             for s in squares)
peers = dict((s, set(sum(units[s],[]))-set([s]))
             for s in squares)




#This function parses the grid. It converts the grid to a dictionary of possible values. Returns false if a contradiction is detected
def parse_grid(grid):
    values = dict((s, digits) for s in squares)
    for s,d in grid_values(grid).items():
        if d in digits and not assign(values, s, d):
            return False 
    return values

#Converts grid into a dictionary with '0' or '.' assigned to empty entries.
def grid_values(grid):
    chars = [c for c in grid if c in digits or c in '0.']
    assert len(chars) == 256
    return dict(zip(squares, chars))


#The function returns values. Returns false if there is a contradiction.
def assign(values, s, d):
    other_values = values[s].replace(d, '')
    if all(eliminate(values, s, d2) for d2 in other_values):
        return values
    else:
        return False

#The function eleminates d from values.  		
def eliminate(values, s, d):
    if d not in values[s]:
        return values ## d is already present
    values[s] = values[s].replace(d,'')
    
    if len(values[s]) == 0:
        return False 
    elif len(values[s]) == 1:
        d2 = values[s]
        if not all(eliminate(values, s2, d2) for s2 in peers[s]):
            return False
    
    for u in units[s]:
        dplaces = [s for s in u if d in values[s]]
        if len(dplaces) == 0:
            return False 
        elif len(dplaces) == 1:
             
            if not assign(values, dplaces[0], d):
                return False
    return values



def display(values):
    width = 1+max(len(values[s]) for s in squares)
    line = '+'.join(['-'*(width*4)]*4)
    for r in rows:
        print (''.join(values[r+c].center(width)+('|' if c in '37B' else '')
                      for c in cols))
        if r in 'DHL': print (line)
    print



def solve_python(grid): return search(parse_grid(grid))


#Use DFS, try all possible values
def search(values):
    if values is False:
        return False 
    if all(len(values[s]) == 1 for s in squares):
        return values 
    
    n,s = min((len(values[s]), s) for s in squares if len(values[s]) > 1)
    return some(search(assign(values.copy(), s, d))
                for d in values[s])



#Returns element in the sequence that is true
def some(seq):
    
    for e in seq:
        if e: return e
    return False


def shuffled(seq):
    
    seq = list(seq)
    random.shuffle(seq)
    return seq



import time, random, collections

#Checks if the puzzle is solved
def solved(values):
    
    def unitsolved(unit): return set(values[s] for s in unit) == set(digits)
    return values is not False and all(unitsolved(unit) for unit in unitlist)



if __name__ == '__main__':
    
    
    start = time.clock()
    gridToSolve=sys.argv[1];
    values2 = solve_python(gridToSolve)
    t=time.clock() - start
    
    
    if solved(values2):
    	od = collections.OrderedDict(sorted(values2.items()))
    	return_string=''.join(od.values())
    	print return_string
