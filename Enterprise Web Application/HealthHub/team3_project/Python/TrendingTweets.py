import sys
from TwitterAPI import TwitterAPI, TwitterOAuth

search_string = sys.argv[1]
#"covid19"
flag = sys.argv[2]
#"ScreenName"

print("search_string: ", search_string)


consumer_key = 'ZU9akhD6dCyOyLUaHjAsDogas'
consumer_secret = 'JHB3v1emaJv16jWlrClOE2UFXJjD7wKbWe1xR8ALjPFdcTDraW'
access_token_key = '1329519691321192449-wgQKG7OyBUl1HELRVPzYlN3RhpP5Kl'
access_token_secret = 'RCMnVVeNp1jFMN51JLErS7COQ6j16KJepHcb6A3IRdYwG'


# Using OAuth1...
twitter = TwitterAPI(consumer_key,
                 consumer_secret,
                 access_token_key,
                 access_token_secret)

print("Screenname:", search_string)
print("Query:", flag)

if flag == 'ScreenName':
    
    print("Inside screen_name")
    response = [tweet for tweet in twitter.request('statuses/user_timeline',
                                                    {'screen_name': search_string,
                                                     'count': 100})]
    
    tweets = [r for r in response]
    TweetList_screenname = open('C:\\apache-tomcat-7.0.34\\webapps\\team4_2\\TweetsByScreenName.txt', 'w')
    
    for tweet in tweets:
        text = tweet['text']
        try:
            TweetList_screenname.write("%s\n" % str(text))
        except UnicodeEncodeError:
            pass
        
    TweetList_screenname.close()


elif flag == 'QueryString':

    response = twitter.request('search/tweets', {'q': search_string}) 
    tweets = [r for r in response]
    
    TweetsList = open('C:\\apache-tomcat-7.0.34\\webapps\\team4_2\\Tweets.txt', 'w')
    
    for tweet in tweets:
        text = tweet['text']
        try:
            TweetsList.write("%s\n" % str(text))
        except UnicodeEncodeError:
            pass
        
    TweetsList.close()
