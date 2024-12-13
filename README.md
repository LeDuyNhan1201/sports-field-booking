## User Roles and Features

### **Customer Users:**
- **Browse Website:** View homepage and website introduction.
- **Sports Fields:** Browse fields, add to cart, and book via the booking form.
- **User Info:** Edit personal information.
- **Invoices:** View all confirmed bookings.
- **Feedback:** Rate fields (stars) and comment after completing a booked session.

### **Field Owners:**
- Same features as **Customer Users**, plus:
  - **Statistics Management:** Manage reports and analytics.
  - **Invoice Management:** Handle owner-related invoices.
  - **Field Management:** Manage owned sports fields.

### **Administrators (Admins):**
- **User Management:** Add, edit, or remove users.
- **Discount Management:** Manage discounts.
- **Field Management:** Manage all sports fields.
- **Category Management:** Handle field categories.

---

## Additional Features:
- **Password Recovery:** Send reset password email via Gmail.
- **Social Login:** Login and register using Gmail or Facebook.
- **Core Technologies:** Built with Spring Security, Spring JPA, Thymeleaf, Spring Web, and RESTful APIs.
- **Payments:** Integrated with VNPay for secure transactions.
- **Logging:** Application logging with Elasticsearch, Logstash, and Kibana (ELK Stack).
- **Authentication:** Access and refresh token handling for secure sessions.
- **Async Tasks:** Kafka for tasks like email sending, metadata management, CRUD operations.
- **Caching:** Redis cache for JWT revoked token handling.
- **System Monitoring:** Grafana, Prometheus, and Tempo for observability.

-------------------------------------------------------------------------------------------------------------------------------------------
# Setup Docker:
1. Install Docker Desktop (Search for Docker Desktop on Google)
2. Go to root directory of the project, open terminal and run the following commands:
    - docker-compose -f docker-compose.yml up -d (Waiting for the images of containers to be pulled and started for the first time)
3. Open Docker Desktop, go to "Containers" at the left sidebar, click "sports-field-booking" to see the containers running:
4. Go to http://localhost:8000/ to access phpMyAdmin (database name: sports_field_booking_db)

# Project Structure:
1. "entities" folder contains the classes for the entities in the database (using ORM)
2. "repositories" folder contains the classes for CRUD into database (using JPA)
3. "services" folder contains the classes for the business logic of the application:
    - "impls" folder contains the implementation of the interfaces in the "services" folder
4. "controllers" folder contains the classes for the RESTful API endpoints
5. "configurations" folder contains the classes for the configurations of the application:
    - "security" folder contains the classes for the security configurations of the application
    - "OpenAPIConfiguration.java" contains the configurations for OpenAPI (Swagger)
    - "LocalResolverConfiguration.java" contains the configurations for the Multi-language support
6. "components" folder contains the classes for the components of the application:
    - "Translator.java" contains the static function to get messages in multi-language from the resource/i18n/messages.properties file
7. "exceptions" folder contains the classes for the Custom Exceptions and Exception Handlers:
    - "GlobalExceptionHandler.java" contains the class to handle exceptions globally from controllers
8. "helpers" folder contains the classes static functions.
9. "mappers" folder contains the classes for mapping entities to DTOs and vice versa.
10. "dtos" folder contains the classes for the Data Transfer Objects (DTOs) of the application.
11. "resources" folder contains the classes for the resources of the application:
    - "resource/i18n/messages.properties" file contains the messages in English
    - "resource/i18n/messages_vi.properties" file contains the messages in Vietnamese
    - "application.yml" contains the configurations for the application
12. After running the application, go to http://localhost:8888/sports-field-booking/swagger-ui/index.html to see the RESTful API documentation (Swagger)

# Get json file from Swagger:
1. Go to http://localhost:8888/sports-field-booking/swagger-ui/index.html
2. Look in the top left corner, below the title "Sports Field Booking server REST API", there is an url "/sports-field-booking/api-docs/rest-api-service-dev"
3. Click on the url or directly go to http://localhost:8888/sports-field-booking/api-docs/rest-api-service-dev, ctrl A to select all, ctrl C to copy
4. Create a new .json file with any name, paste the copied content into the file
5. Open Postman, click on "Import" button, choose "Import File", choose the created .json file in step 4

