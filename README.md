# instagram_followers
try to fetch the followers of a given user

This is a Spring boot application, after build and run type into Chrome:

http://localhost:8080/getSubscribers?myLogin=*********&myPassword=********&userToFindLogin=channingtatum

and you will get some of the subscribers

I'm using library org.brunocvcunha.instagram4j which allows to get the access to Instagram using your credentials.

Currently it's impossible to fetch all the users because of Instagram limitation (200 followers only by each query). 

And there is a bug in the instagram4j when fetching too much followers resulting in SocketException: connection reset.

Maximum I was able to fetch 27k of followers for Channing Tatum
