package com.tvse.dellin;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Stream;

public class D01FileParser {

	public static void main(String[] args) throws ParseException {

		StringBuilder contentBuilder = new StringBuilder();
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		 
        try (Stream<String> stream = Files.lines( Paths.get("D:\\D01FSDTVSIN84916327624201030032058.txt"), StandardCharsets.UTF_8)) 
        {
            stream.forEach(s -> contentBuilder.append(s).append("\n"));
        }
        catch (IOException e) 
        {
            e.printStackTrace();
        }
 
        String content =  contentBuilder.toString();

        System.out.println(content);
        
        content = content.replaceAll("\n", "");
        
        System.out.println("content" +content);
        
        Long serviceReferenceNumber = Long
				.parseLong(content.substring(content.indexOf("H12") + 7, content.indexOf("H12") + 36).trim());
        
        System.out.println("serviceReferenceNumber => "+serviceReferenceNumber);
        
        String cancelationDate = content.substring(content.indexOf("H12") + 38, content.indexOf("H12") + 46)
				.trim();
		String cancelationTime = content.substring(content.indexOf("H12") + 47, content.indexOf("H12") + 53)
				.trim();
		Date cancelationDateTimeVal = null;
		String cancelationDateTime = cancelationDate.substring(6, 8) + "/" + cancelationDate.substring(4, 6)
				+ "/" + cancelationDate.substring(0, 4) + " " + cancelationTime.substring(0, 2) + ":"
				+ cancelationTime.substring(2, 4) + ":" + cancelationTime.substring(4, 6);
		cancelationDateTimeVal = formatter.parse(cancelationDateTime);
		
		System.out.println("cancelationDateTimeVal ==> "+cancelationDateTimeVal);
		String cancellationReason = "";
		if(content.indexOf("D33") != -1){
			cancellationReason = content.substring(content.indexOf("D29") + 4, content.indexOf("D33")).trim();
		}else{
			cancellationReason = content.substring(content.indexOf("D29") + 4, content.length()).trim();
		}					
		cancellationReason = cancellationReason.replaceAll("  ", "");
		cancellationReason = cancellationReason.replaceAll("D29", " ");
		
		System.out.println("cancellationReason ==> "+cancellationReason);

	}

}
