resource "aws_instance" "MasterInstance" {
  ami = "${lookup(var.ami_by_region, var.region)}"
  instance_type = "${var.instance_type}"
  key_name = "${var.key_name}"
  associate_public_ip_address = "true"
  subnet_id = "${aws_subnet.mosaico_subnet.id}"
  private_ip = "${var.ip_prefix}.5"
  vpc_security_group_ids = ["${aws_security_group.FrontEnd.id}"]
  tags {
    Application = "Mosaico"
    Name = "master"
  }
  ebs_block_device {
    device_name = "/dev/sda1"
    delete_on_termination = "true"
    volume_size = "${var.volume_size}"
  }
}
resource "aws_instance" "NodeInstance" {
  ami = "${lookup(var.ami_by_region, var.region)}"
  instance_type = "${var.instance_type}"
  key_name = "${var.key_name}"
  associate_public_ip_address = "true"
  subnet_id = "${aws_subnet.mosaico_subnet.id}"
  private_ip = "${var.ip_prefix}.${count.index + 6}"
  vpc_security_group_ids = ["${aws_security_group.FrontEnd.id}"]
  tags {
        Application = "Mosaico"
        Name = "node_${count.index}"
  }
  ebs_block_device {
    device_name = "/dev/sda1"
    delete_on_termination = "true"
    volume_size = "${var.volume_size}"
  }
  count = 2
}
