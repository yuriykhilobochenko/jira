import com.atlassian.jira.jql.builder.*
import com.atlassian.query.*
import com.atlassian.jira.issue.search.SearchResults
import com.atlassian.crowd.embedded.api.User
import com.atlassian.jira.user.ApplicationUser
import com.atlassian.jira.component.ComponentAccessor
import com.atlassian.jira.bc.issue.search.SearchService
import com.atlassian.jira.web.bean.PagerFilter

def searchService = ComponentAccessor.getComponentOfType(SearchService.class)
def currentUser = ComponentAccessor.getJiraAuthenticationContext().getLoggedInUser()

final JqlQueryBuilder builder = JqlQueryBuilder.newBuilder();
	builder.where().assignee().eq(currentUser.name).and().status().eq().string("In Progress");
	Query query = builder.buildQuery();

SearchResults results = searchService.search(currentUser, query, PagerFilter.getUnlimitedFilter());
//this.searchService.
//return results.getIssues();

results.getIssues()
