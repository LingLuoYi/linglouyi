package com.linglouyi.demo.linked;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/2/24
 * @Created liyi
 */
public class Node{

    @Setter
    @Getter
    private Object data;

    @Setter @Getter
    private Node next;

//    private Node Dnext;//如果是双向链表

    public Node(Object data){
        this.data = data;
    }

    public Node(Object data,Node node){
        this.data = data;
        this.next = node;
    }

    public void display() {
        System.out.print( "["+data+"]  ");
    }
}
