package com.main.model;

import com.main.entity.Member;
import com.main.respository.LibraryDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Collections;

public class MemberListModel {
    private final ObservableList<Member> list;

    public MemberListModel() {
        this.list = FXCollections.observableArrayList();
        Collections.addAll(this.list, LibraryDAO.getMemberList());
    }

    public ObservableList<Member> getList() {
        return list;
    }



}
