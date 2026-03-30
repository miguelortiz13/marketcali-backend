variable "project_name" {
  description = "The name of the project"
  type        = string
  default     = "marketcali"
}

variable "location" {
  description = "The Azure Region to deploy resources into"
  type        = string
  default     = "eastus"
}

variable "environment" {
  description = "The environment (e.g., dev, prod)"
  type        = string
  default     = "prod"
}

variable "mysql_admin_username" {
  description = "The administrator username of the MySQL Database"
  type        = string
  default     = "marketcali_admin"
}

variable "mysql_admin_password" {
  description = "The administrator password of the MySQL Database"
  type        = string
  sensitive   = true
}


