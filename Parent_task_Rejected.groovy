import com.atlassian.jira.ComponentManager;
import com.atlassian.jira.issue.comments.CommentManager;
import com.opensymphony.workflow.WorkflowContext;
import org.apache.log4j.Category;
import com.atlassian.jira.config.SubTaskManager;
import com.atlassian.jira.workflow.WorkflowTransitionUtil;
import com.atlassian.jira.workflow.WorkflowTransitionUtilImpl;
import com.atlassian.jira.util.JiraUtils;
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.link.DefaultIssueLinkManager

import com.atlassian.jira.issue.fields.DefaultFieldManager
import com.atlassian.sal.api.component.ComponentLocator
import com.atlassian.jira.issue.IssueImpl
import com.atlassian.jira.bc.issue.DefaultIssueService
import com.atlassian.jira.user.util.DefaultUserManager
import com.atlassian.jira.component.ComponentAccessor

def fieldManager = ComponentLocator.getComponent(DefaultFieldManager.class)
def issueService = ComponentLocator.getComponent(DefaultIssueService.class)
def issueLinkManager = ComponentLocator.getComponent(DefaultIssueLinkManager.class)
def userManager = ComponentAccessor.getUserManager()


def values = issue.summary.split('#!')
if (values[1] != "") {
def TIssue = values[1].split('!#')

if (TIssue[0] != "") {
def PIssue = issueService.getIssue(issue.assignee,"" + TIssue[0]).getIssue()

WorkflowTransitionUtil workflowTransitionUtil = (WorkflowTransitionUtil) JiraUtils.loadComponent(WorkflowTransitionUtilImpl.class);
workflowTransitionUtil.setIssue(PIssue);
workflowTransitionUtil.setAction(61);
workflowTransitionUtil.validate();
workflowTransitionUtil.progress();
}
}
