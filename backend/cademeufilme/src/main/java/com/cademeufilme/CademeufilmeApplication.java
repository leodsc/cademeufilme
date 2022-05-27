package com.cademeufilme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CademeufilmeApplication {

	public static void main(String[] args) {
		SpringApplication.run(CademeufilmeApplication.class, args);
	}

}

//@RestController
//class Controller {
//	@GetMapping("/")
//	public ResponseEntity<String> getData() throws InterruptedException {
//		Scraper netflix = new NetflixScraper();
//		netflix.start();
//		return ResponseEntity.ok().build();
//	}
//}
