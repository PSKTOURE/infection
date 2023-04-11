set terminal png
set output 'hist.png'

set title ''
set boxwidth 0.9 relative
set ylabel 'Nombres de noeuds'
set key inside right top vertical Right noreverse noenhanced autotitle nobox
set style data histograms
set style histogram cluster gap 2
set style fill solid 1.0 border lt -1
#set xtic rotate by -45 scale 0
plot for [COL =2:3]'datanoeuds2.csv' using COL:xticlabels(1) ti col
