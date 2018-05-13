package ergasia;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;


public class Photo {
	int height;
	int width;
	File photofile;
	String name;
	String Geolocation;
	String tags;
	String photopath;
	BufferedImage image;
	String date;
	String time;
	
	
	public Photo() {
		
	}
	
	
	public Photo(String source) {
		SetPhotoPath(source);
		SetPhotoFile();
		SetBufferPhoto();
		SetPhotoName();
		SetPhotoWidth();
		SetPhotoHeight();
	
	}
	
	public void SetPhotoFile() {
		this.photofile=new File(this.photopath);
	}
	
	public void SetPhotoPath(String source) {
		this.photopath = source;
	}
	
	public void SetPhotoName() {
		this.name = photofile.getName();
	}
	
	public void SetBufferPhoto() {
		try {
			this.image= ImageIO.read(this.photofile);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	public void SetPhotoWidth() {
		this.width= image.getWidth();
	}
	
	public void SetPhotoHeight() {
		this.height= image.getHeight();
	}
	
	public void EncryptPhoto() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IOException {
		Cipher cipher=Cipher.getInstance("AES");
		SecretKeySpec key = new SecretKeySpec("passwordpassword".getBytes(),"AES");
		System.out.println(key);
		cipher.init(Cipher.ENCRYPT_MODE, key);
		CipherInputStream cipherIn=new CipherInputStream(new FileInputStream(this.photofile), cipher);
		FileOutputStream fos=new FileOutputStream(this.photofile);
		int i;
		while((i=cipherIn.read())!=-1){
		fos.write(i);
		}
		fos.close();
		cipherIn.close();
	}
	
	
	public void DecryptPhoto() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException {
		Cipher cipher=Cipher.getInstance("AES");
		SecretKeySpec key = new SecretKeySpec("passwordpassword".getBytes(),"AES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		CipherInputStream cipherIn=new CipherInputStream(new FileInputStream(this.photofile), cipher);
		FileOutputStream fos=new FileOutputStream(this.photofile);
		int i;
		while((i=cipherIn.read())!=-1){
		fos.write(i);
		}
		fos.close();
		cipherIn.close();
	}
	
	public Boolean CopyPhoto(String sourced,String user) throws IOException, URISyntaxException {
		CreateUserFolder(user);
		File source= new File(sourced);
		File dest =  new File(Photo.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+user+"/"+source.getName());
		InputStream input = null;
		OutputStream output = null;
		try {
			input = new FileInputStream(source);
			output = new FileOutputStream(dest);
			byte[] buf = new byte[1024];
			int bytesRead;
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
		} finally {
	
			input.close();
			output.close();
		}
		return true;
	}

	
	public Boolean CreateUserFolder(String user) {
		try {
			File root = new File(Photo.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()+user);
			if (Files.exists(root.toPath())) {
			    return false;
			    		
			}else {
				
				root.mkdir();
			}
		} catch (URISyntaxException e) {
			
			e.printStackTrace();
		}
		return true;
	}


}
