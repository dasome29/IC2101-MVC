import socket
from tkinter import *
import json
from threading import Thread

HOST = '127.0.0.1'  # Standard loopback interface address (localhost)
PORT = 7777  # Port to listen on (non-privileged ports are > 1023)

root = Tk()
root.title('MVC')
root.minsize(750, 750)
root.resizable(width=NO, height=NO)
canvas = Canvas(root, width=750, height=750, bg="blue")

grid = []

size = 15

colors = ["#61A0AF", "#96C9DC", "#F06C9B", "#CBF3F0", "#04E762", "#DC0073", "#008BF8", "#6C464F", "#9FA4C4",
          "#B3CDD1", "#C7F0BD", "#20063B", "#FFD166", "#8F8073", "#A682FF", "#B7FDFE"]

canvas.pack()


def make_grid():
    for i in range(50):
        temp = []
        for j in range(50):
            rectangle = canvas.create_rectangle(i * size, j * size, (i * size) + size, (j * size) + size, fill=colors[0])
            temp.append(rectangle)
        grid.append(temp)


make_grid()


def update(string):
    print(string)
    json_list = json.loads(string)
    for i in json_list:
        canvas.itemconfig(grid[i["j"]][i["i"]], fill=colors[i["value"]])
    print(json_list)
    # json.dumps(json_list)


def trim_string(string: str):
    string = string.replace(" ", "")
    flag = False
    buff = ""
    for i in string:
        if i == "[":
            flag = True
        if flag:
            buff += i
    return buff


def receive():
    with socket.socket(socket.AF_INET, socket.SOCK_STREAM) as s:
        s.bind((HOST, PORT))
        s.listen()
        conn, addr = s.accept()
        with conn:
            print('Connected by', addr)
            while True:
                data = conn.recv(1024)
                data = data.decode("utf-8", errors='ignore')

                if ("ESCAPE" in data) or not data:
                    conn.close()
                    s.close()
                    break
                update(trim_string(data))
                # print(json.loads(data))


game_thread = Thread(target=receive)
game_thread.start()

# update('[{"i":0,"j":1,"value":11},{"i":1,"j":1,"value":11},{"i":2,"j":1,"value":11},{"i":3,"j":1,"value":11}]')

root.mainloop()
