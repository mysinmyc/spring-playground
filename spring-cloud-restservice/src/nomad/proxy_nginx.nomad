job "nginx" {

    datacenters = ["dc1"]
    type="system"

    group "nginx" {

        restart {
            attempts=1
            delay="30s"	
            interval="60s"	
        }
        
        network {
        port "http" {
            static = 8081
        }
        }

        service {
        name = "nginx"
        port = "http"
        }

        task "nginx" {
            driver = "docker"

            config {
                image = "nginx"

                ports = ["http"]

                volumes = ["local:/etc/nginx/conf.d" ]
            }

            template {
                data = <<EOF
upstream restservice {
{{ range service "restservice" }}
  server {{ .Address }}:{{ .Port }};
{{ else }}server 127.0.0.1:65535; # force a 502
{{ end }}
}
server {
   listen 8081;

   location / {
      proxy_pass http://restservice;
   }
}
EOF

                destination   = "local/load-balancer.conf"
                change_mode   = "signal"
                change_signal = "SIGHUP"
            }
        }
    }
}
