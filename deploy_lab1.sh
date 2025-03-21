echo "rm -r ~/lab1/" | ssh helios
echo "mkdir ~/lab1" | ssh helios
scp -r ./lab1/* helios:~/lab1