package Main;


import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	static byte[] staticPlainText = {0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf};//明文
	static byte[] staticKey = {0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf,0xf};//密钥
	static byte[] cipherText = new byte[16];
	static byte[] staticCopyedKey = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}; //修改密钥时不需要同步修改
	static byte[] staticEncryptText = {3, 3, 3, 3, 13, 12, 13, 3, 2, 1, 3, 2, 1, 0, 13, 2};//密文
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.arraycopy(staticKey, 0, staticCopyedKey, 0,staticKey.length);
		presentEncrypt.Encrypt(staticPlainText, staticKey);
		presentDecrypt.Decrypt(staticEncryptText, staticCopyedKey);
		//byte[] key = {6,13,10,11,3,1,7,4,4,15,4,1,13,7,0,0,8,7,5,9};
		//byte[] key = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		//Byte[] bkey = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		//ArrayList<Byte[]> roundKeyList = new ArrayList<Byte[]>();
		//Byte[] bkey = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};
		//roundKeyList.add(bkey);
		//for(int i = 0;i<31;i++){
		//System.out.println("key:"+Arrays.toString(key));
		//key = presentEncrypt.UpdataKeys(key, i+1);
		//bkey = new Byte[20];
			//for(int j = 0;j<20;j++) {
				//bkey[j] = key[j];
			//}
		//roundKeyList.add(bkey);
		//}
		
		//System.out.println("1");
	}
}
