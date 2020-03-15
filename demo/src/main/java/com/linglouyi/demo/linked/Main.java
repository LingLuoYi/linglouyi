package com.linglouyi.demo.linked;


/**
 * @Date 2020/2/24
 * @Created liyi
 */
public class Main {

    //链表翻转
    public static Node reverseListNode(Node node){
        Node preNode = null;
        Node nextNode = null;

        while (node != null){
            nextNode = node.getNext();//节点下移
            node.setNext(preNode);//当前节点前移
            preNode = node;
            node = nextNode;
        }
        System.out.println();
        return preNode;
        //https://linglouyi.tk:5128/weBot/accept
    }

    /**
     * 测试main
     */
    public static void main(String[] args) {
//        SLinkList sLinkList = new SLinkList();
//        sLinkList.insertList(1,1);
//        sLinkList.insertList(2,2);
//        sLinkList.insertList(3,3);
//        sLinkList.insertList(4,4);
//        sLinkList.insertList(5,5);
//        sLinkList.insertList(6,6);
//        sLinkList.insertList(7,7);
//        for (int i = 1; i <= sLinkList.getLength(); i++) {
//            System.out.print(sLinkList.getIndexData(i)+",");
//        }
//        System.out.println();
//        Node node = Main.reverseListNode(sLinkList.getHead());
//        sLinkList.setHead(node);
//        for (int i = 1; i <= sLinkList.getLength(); i++) {
//            System.out.print(sLinkList.getIndexData(i)+",");
//        }
        System.out.println(2<<2);
    }
}


///////////////链表
//SLinkList  java实现的单向链表的操作类
//Node 单向链表的的实体模型
//reverseListNode 遍历法实现的链表转向，还有递归法也可实现
//链表拥有较快的增加和删除能力，同时链表创建不需要开辟一块连续的内存空间

//