import socket

serverIP = "127.0.0.1"
serverPort = 9008
msg_bytes = (300).to_bytes(4, byteorder='little')

print('PYTHON UDP CLIENT')
client = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
client.sendto(msg_bytes, (serverIP, serverPort))

buff = []
while True:
    buff, address = client.recvfrom(1024)
    num = int.from_bytes(buff, byteorder='big')
    print("python udp client received msg: " + str(num))
