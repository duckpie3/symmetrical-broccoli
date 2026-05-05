package com.product.api.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProduct;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

@Service
public class SvcProductImageImp implements SvcProductImage {

	@Autowired
	RepoProductImage repo;

	@Autowired
	RepoProduct repoProduct;

	@Value("${app.upload.dir}")
	private String uploadDir;

	@Value("${app.upload.images}")
	private String uploadImages;

	@Override
	public void uploadProductImage(DtoProductImageIn in) {
		try {
			// Decodifica la cadena Base64 a bytes
			byte[] imageBytes = Base64.getDecoder().decode(in.getImage());
			// Genera un nombre único para la imagen (se asume extensión PNG)
			String fileName = UUID.randomUUID().toString() + ".png";
			// Construye la ruta completa donde se guardará la imagen
			Path imagePath = Paths.get(uploadDir, uploadImages, "product", fileName);
			// Asegurarse de que el directorio exista
			Files.createDirectories(imagePath.getParent());
			// Escribir el archivo en el sistema de archivos
			Files.write(imagePath, imageBytes);
			// Crear la entidad CustomerImage y guardar la URL en la base de datos
			ProductImage productImage = new ProductImage();
			productImage.setProductId(in.getProductId());
			productImage.setImage("/" + uploadImages + "/product/" + fileName);
			productImage.setStatus(1);
			// Guardar la ruta de la imagen
			repo.save(productImage);
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al guardar el archivo");
		}

	}

	@Override
	public List<DtoProductImageOut> getProductImages(Integer productId) {
		try {
			validateProductId(productId);
			List<ProductImage> productImages = repo.findByProductId(productId);
			List<DtoProductImageOut> imageList = new ArrayList<>();
			for (ProductImage productImage : productImages) {
				DtoProductImageOut dto = new DtoProductImageOut();
				dto.setProductImageId(productImage.getProductImageId());
				dto.setImage(readProductImageFile(productImage.getImage()));
				imageList.add(dto);
			}
			return imageList;
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}

	}

	@Override
	public void deleteProductImage(Integer productId, Integer productImageId) {
		try {
			ProductImage productImage = repo.findById(productImageId)
					.orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "El id de la imagen no existe"));

			if (!productId.equals(productImage.getProductId())) {
				throw new ApiException(HttpStatus.BAD_REQUEST, "La imagen no pertenece al producto indicado");
			}

			String imageUrl = productImage.getImage();
			if (imageUrl != null && !imageUrl.isBlank()) {
				String normalizedPath = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;
				Path imagePath = Paths.get(uploadDir, normalizedPath);
				Files.deleteIfExists(imagePath);
			}

			repo.delete(productImage);

		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar el archivo.");
		}
	}

	private String readProductImageFile(String imageUrl) {
		try {
			if (imageUrl == null || imageUrl.isBlank()) {
				return "";
			}

			String normalizedPath = imageUrl.startsWith("/") ? imageUrl.substring(1) : imageUrl;
			Path path = Paths.get(uploadDir, normalizedPath);

			if (!Files.exists(path)) {
				return "";
			}

			byte[] imageBytes = Files.readAllBytes(path);
			return Base64.getEncoder().encodeToString(imageBytes);
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo.");
		}
	}

	private void validateProductId(Integer id) {
		try {
			if (repoProduct.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

}
