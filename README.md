REST API with one endpoint which finds the shortest contribution path between two GitHub users.
The path is defined as the number of hops between contributors via their contribution to a repository.

For example :
Given John and Rob both contributed to the 'alogrithmics' repository (hosted in GitHub under John/algorithmics).
Then the shortest distance between them is one and goes through the algorithmics repository.

Given we have three users (user1, user2 and user3) and two repositories (repository1, repository2).
When User1 contributes to repository1, User2 contributed to repository1 and repository2, and User3 contribute to repository2.
Then the shortest path between User1 and User3 will equal 2 and it will go through repository1 and repository2.


We can run the application locally by simply running mvn clean package jetty:run
