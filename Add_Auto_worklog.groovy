import com.atlassian.jira.issue.worklog.WorklogImpl
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.sal.api.component.ComponentLocator
import java.util.Date
import java.sql.Date
   import com.atlassian.jira.issue.fields.DefaultFieldManager
import com.atlassian.sal.api.component.ComponentLocator
import com.atlassian.jira.issue.IssueImpl
import com.atlassian.jira.bc.issue.DefaultIssueService
import com.atlassian.jira.user.util.DefaultUserManager
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.util.JiraDurationUtils

def fieldManager = ComponentLocator.getComponent(DefaultFieldManager.class)
def issueService = ComponentLocator.getComponent(DefaultIssueService.class)
def userManager = ComponentAccessor.getUserManager()
def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()

def customValue = fieldManager.getCustomField("customfield_11108")

def worklogManager = ComponentAccessor.getWorklogManager()
double timesum = 0



worklogManager.getByIssue(issue).each() { 
    timesum += issue.getTimeSpent();
}

//long time = issue.getCustomFieldValue(customValue).toString().toLong()


//long today = System.currentTimeMillis().toLong()
long daysBetween = ((System.currentTimeMillis().toLong() - issue.getCustomFieldValue(customValue).toString().toLong()) / 1000).toLong();
timesum += daysBetween
//Date dat = Date.valueOf(issue.getCustomFieldValue(customValue).toString())
Date d = new Date(issue.getCustomFieldValue(customValue).toString().toLong())
Date now = new Date(System.currentTimeMillis())
//issue.setTimeSpent(issue.getTimeSpent() + daysBetween)

   WorklogImpl worklog = new WorklogImpl(worklogManager,
                                         issue,
                                         null,
                                         currentUser.name,
                                         issue.summary,
                                         d,
                                         null,
                                         null,
                                         daysBetween,
                                         currentUser.name,
                                         now,
                                         null)   

    worklogManager.create(currentUser, worklog, 1L,false)

issue.setCustomFieldValue(customValue,System.currentTimeMillis().toString())
