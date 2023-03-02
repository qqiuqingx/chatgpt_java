package com.example.javachatbot.model;

import lombok.Data;

@Data
public class GptEntity {
   private String role;
   private String content;

   public GptEntity() {

   }

   public GptEntity(String content, String role) {
      this.content = content;
      this.role=role;
   }
}
