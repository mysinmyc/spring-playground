EDGE GATEWAY
============


This server exposes an ingress gateway for consul registered services


## Static Endpoints


| path                   | route                                                    |
|------------------------|----------------------------------------------------------|
| /static/httpd/**       | route to a service named httpd-httpd                     |
| /static/restservice/** | route to a service named restservice                     |


## Dynamic endpoints


By setting `gateway.discovery.locator.enabled: true` it will be possible to reach any service registered in consul (path  **/{serviceId}/{blablabla}**) 