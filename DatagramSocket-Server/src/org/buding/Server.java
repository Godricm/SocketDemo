package org.buding;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * @program: SocketDemo
 * @author: miaochen
 * @create: 2019-06-28 16:05
 * @description:
 **/
public class Server {
    public static void main(String[] args) {
        try {
            DatagramSocket server=new DatagramSocket(5060);
            DatagramPacket packet=new DatagramPacket(new byte[1024],1024);
            server.receive(packet);
            System.out.println(packet.getAddress().getHostAddress()+"("+packet.getPort()+"):"+new String(packet.getData()));
            packet.setData("Hello Client".getBytes());
            packet.setPort(5070);
            packet.setAddress(InetAddress.getLocalHost());
            server.send(packet);
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
