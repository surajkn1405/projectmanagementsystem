package com.suraj.service;

import com.suraj.modal.Issue;
import com.suraj.modal.User;
import com.suraj.request.IssueRequest;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface IssueService {

    Issue getIssueById(Long issueId) throws Exception;

    List<Issue> getIssueByprojectId(Long projectId) throws Exception;

    Issue createIssue(IssueRequest issueRequest, User user) throws Exception;

//    Optional<Issue> updateIssue(Long issueId, IssueRequest updatedIssue, Long userId)throws Exception;

    void deleteIssue(Long issueId, Long userId) throws Exception;

//    List<Issue> getIssueByAssigneeId(Long assigneeId) throws Exception;

    Issue addUserToIssue(Long issueId, Long userId) throws Exception;

    Issue updateStatus(Long issueId, String status) throws Exception;
}
