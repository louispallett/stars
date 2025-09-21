/* -------------------------------------------------------
* Spring Application with MySQL
* --------------------------------------------------------
* Running a Spring application with MySQL can be done exactly the same way as running it with PostgreSQL. Because
* both use relational databases, the basic setup is the same. The only difference is in the application.properties
* document:
*
*   spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
*
* So, here we just define the correct driver. Everything else can be easily imported over!
*/
package com.example.backend;

import com.example.backend.model.Star;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
