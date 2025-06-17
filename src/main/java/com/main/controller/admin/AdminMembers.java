package com.main.controller.admin;
import com.main.entity.Member;
import com.main.model.MemberListModel;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

//display table of members(users) accounts with their names(last and first), email, username, and library ID
public class AdminMembers {
    @FXML public TableView<Member> viewMembers;

    @FXML private TableColumn<Member,String> libraryIDCol;
    @FXML private TableColumn<Member,String> nameCol;
    @FXML private TableColumn<Member,String> usernameCol;
    @FXML private TableColumn<Member,String> emailCol;
    @FXML private TableColumn<Member,Double> balanceCol;
    private MemberListModel memberListModel;

    public void setViewMembers(MemberListModel memberList){
        libraryIDCol.setCellValueFactory(new PropertyValueFactory<>("libraryID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        balanceCol.setCellValueFactory(new PropertyValueFactory<>("balance"));
        viewMembers.getItems().addAll(memberList.getList());
    }

    public void setMemberListModel(MemberListModel memberListModel){
        this.memberListModel = memberListModel;
    }
    public MemberListModel getMemberList() {
        return this.memberListModel;
    }
}
