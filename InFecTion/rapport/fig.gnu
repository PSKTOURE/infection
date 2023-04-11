set terminal png
set output 'fig1.png'

set title 'Comparaison logarithmique Minmax Alpha-Beta'                    
set xlabel 'Profondeur de reflexion'                    
set ylabel 'Nombre de noeuds visités'

plot 'datanoeuds2.csv' using 1:2 with linespoints lw 4 title 'Minmax', '' using 1:3 with linespoints lw 4 title 'Alpha-Beta'
