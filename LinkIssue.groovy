import com.atlassian.jira.jql.builder.*
import com.atlassian.query.*
import com.atlassian.jira.issue.search.SearchResults
import com.atlassian.crowd.embedded.api.User
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.issue.*
  
import com.atlassian.jira.workflow.WorkflowTransitionUtil;
import com.atlassian.jira.workflow.WorkflowTransitionUtilImpl;
import com.atlassian.jira.util.JiraUtils;
import com.atlassian.sal.api.component.ComponentLocator
import com.atlassian.jira.bc.issue.DefaultIssueService
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.link.IssueLinkManager

import com.atlassian.jira.jql.parser.JqlQueryParser


def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser.class)
def searchService = ComponentAccessor.getComponentOfType(SearchService.class)
def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issueService = ComponentLocator.getComponent(DefaultIssueService.class)

Issue issue = issueService.getIssue(currentUser,"DAT-367").getIssue()
Query query = jqlQueryParser.parseQuery("project = "+issue.getProjectObject().name+" and status in (Approved) and sprint in openSprints()")
IssueLinkManager issueLinkManager = ComponentAccessor.getIssueLinkManager()
log.info(query)

boolean bool = false;

SearchResults results = searchService.search(currentUser, query, PagerFilter.getUnlimitedFilter());
WorkflowTransitionUtil workflowTransitionUtil = (WorkflowTransitionUtil) JiraUtils.loadComponent(WorkflowTransitionUtilImpl.class);

results.getIssues().each() {

   MutableIssue PIssue = issueService.getIssue(currentUser,it.key).getIssue();
   log.info(PIssue);

    issueLinkManager.createIssueLink(issue.getId(), PIssue.getId(), 10003l, 0l, currentUser)
  bool = true

}

log.info(bool);

//passesCondition =  bool;