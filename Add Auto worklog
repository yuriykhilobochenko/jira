import com.atlassian.jira.issue.worklog.WorklogImpl
import com.atlassian.jira.issue.MutableIssue
import com.atlassian.sal.api.component.ComponentLocator
    
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

def customValue = fieldManager.getCustomField("customfield_10202")

def worklogManager = ComponentAccessor.getWorklogManager()
double timesum = 0



worklogManager.getByIssue(issue).each() { 
    timesum += issue.getTimeSpent();
}

long time = issue.getCustomFieldValue(customValue).toString().toLong()


long today = System.currentTimeMillis().toLong()
long daysBetween = (today - time)
issue.setTimeSpent(daysBetween)

issue.setCustomFieldValue(customValue,System.currentTimeMillis().toString())


   WorklogImpl worklog = new WorklogImpl(worklogManager,
                                         issue,
                                         null,
                                         issue.reporter.name,
                                         issue.summary,
                                         issue.getUpdated(),
                                         null,
                                         null,
                                         daysBetween,
                                         issue.reporter.name,
                                         issue.getUpdated()-10,
                                         issue.getUpdated())   

    worklogManager.create(issue.reporter, worklog, 0L, true)

