#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <string>
#include <unistd.h>

int sockfd; // socket file descriptor 
int portno; // port number
struct sockaddr_in serv_addr;
struct hostent *server;

int main(int argc, const char* argv[]){ 
    fprintf(stdout, "In the file\n");
    fflush(stdout);
    server = gethostbyname("192.168.1.49"); // ip of server, Need to specify
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    portno = (int) atoi(argv[1]); // port number
 
    sockfd = socket(AF_INET, SOCK_STREAM, 0); // generate file descriptor 
    if (sockfd < 0) 
        perror("ERROR opening socket");
 

 
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, (char *)&serv_addr.sin_addr.s_addr, server->h_length);
    serv_addr.sin_port = htons(portno);
 
    if (connect(sockfd,(struct sockaddr *)&serv_addr,sizeof(serv_addr)) < 0)
    perror("ERROR connecting");

    char rbuff[256];
    int rbytes; 
 
    //rbytes = read(sockfd, rbuff, sizeof(rbuff)); // read from socket and store the msg into buffer
    rbytes = recv(sockfd, rbuff, sizeof(rbuff), 0); // similar to read(), but return -1 if socket closed
    rbuff[rbytes] = '\0'; // set null terminal
    printf("Message: %s\n", rbuff);

    int wbytes;
    char * wbuff; 
    std::string str = "Jetson Message"; 
    wbuff = (char *)str.c_str();
    wbytes = write(sockfd, wbuff, strlen(wbuff));
    if(wbytes < 0) perror("Cannot write to socket");
    fprintf(stdout, "end of app\n");
    fflush(stdout);

}