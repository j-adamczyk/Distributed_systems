import socket
from time import sleep

serverIP = "127.0.0.1"
serverPort = 9008
msg = "[P] Ping Python Udp!"

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(bytes(msg, 'UTF-8'), (serverIP, serverPort))

buff = []
while True:
    client.sendto(bytes(msg, 'UTF-8'), (serverIP, serverPort))
    buff, address = client.recvfrom(1024)
    print("python udp server received msg: " + str(buff, 'UTF-8'))
    sleep(1)
