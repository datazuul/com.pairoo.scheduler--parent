#! /bin/sh

do_start () {
        java -Denv=PROD -DcronExpression="0 0 13 ? * FRI *" -jar /home/datazuul/PmailDaemonRunner-1.0-SNAPSHOT.jar
}

case "$1" in
  start|"")
        do_start
        ;;
  restart|reload|force-reload)
        echo "Error: argument '$1' not supported" >&2
        exit 3
        ;;
  stop)
        # No-op
        ;;
  status)
        do_status
        exit $?
        ;;
  *)
        echo "Usage: pairooCronJobs.sh [start|stop]" >&2
        exit 3
        ;;
esac