package br.com.inatel.jamming.service.cloudinary;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

@Service
public class CloudinaryService {

	private Cloudinary cloudinary;
	
	public CloudinaryService() {
		cloudinary = new Cloudinary(ObjectUtils.asMap(
				"cloud_name","descvawhl",
				"api_key","938163524347675",
				"api_secret","VPQfZYFzOhSadJux_WBA2lrTs8Q"));
	}
	
	@SuppressWarnings("rawtypes")
	public Map upload(MultipartFile multipartFile, String folder, String fileType) throws IOException {
		File file = convert(multipartFile);
		Map params = ObjectUtils.asMap("folder", "jamming/" + folder, "unique_filename", true, "overwrite", true,
				"resource_type", fileType);
		Map result = cloudinary.uploader().upload(file, params);
		file.delete();
		return result;
	}
	
	@SuppressWarnings("rawtypes")
	public Map delete(String id) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return result;
	}

	private File convert(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}
	
}
