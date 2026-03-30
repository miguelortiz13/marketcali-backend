output "resource_group_name" {
  description = "The name of the Resource Group"
  value       = azurerm_resource_group.rg.name
}

output "acr_login_server" {
  description = "The ACR login server. Use this to tag your docker images."
  value       = azurerm_container_registry.acr.login_server
}

output "mysql_server_fqdn" {
  description = "The fully qualified domain name of the MySQL Flexible Server"
  value       = azurerm_mysql_flexible_server.mysql.fqdn
}

output "monolith_backend_url" {
  description = "The public URL of the backend monolith"
  value       = "https://${azurerm_container_app.monolith_app.ingress[0].fqdn}"
}

output "frontend_url" {
  description = "The public URL of the React frontend application"
  value       = "https://${azurerm_container_app.frontend_app.ingress[0].fqdn}"
}
