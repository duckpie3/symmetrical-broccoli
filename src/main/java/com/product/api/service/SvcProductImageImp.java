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

import com.product.api.dto.in.DtoProductImageIn;
import com.product.api.dto.out.DtoProductImageOut;
import com.product.api.entity.ProductImage;
import com.product.api.repository.RepoProductImage;
import com.product.exception.ApiException;
import com.product.exception.DBAccessException;

public class SvcProductImageImp implements SvcProductImage {

	@Autowired
	RepoProductImage repo;

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
			Path imagePath = Paths.get(uploadDir, "img", "customer", fileName);
			// Asegurarse de que el directorio exista
			Files.createDirectories(imagePath.getParent());
			// Escribir el archivo en el sistema de archivos
			Files.write(imagePath, imageBytes);
			// Crear la entidad CustomerImage y guardar la URL en la base de datos
			ProductImage productImage = new ProductImage();
			productImage.setProductId(in.getProductId());
			productImage.setImage("/product/" + fileName);
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

			String[] encodedImages = readProductImageFiles(productId);
			List<DtoProductImageOut> imageList = new ArrayList<>();
			for (String image : encodedImages) {
				DtoProductImageOut dto = new DtoProductImageOut();
				dto.setImage(image);
				imageList.add(dto);
			}
			return imageList;
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}

	}

	@Override
	public void deleteProductImage(Integer productId, Integer productImageId) {
		// TODO
	}

	private String[] readProductImageFiles(Integer product_id) {
		try {
			// Obtiene las imagenes asociadas al producto
			ProductImage[] productImages = repo.findByProductId(product_id).toArray(new ProductImage[0]);

			// Si no hay imagenes, devolver un arreglo vacio
			if (productImages == null || productImages.length == 0) {
				return new String[0];
			}

			// Crear un arreglo para almacenar las imagenes codificadas en Base64
			String[] imagesUrl = new String[productImages.length];

			// Iterar sobre las imagenes y procesarlas
			for (int i = 0; i < productImages.length; i++) {
				String imageUrl = productImages[i].getImage();

				// Si la URL comienza con "/" la eliminamos para obtener la ruta relativa
				if (imageUrl.startsWith("/")) {
					imageUrl = imageUrl.substring(1);
				}

				// Construir el Path
				Path imagePath = Paths.get(uploadDir, imageUrl);

				// Verifica que el archivo exista
				if (!Files.exists(imagePath)) {
					imagesUrl[i] = ""; // Si el archivo no existe, asignar una cadena vacia
					continue; // Continuar con la siguiente imagen
				}

				// Leer los bytes de la imagen y codificarlos a Base64
				byte[] imageBytes = Files.readAllBytes(imagePath);
				imagesUrl[i] = Base64.getEncoder().encodeToString(imageBytes); // Almacenar en el arreglo
			}

			// Devolver el arreglo con las imagenes codificadas
			return imagesUrl;

		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		} catch (IOException e) {
			throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al leer el archivo.");
		}
	}

	private void validateProductId(Integer id) {
		try {
			if (repo.findById(id).isEmpty()) {
				throw new ApiException(HttpStatus.NOT_FOUND, "El id del producto no existe");
			}
		} catch (DataAccessException e) {
			throw new DBAccessException(e);
		}
	}

}
