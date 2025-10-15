## Microservice for Transaction Limit Monitoring

This project is a secure Java Spring Boot microservice integrated with MongoDB that tracks and validates client transactions based on monthly spending limits.

### Key Features

* **JWT Authentication** to secure all endpoints and identify clients.
* **MongoDB integration** with three core collections: `clients`, `limits`, and `transactions`.
* Stores all client transactions and marks those that **exceed predefined monthly limits**.
* Supports **category-based limits** (goods & services).
* Allows clients to set **new spending limits**, with automatic timestamping (only current date allowed).
* Retrieves a list of transactions that **exceeded limits**, including:

  * Limit amount
  * Establishment date
  * Currency
