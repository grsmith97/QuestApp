package com.example.questv3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuestBase {
    private List<QuestItem> mData;
    private List<QuestItem> logData;



    private int identification;

    private QuestBase(){
        mData = new ArrayList<>();
        logData = new ArrayList<>();
        identification = 123456789; //testing id
    }

    private static QuestBase mQuestBase;
    public static QuestBase getInstance(){
        if(mQuestBase == null){
            mQuestBase = new QuestBase();
        }
        return mQuestBase;
    }

    public List<QuestItem> getData(){
        return mData;
    }
    public List<QuestItem> getLogData(){
        return logData;
    }
    public void add(QuestItem questItem){
        mData.add(questItem);
    }
    public void remove(int position){
        mData.remove(position);
    }
    public void swap(int fromPosition,int toPosition){
        Collections.swap(mData,fromPosition,toPosition);
    }
    public QuestItem get(int position){
        return mData.get(position);
    }
    public int getIdentification() {
        return identification;
    }
    public void setIdentification(int identification) {
        this.identification = identification;
    }
}