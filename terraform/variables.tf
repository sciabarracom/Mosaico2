variable "region" {
  default = "us-east-1"
}

variable "ami_by_region" {
  type = "map"
  default = {
    us-east-1 = "ami-ae7bfdb8"
  }
}

variable "instance_type" {
  default = "t2.medium"
  description = "EC2 instance type"
}

variable "key_name" {
  default = "mosaico"
  description = "the ssh key to use in the EC2 machines"
}

variable "vpc_fullcidr" {
  default = "10.0.0.0/16"
  description = "the vpc cdir"
}

variable "subnet_cidr" {
  default = "10.0.0.0/24"
  description = "the cidr of the subnet"
}

variable "ip_prefix" {
  default = "10.0.0"
  description = "ip prefix of node instances"
}

variable "volume_size" {
  default = "20"
  description = "volume size"
}
