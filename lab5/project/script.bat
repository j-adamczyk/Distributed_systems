for /L %%n in (1,0,10) do (
  @echo "Hello Zookeeper" >> file.txt
  timeout /t 2 /nobreak > NUL
)