package org.buding;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * @program: SocketDemo
 * @author: miaochen
 * @create: 2019-06-28 15:13
 * @description:
 **/
public class server {
    public static void main(String[] args) {
        int port =getPort(args);
        ServerSocket server=null;
        ExecutorService service= Executors.newFixedThreadPool(50);

        try {
            server=new ServerSocket(port);
            System.out.println("server start");
            while (true){
                Socket socket=server.accept();
                service.execute(new Handler(socket));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(server!=null){
                try {
                    server.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        // new ThreadPoolExecutor(50,200,0, TimeUnit.SECONDS,new LinkedBlockingDeque<>())
       //ThreadFactory namedFactory=new ThreadFactoryBudiler

    }

    static class Handler implements Runnable{

        Socket socket=null;

        public Handler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader reader=null;
            PrintWriter writer=null;
            try {
                reader=new BufferedReader(new InputStreamReader(socket.getInputStream(),"UTF-8"));
                writer=new PrintWriter(new OutputStreamWriter(socket.getOutputStream(),"UTF-8"));

                String readMessage=null;
                while (true){
                    System.out.println("server reading....");
                    if((readMessage=reader.readLine())==null){
                        break;
                    }
                    System.out.println(readMessage);
                    writer.println("server recive: "+readMessage);
                    writer.flush();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                if(socket!=null){
                    try {
                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    socket=null;
                    if(reader!=null){
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    reader=null;

                    if(writer!=null){
                        writer.close();
                    }
                    writer=null;
                }
            }
        }
    }

    private static int getPort(String[] args){
        if(args.length>0){
            try {
                return  Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                return 9999;
            }
        }else {
            return 9999;
        }
    }
}
