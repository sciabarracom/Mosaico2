resource "aws_subnet" "mosaico_subnet" {

  vpc_id = "${aws_vpc.mosaico_vpc.id}"
  cidr_block = "${var.subnet_cidr}"
  tags {
        Name = "Mosaico subnet"
        Application = "Mosaico"
  }
  availability_zone = "${data.aws_availability_zones.available.names[0]}"
}

resource "aws_route_table_association" "mosaico_routetable_ass" {
    subnet_id = "${aws_subnet.mosaico_subnet.id}"
    route_table_id = "${aws_route_table.public.id}"
}
