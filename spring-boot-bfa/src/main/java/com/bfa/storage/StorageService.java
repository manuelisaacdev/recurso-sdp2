package com.bfa.storage;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

	void init();

	String store(MultipartFile file);

	List<String> store(MultipartFile[] files);

	void delete(String ... filename);

}
