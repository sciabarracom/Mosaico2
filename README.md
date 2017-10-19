# Mosaico

by [Michele Sciabarra](http://michele.sciabarra.com) and   [Sciabarra.com](http://sciabarra.com)

A starter kit for building a Cloud in AWS

- Terraform for creating the cloud
- Ansible for privisioning Docker and Jenkins
- SBT for building a collection of Docker images
- Ammonite Scripts for configuration and management

## Prerequisites

This stuff is unixish. So you need Mac or Linux. It may work on Windows bash, but no promises.

Get an AWS account, get Access and Secret keys and place in ~/.aws/credentials.

Something like:

```
[default]
aws_access_key_id = XXXXXXX
aws_secret_access_key = YYYYYYYY
```
Hint: go in the IAM Management Console, create an user and then in the Security Credentials, create an access key.

Then generate an ssh key in `~/.ssh/id_rsa` if you don't have it.
Hint: use `ssh-keygen`.

## Beginning

Start installing `sbt` then

```
cd scripts
sbt
```

The [readme](ammonite/README.md) provides informations about the avalable scripts
