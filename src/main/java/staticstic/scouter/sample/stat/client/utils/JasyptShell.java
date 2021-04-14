package staticstic.scouter.sample.stat.client.utils;

import java.io.FileNotFoundException;
import java.util.Properties;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptShell {

	public static void main(String[] args) throws FileNotFoundException {
		Properties p = SmchoUtil.loadYamlIntoProperties("application.yml");
		
		StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
		pbeEnc.setAlgorithm("PBEWithMD5AndDES");
		pbeEnc.setPassword(p.getProperty("encKey")); //암호화키
		
		if(args == null || args.length < 2){
			System.out.println("ex) encdes.sh type text");
			System.out.println("  type : ENC - encoding, DES - decoding");
			return;
		}if("ENC".equalsIgnoreCase(args[0])  == true ){
			String enc = pbeEnc.encrypt(args[1]);
			System.out.println("enc = " + enc);
			return;
		}if("DES".equalsIgnoreCase(args[0])  == true ){
			String des = pbeEnc.decrypt(args[1]);
			System.out.println("des = " + des);
			return;
		}else {
			System.out.println("ex) encdes.sh type text");
			System.out.println("  type : ENC - encoding, DES - decoding");
		}
		
//		String enc = pbeEnc.encrypt("jdbc:mysql://localhost:30406/shinhan_finance?useUnicode=true&characterEncoding=UTF-8&autoReconnect=true&RewriteBatchedStatements=true&serverTimezone=UTC"); //암호화 할 내용
//		System.out.println("enc = " + enc); //암호화 한 내용을 출력
//		
//		//테스트용 복호화
//		String des = pbeEnc.decrypt(enc);
//		System.out.println("des = " + des);
//		
//		
//		enc = pbeEnc.encrypt("shinhan_finance"); //암호화 할 내용
//		System.out.println("enc = " + enc); //암호화 한 내용을 출력
//		
//		//테스트용 복호화
//		des = pbeEnc.decrypt(enc);
//		System.out.println("des = " + des);
//		
//		
//		enc = pbeEnc.encrypt("test1234"); //암호화 할 내용
//		System.out.println("enc = " + enc); //암호화 한 내용을 출력
//		
//		//테스트용 복호화
//		des = pbeEnc.decrypt(enc);
//		System.out.println("des = " + des);
		    
	}

}
