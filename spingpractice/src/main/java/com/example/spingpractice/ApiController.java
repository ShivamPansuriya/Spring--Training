//package com.example.spingpractice;
//
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.IOException;
//import java.nio.file.Files;
//import java.util.Arrays;
//import java.util.List;
//
//@RestController
//public class ApiController {
//
//    @RequestMapping("/course")
//    public List<Course> course() {
//        return Arrays.asList( new Course(1,"Shivam","science"),
//                new Course(1,"Raj","science")
//        );
//    }
//
//    @PostMapping("/upload/")
//    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
//        file.transferTo(new File("/home/shivam/Downloads/"+ file.getOriginalFilename()));
//        return "success";
//    }
//
//
//    @GetMapping("/download/{filename}")
//    public ResponseEntity<byte[]> download(@PathVariable String filename) throws IOException {
//        File file = new File("/home/shivam/Downloads/"+ filename);
//        var image = Files.readAllBytes(file.toPath());
//
//        var headers = new HttpHeaders();
//        headers.setContentType(MediaType.IMAGE_PNG);
//        return new ResponseEntity<>(image, headers, HttpStatus.OK);
//    }
//}
