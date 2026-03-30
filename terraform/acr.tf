# Generate random integer for ACR name uniqueness
resource "random_integer" "acr_suffix" {
  min = 1000
  max = 9999
}

# Azure Container Registry
resource "azurerm_container_registry" "acr" {
  name                = "acr${var.project_name}${var.environment}${random_integer.acr_suffix.result}"
  resource_group_name = azurerm_resource_group.rg.name
  location            = azurerm_resource_group.rg.location
  sku                 = "Basic"
  admin_enabled       = true # Enabled for Azure Container Apps

  tags = {
    Environment = var.environment
    Project     = var.project_name
  }
}