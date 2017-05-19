package com.amin.realty.cloud.aws;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amin.realty.service.util.RandomUtil;

public class S3WrapperTest {

	private static final String uploadFile = "C:/Users/vsundgo/Desktop/keeper/images.jpg";
	private static final String downloadedFile = "C:/Users/vsundgo/Desktop/keeper/image-downloaded.jpg";

	public static void main(String[] args) throws IOException {
		// testUploadSingle();
		testDownload();
	}

	private static void testUploadSingle() throws FileNotFoundException {
		String key = RandomUtil.generatePassword();

		System.err.println(key);

		S3Wrapper.upload(new FileInputStream(uploadFile), key, CannedAccessControlList.PublicRead);
	}

	private static void testDownload() throws IOException {

		byte[] byteArray = S3Wrapper.download("7kTigk2ONbYGBFjIcCVx");

		FileOutputStream fos = new FileOutputStream(downloadedFile);
		fos.write(byteArray);
		fos.close();
	}

}
