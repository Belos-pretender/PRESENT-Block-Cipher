package Main;

import java.util.Arrays;

public class presentEncrypt {
	
	public static void Encrypt(byte[] plaintext,byte[] key) {
		int i = 0;
		for(i = 0;i<31;i++) {
			plaintext = AddRoudKey(plaintext,key);
			plaintext = Sub_bytes(plaintext);
			plaintext = pExchange(plaintext);
			key = UpdataKeys(key,i + 1);
			//System.out.println("text:"+Arrays.toString(plaintext));
			//System.out.println("key:"+Arrays.toString(key));
		}
		plaintext = AddRoudKey(plaintext,key);
		System.out.println(Arrays.toString(plaintext));
	}
	
	//ÂÖÃØÔ¿¼Ó
	public static byte[] AddRoudKey(byte[] Ap,byte[] Ak) {
		for(int i = 0;i<16;i++) {
			Ap[i] ^= Ak[i];
		}
		return Ap;
	}
	
	
	//Sbox
	public static byte[] Sub_bytes(byte[] Sp) {
		byte[] sbox = new byte[] {
			0x0c,0x05,0x06,0x0b,
			0x09,0x00,0x0a,0x0d,
			0x03,0x0e,0x0f,0x08,
			0x04,0x07,0x01,0x02
		};
		for(int i = 0;i<16;i++) {
			Sp[i] = sbox[Sp[i]];
		}
		return Sp;
	}
	
	//pLayer
	public static byte[]  pExchange(byte[] pEp) {
		byte[] rPx = new byte[] {
		     0,4,8,12,16,20,24,28,32,36,40,44,48,52,56,60,
		     1,5,9,13,17,21,25,29,33,37,41,45,49,53,57,61,
		     2,6,10,14,18,22,26,30,34,38,42,46,50,54,58,62,
		     3,7,11,15,19,23,27,31,35,39,43,47,51,55,59,63	
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
	
	public static byte[] UpdataKeys(byte[] Uk,int r) {
	    byte[] sbox = new byte[]{
	            0x0c, 0x05, 0x06, 0x0b,
	            0x09, 0x00, 0x0a, 0x0d,
	            0x03, 0x0e, 0x0f, 0x08,
	            0x04, 0x07, 0x01, 0x02
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
	        
	         //Byte[] tmpUk = null;
	         //System.arraycopy(Uk, 0, tmpUk, 0, Uk.length);
	         
	         return Uk;
	         
	}
	public static Byte[] UpdataKeysToByte(byte[] Uk,int r) {
	    byte[] sbox = new byte[]{
	            0x0c, 0x05, 0x06, 0x0b,
	            0x09, 0x00, 0x0a, 0x0d,
	            0x03, 0x0e, 0x0f, 0x08,
	            0x04, 0x07, 0x01, 0x02
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
	        
	         Byte[] tmpUk = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
	         for(int j = 0;j<19;j++) {
	        	 tmpUk[j] = Uk[j];
	         }
	         
	         System.out.println("1");
	         
	         return tmpUk;
	         
	}
	
}
