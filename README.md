# Shows a Pooled HTTP Connection configuration

Has a client and a server app. The client has a /hello endpoint that, when hit, calls the server's /hello endpoint. The server takes a few seconds
to respond because it is "working" hard. Run this in k8s and test out scaling up and down the client and server pods. 

It is easy to produce a situation where many clients are all hitting the same pod. Scaling up the service pod 
has no affect on the poor pod getting slammed. All I have to do is scale the server down to 1 pod 
(or something less controlled like a worker node scaling event could do the same). Then all of the client 
pools point at this 1 node. Scaling the server up to 3 pods has no impact on the existing pools.

If this is simply a naive configuration on my part, then I hope to learn from it. However, if I made that configuration
mistake, others will, too.
