job "httpdproxy" {
    datacenters = ["dc1"]
    type="system"
    group "httpdproxy" {

    restart {
        attempts=1
        delay="30s"	
        interval="60s"	
    }

    network {
        port "http" {
            static = 8082
        }
    }

    service {
        name = "httpdproxy"
        port = "http"
    }

    task "httpdproxy" {
        driver = "docker"

        config {
            image = "nomadserver.default/myhttpd"

            ports = ["http"]

            volumes = [
                "local:/usr/local/apache2/conf.d",
            ]
        }

        template {
            data = <<EOF
CustomLog /proc/self/fd/1 common

Listen 8082
LoadModule proxy_module modules/mod_proxy.so
LoadModule proxy_balancer_module modules/mod_proxy_balancer.so
LoadModule proxy_connect_module modules/mod_proxy_connect.so
LoadModule proxy_http_module modules/mod_proxy_http.so
LoadModule slotmem_shm_module modules/mod_slotmem_shm.so
LoadModule lbmethod_byrequests_module modules/mod_lbmethod_byrequests.so

<Proxy "balancer://restservice" >
{{ range service "restservice" }}
  BalancerMember "http://{{ .Address }}:{{ .Port }}"
{{ end }}
</Proxy>

ProxyPass        "/restservice/" "balancer://restservice/"
ProxyPassReverse "/restservice/" "balancer://restservice/"


<Location "/balancer-manager">
    SetHandler balancer-manager
</Location>
EOF

            destination   = "local/myhttpd.conf"
            change_mode   = "signal"
            change_signal = "SIGHUP"
            }
        }
   }
}
