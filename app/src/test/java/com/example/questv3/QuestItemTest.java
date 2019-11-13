package com.example.questv3;

import junit.framework.TestCase;

public class QuestItemTest extends TestCase {
    private QuestItem quest1;
    //setup empty quest item
    protected void setUp() {
        quest1 = new QuestItem("", "", "", 0);
        quest1.setTitle("Do the laundry");
        quest1.setContent("Get the laundry done by tomorrow");
        quest1.setDate("2019 November 11");
        quest1.setIcon(1);
    }

    //tests all the getters
    public void testGetTitle(){
        assertEquals("Do the laundry", quest1.getTitle());
    }

    public void testGetContent(){
        assertEquals("Get the laundry done by tomorrow", quest1.getContent());
    }

    public void testGetDate(){
        assertEquals("2019 November 11", quest1.getDate());
    }

    public void testGetIcon(){
        assertEquals(1, quest1.getIcon());
    }

    public void testToString(){
        assertEquals("Quest[title: Do the laundry, date: 2019 November 11, img: 1]", quest1.toString());
    }
}
