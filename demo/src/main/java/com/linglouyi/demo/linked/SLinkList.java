package com.linglouyi.demo.linked;

import lombok.Getter;
import lombok.Setter;

/**
 * @Date 2020/2/24
 * @Created liyi
 */
public class SLinkList {

    @Getter @Setter
    private Node head;//表头

    @Getter
    private int length = 0;//获取链表的长度（结点的个数）

    public SLinkList() {
        this.head = null;
    }

    //在链表头部添加结点
    public void addHead(Object data) {
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;
        }
        else{
            newNode.setNext(head);
            head = newNode;
        }
        length++;//结点个数加1
    }

    //在链表头部删除结点
    public Node deleteHead() {
        if(head == null){
            return head;
        }
        Node curNode = head;//把头节点赋给curNode
        head = curNode.getNext();//把之前头节点指向的下一个节点置为新头
        length--;//结点个数减1
        return curNode;
    }

    //在链表尾部添加结点
    public void addTail(Object data) {
        Node newNode = new Node(data);
        if (head == null) {
            head = newNode;
        } else {
            Node pre = head;
            int count = 1;
            while (count < length) {
                pre = pre.getNext();
                count++;
            }
            Node curNode = pre.getNext();//最后结点的next指针，即指向null
            newNode.setNext(curNode);//新结点的next指针指向最后结点的next指针
            pre.setNext(newNode);//最后结点的next指针指向新结点
        }
        length++;//结点个数加1
    }

    //在链表尾部删除结点
    public Node deleteTail() {
        if(head == null){
            return head;
        }
        Node preNode = head;
        int count = 1;
        while(count < length-1) {
            preNode = preNode.getNext();
            count++;
        }
        Node curNode = preNode.getNext();//指向最后一个结点
        preNode.setNext(curNode.getNext());//指向最后一个结点next指针值null
        length--;
        return null;
    }


    //在指定位置插入结点
    public void insertList(Object data, int index) {
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;//链表为空，插入
        }
        if(index > length+1 || index < 1) {
            System.out.println("结点插入的位置不存在，可插入的位置为1到"+(length+1));
        }
        if(index == 1) {
            newNode.setNext(null);
            head = newNode;//在链表开头插入
        }
        else{             //在链表中间或尾部插入
            Node preNode = head;
            int count = 1;
            while(count < index-1) {
                preNode = preNode.getNext();
                count++;
            }
            Node curNode = preNode.getNext();
            newNode.setNext(curNode);
            preNode.setNext(newNode);
        }
        length++;
    }

    //在指定位置删除结点
    public Node deleteList(int index) {
        if(index > length || index < 1) {
            System.out.println("结点删除的位置不存在，可删除的位置为1到"+length);
        }
        if(index == 1) {     //删除表头结点
            Node curNode = head;
            head = curNode.getNext();
            length--;
            return curNode;
        }
        else{         //删除链表中间或尾部结点
            Node preNode = head;
            int count = 1;
            while(count < index-1) {
                preNode = preNode.getNext();
                count++;
            }
            Node curNode = preNode.getNext();
            preNode.setNext(curNode.getNext());
            length--;
            return curNode;
        }
    }



    //获取指定位置的数据
    public Object getIndexData(int index) {
        if(head == null){
            System.out.println("空表");
        }
        if(index > length || index < 1) {
            System.out.println("结点位置不存在，可获取的位置为1到"+length);
        }
        Node curNode = head;
        int count = 1;
        while(count != index) {
            curNode = curNode.getNext();
            count++;
        }
        return curNode.getData();
    }

    //修改指定位置的结点数据，与获取指定位置的数据的方法基本一致。
    public void updateIndexData(int index, Object data) {
        if(head == null){
            System.out.println("空表");
        }
        if(index > length || index < 1) {
            System.out.println("结点位置不存在，可更新的位置为1到"+length);
        }
        Node curNode = head;
        int count =1;         //也可以用for循环方式求取
        while(count != index) {
            curNode =curNode.getNext();
            count++;
        }
        curNode.setData(data);//将获取的指定位置的数据修改为指定的数据
    }}
