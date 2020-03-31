package Main;

import java.util.ArrayList;
import java.util.Arrays;

public class presentDecrypt {

	public static void Decrypt(byte[] encryptedText,byte[] key) {
		int i = 0;
		byte[] deKey = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};//与传入形参做区分
		ArrayList<Byte[]> roundKeyList = new ArrayList<Byte[]>();//用于存放轮密钥
		Byte[] bkey = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1};//因为泛型只支持Byte[]，用于存放入list
		for(int j = 0;j<20;j++) {
			bkey[j] = key[j]; //赋值传入的初始密钥
		}
		roundKeyList.add(bkey);//加入list
		
		//生成所有的轮秘钥并添加入list
		for(int k = 0;k<31;k++){
			//System.out.println("key:"+Arrays.toString(key));
			key = presentEncrypt.UpdataKeys(key, k+1);
			bkey = new Byte[20];
				for(int j = 0;j<20;j++) {
					bkey[j] = key[j];
				}
			roundKeyList.add(bkey);
			}
		
		int ri = 31;//从list的最末尾开始取轮秘钥（共32轮秘钥
		for(i = 0;i<31;i++) {
				Byte[] tmpByte = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
				System.arraycopy(roundKeyList.get(ri), 0, tmpByte, 0, tmpByte.length);//因为list中存的是Byte型数组，做一个数组复制，方便下面类型转换
				ri = ri-1;
				for(int p = 0;p<20;p++) {
					deKey[p] = tmpByte[p]; //类型转换 Byte转byte
				}
				
			encryptedText = AddRoudKey(encryptedText,deKey);
			encryptedText = pExchange(encryptedText);
			encryptedText = Sub_bytes(encryptedText);
			//System.out.println("text:"+Arrays.toString(encryptedText));
			//System.out.println("key:"+Arrays.toString(key));
		}
		Byte[] finalByte = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
		System.arraycopy(roundKeyList.get(0), 0, finalByte, 0, finalByte.length);
		for(int p = 0;p<20;p++) {
			deKey[p] = finalByte[p];
		}
		encryptedText = AddRoudKey(encryptedText,deKey);//因为循环的关系，最后手动加一轮密钥，如上↑
		System.out.println(Arrays.toString(encryptedText));
	}
	
	//轮秘钥加
	public static byte[] AddRoudKey(byte[] Ap,byte[] Ak) {
		for(int i = 0;i<16;i++) {
			Ap[i] ^= Ak[i];
		}
		return Ap;
	}
	
	
	//逆Sbox
	public static byte[] Sub_bytes(byte[] Sp) {
		byte[] sbox = new byte[] {
			0x05,0x0e,0x0f,0x08,
			0x0c,0x01,0x02,0x0d,
			0x0b,0x04,0x06,0x03,
			0x00,0x07,0x09,0x0a
		};
		for(int i = 0;i<16;i++) {
			Sp[i] = sbox[Sp[i]];
		}
		return Sp;
	}
	
	//逆pLayer
	public static byte[]  pExchange(byte[] pEp) {
		byte[] rPx = new byte[] {
			 0,16,32,48,1,17,33,49,2,18,34,50,3,19,35,51,
			 4,20,36,52,5,21,37,53,6,22,38,54,7,23,39,55,
			 8,24,40,56,9,25,41,57,10,26,42,58,11,27,43,59,
			 12,28,44,60,13,29,45,61,14,30,46,62,15,31,47,63	
		};
		byte row1, col1, row, col, shift, shift1, tmp;
	    byte[] buf = new byte[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	    for(int i = 0;i<64;i++) {
	    	shift1 = 0x08;
	    	row1 = (byte) (i/4);
	    	col1 = (byte) (i%4);
	    	shift1 >>= col1;
	    	shift = 0x08;
	    	tmp = rPx[i];
	    	row = (byte) (tmp/4);
	    	col = (byte) (tmp%4);
	    	shift >>= col;
			if((shift & pEp[row])!=0) {
				buf[row1] |= shift1;
			}
	    }
	    pEp = new byte[buf.length];
	    for (int i = 0; i < pEp.length; i++) {
			pEp[i] = buf[i];
		}
	    
	    return pEp;
	}
	
	//解密中无用↓
	public static byte[] UpdataKeys(byte[] Uk,int r) {
		byte[] sbox = new byte[]{
				0x05,0x0e,0x0f,0x08,
				0x0c,0x01,0x02,0x0d,
				0x0b,0x04,0x06,0x03,
				0x00,0x07,0x09,0x0a
	        };
	        byte[] tmpk1 = new byte[20];
	        int i;
	         		for (i = 0; i < 20; i++)
	                {
	                    tmpk1[i] = Uk[i];
	                }

	         Uk[0] = (byte) (((tmpk1[15] << 1) | (tmpk1[16] >> 3)) & 0x0f);
	         Uk[1] = (byte) (((tmpk1[16] << 1) | (tmpk1[17] >> 3)) & 0x0f);
	         Uk[2] = (byte) (((tmpk1[17] << 1) | (tmpk1[18] >> 3)) & 0x0f);
	         Uk[3] = (byte) (((tmpk1[18] << 1) | (tmpk1[19] >> 3)) & 0x0f);
	         Uk[4] = (byte) (((tmpk1[19] << 1) | (tmpk1[0] >> 3)) & 0x0f);
	         Uk[5] = (byte) (((tmpk1[0] << 1) | (tmpk1[1] >> 3)) & 0x0f);
	         Uk[6] = (byte) (((tmpk1[1] << 1) | (tmpk1[2] >> 3)) & 0x0f);
	         Uk[7] = (byte) (((tmpk1[2] << 1) | (tmpk1[3] >> 3)) & 0x0f);
	         Uk[8] = (byte) (((tmpk1[3] << 1) | (tmpk1[4] >> 3)) & 0x0f);
	         Uk[9] = (byte) (((tmpk1[4] << 1) | (tmpk1[5] >> 3)) & 0x0f);
	         Uk[10] = (byte) (((tmpk1[5] << 1) | (tmpk1[6] >> 3)) & 0x0f);
	         Uk[11] = (byte) (((tmpk1[6] << 1) | (tmpk1[7] >> 3)) & 0x0f);
	         Uk[12] = (byte) (((tmpk1[7] << 1) | (tmpk1[8] >> 3)) & 0x0f);
	         Uk[13] = (byte) (((tmpk1[8] << 1) | (tmpk1[9] >> 3)) & 0x0f);
	         Uk[14] = (byte) (((tmpk1[9] << 1) | (tmpk1[10] >> 3)) & 0x0f);
	         Uk[15] = (byte) (((tmpk1[10] << 1) | (tmpk1[11] >> 3)) & 0x0f);
	         Uk[16] = (byte) (((tmpk1[11] << 1) | (tmpk1[12] >> 3)) & 0x0f);
	         Uk[17] = (byte) (((tmpk1[12] << 1) | (tmpk1[13] >> 3)) & 0x0f);
	         Uk[18] = (byte) (((tmpk1[13] << 1) | (tmpk1[14] >> 3)) & 0x0f);
	         Uk[19] = (byte) (((tmpk1[14] << 1) | (tmpk1[15] >> 3)) & 0x0f);
	         
	         Uk[0] = sbox[Uk[0]];
	         
	         r = r << 3;
	         
	         Uk[15] = (byte) ((Uk[15] ^ (r >> 4)) & 0x0f);
	         Uk[16] = (byte) ((Uk[16] ^ (r % 16)) & 0x0f);
	        
	         return Uk;
	}
}
