//import com.atlassian.jira.jql.builder.*
//import com.atlassian.query.*
//import com.atlassian.jira.issue.search.SearchResults
//import com.atlassian.crowd.embedded.api.User
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.component.ComponentAccessor
//import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.web.bean.PagerFilter
import com.atlassian.jira.issue.*
  
import com.atlassian.jira.workflow.WorkflowTransitionUtil;
import com.atlassian.jira.workflow.WorkflowTransitionUtilImpl;
import com.atlassian.jira.util.JiraUtils;
import com.atlassian.sal.api.component.ComponentLocator
import com.atlassian.jira.bc.issue.DefaultIssueService
import com.atlassian.jira.issue.MutableIssue;
import com.atlassian.jira.issue.link.IssueLinkManager
import com.atlassian.jira.bc.issue.link.DefaultIssueLinkService

//import com.atlassian.jira.jql.parser.JqlQueryParser

//def jqlQueryParser = ComponentAccessor.getComponent(JqlQueryParser.class)
//def searchService = ComponentAccessor.getComponentOfType(SearchService.class)
def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()
def issueService = ComponentLocator.getComponent(DefaultIssueService.class)
def issueLinkService = ComponentLocator.getComponent(DefaultIssueLinkService.class)

Issue issue = issueService.getIssue(currentUser,"DAT-367").getIssue()
IssueLinkManager issueLinkManager = ComponentAccessor.getIssueLinkManager()

WorkflowTransitionUtil workflowTransitionUtil = (WorkflowTransitionUtil) JiraUtils.loadComponent(WorkflowTransitionUtilImpl.class);


issueLinkManager.getOutwardLinks(issue.id).each {issueLink ->
    MutableIssue linkedIssue = issueLink.destinationObject as MutableIssue
    if (issueLink.issueLinkType.name == "Relates") { 
        if (linkedIssue.getStatus().name == "Approved") {
           try{ 
            linkedIssue.setResolutionId("Done");
            workflowTransitionUtil.setIssue(linkedIssue);
			workflowTransitionUtil.setAction(101);
			workflowTransitionUtil.validate();
			workflowTransitionUtil.progress();
            log.info("True issue Link: " + linkedIssue);
	           }  catch(IOException e){log.error(e)};
    	}else{
        	log.error("Issue Link Status False : " + linkedIssue.getStatus().name)
        }
    }else{
      log.error("False issue Link: " + linkedIssue)
    }
}