import socket
import sys

def main():
    if len(sys.argv) != 3:
        print("put the ip and than the port")
        sys.exit(1)
    server_ip = sys.argv[1]
    server_port = int(sys.argv[2])

    # Create a TCP socket
    s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)

    try:
        # Connect to the server
        s.connect((server_ip, server_port))
        print(f"Connected to server at {server_ip}:{server_port}")

        while True:
            # Get command from user
            command = input("Enter command: ")
            if command.lower() in ["exit"]:
                print("Closing connection...")
                break

            # Send command to server
            s.sendall(command.encode())

            # Receive and print response from server
            response = s.recv(1024).decode()
            print(f"Server response: {response}", end='')

    except ConnectionError as e:
        print(f"Connection error: {e}")
    finally:
        s.close()
        print("Connection closed.")

if __name__ == "__main__":
    main()
