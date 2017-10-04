/* EXTERNAL NETWORG , IG, ROUTE TABLE */
resource "aws_internet_gateway" "gw" {
   vpc_id = "${aws_vpc.mosaico_vpc.id}"
    tags {
        Name = "Mosaico stack gw"
        Application = "Mosaico"
    }
}

resource "aws_network_acl" "all" {
   vpc_id = "${aws_vpc.mosaico_vpc.id}"
    egress {
        protocol = "-1"
        rule_no = 2
        action = "allow"
        cidr_block =  "0.0.0.0/0"
        from_port = 0
        to_port = 0
    }
    ingress {
        protocol = "-1"
        rule_no = 1
        action = "allow"
        cidr_block =  "0.0.0.0/0"
        from_port = 0
        to_port = 0
    }
    tags {
        Name = "Mosaico open acl"
    }
}

resource "aws_route_table" "public" {
  vpc_id = "${aws_vpc.mosaico_vpc.id}"
  tags {
      Name = "Mosaico stack route table"
      Application = "Mosaico"
  }
  route {
        cidr_block = "0.0.0.0/0"
        gateway_id = "${aws_internet_gateway.gw.id}"
  }
}

resource "aws_eip" "forNat" {
    vpc = true
}

resource "aws_nat_gateway" "mosaico_gw" {
    allocation_id = "${aws_eip.forNat.id}"
    subnet_id = "${aws_subnet.mosaico_subnet.id}"
    depends_on = ["aws_internet_gateway.gw"]
}
