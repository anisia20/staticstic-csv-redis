package staticstic.scouter.sample.common.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvRead {
	
	public void readCsv(File csv) {
	
		List<List<String>> ret = new ArrayList<List<String>>();

		
		//ClassPathResource tmpcsv = new ClassPathResource("SERVICE.csv");
		
		try {

			/* 임시 파일 읽기, 추후 주석 */
		    
			//System.out.println(tmpcsv.getURI());
			//File csv = new File(tmpcsv.getURI());
			BufferedReader br = new BufferedReader(new FileReader(csv));
			
			System.out.println("읽기 성공");
			String line = "";
			
			int row = 3, i;
			boolean isFirstLine = false;
			while((line= br.readLine()) != null) {
				
				
				
				if(!isFirstLine ) {
					isFirstLine = true;
					continue;
				}
	             List<String> tmpList = new ArrayList<String>();
	                String array[] = line.split(",");
	                //배열에서 리스트 반환
	                tmpList = Arrays.asList(array);
	                System.out.println(tmpList);
	                ret.add(tmpList);


	
				
				row++;
			}
			//System.out.println(tmpcsv.getURI());
			
			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
