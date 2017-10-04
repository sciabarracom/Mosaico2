resource "aws_vpc" "mosaico_vpc" {

    cidr_block = "${var.vpc_fullcidr}"

    enable_dns_support = true
    enable_dns_hostnames = true

    tags {
      Name = "Mosaico VPC"
      Application = "Mosaico"
    }
}
