variable "count" { 
    type = number
    default = 2
} 

variable "message" { 
    type = string
    default = "blablabla"
} 

variable "repo" { 
    type = string
    default = "http://nomadserver:6000"
} 


variable "artifact" { 
    type = string
    default = "restservice-0.0.1-SNAPSHOT.jar"
} 

job "restservice" {
    datacenters = ["dc1"]
    type        = "service"
    group "restservice" {
        count=var.count
        update {
        canary = var.count
        #min_healthy_time = "60s"
        auto_promote= true
    }
    service {
        name = "restservice"
        port = "httpPort"
        check {
           type="http"
           path="actuator/health"
           interval="10s"
           timeout="2s"
        }
    }
    network {
        port "httpPort" {}
        mode = "host"
    }
    task "restservice" {
        driver = "java"
        kill_timeout="120s"
        resources {
           memory=513
        }
        artifact {
           source      = "${var.repo}/${var.artifact}"
           destination = "local/bin"
        }
        config {
           jar_path = "local/bin/${var.artifact}"
           args = ["--echo.message.default=${var.message} from ${NOMAD_ADDR_httpPort}(${node.unique.name})", "--myProcessId=${NOMAD_ALLOC_ID}", "--server.port=${NOMAD_PORT_httpPort}", "--spring.cloud.consul.discovery.register=false"]
           jvm_options = ["-Xmx512m", "-Xms128m"]
        }
    }
  }
}
