package com.product;
import java.io.*;
import java.util.LinkedList;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer category_id;
    private String category;
    private String tag;
    private Integer status;

    public Integer getCategory_id() {
        return category_id;
    }

    public String getCategory() {
        return category;
    }

    public String getTag() {
        return tag;
    }

    public Integer getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return String.format("{%d, %s, %s, %d}", category_id, category, tag, status);
    }

    public Category(Integer category_id, String category, String tag, Integer status) {
        this.category_id = category_id;
        this.category = category;
        this.tag = tag;
        this.status = status;
    }

    private static LinkedList<Category> registeredCategories = new LinkedList<>();

    public static void getCategories() {
        if (registeredCategories.isEmpty()) {
            System.out.println("No existen categorias registradas.");
        } else {
            System.out.println(registeredCategories);
        }
    }

    public static void createCategory(Category newCategory) {
        // Verifica que id, category y tag sean unicos
        for (Category category : registeredCategories) {
            if (category.getCategory_id().equals(newCategory.getCategory_id())
                    || category.getCategory().equals(newCategory.getCategory())
                    || category.getTag().equals(newCategory.getTag())) {
                System.out.println("id, category, tag tienen que ser unicos.");
                return;
            }
        }
        // Agrega categoria
        registeredCategories.add(newCategory);
        System.out.println("Categoria creada exitosamente.");
    }

    public static void deleteCategory(Integer id) {
        for (Category category : registeredCategories) {
            if (category.getCategory_id().equals(id)) {
                category.status = 0;
            }
        }
    }

    public static void saveCategories() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("lista"))) {
            oos.writeObject(registeredCategories);
            oos.close();
        } catch (Exception e) {
            System.out.println("Error al guardar.");
        }
    }

    @SuppressWarnings("unchecked")
    public static void loadCategories() {
        File file = new File("lista");
        if (!file.exists()) {
            return;
        }
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("lista"))) {
            registeredCategories = (LinkedList<Category>) ois.readObject();
        } catch (Exception e) {
            System.out.println("Error al cargar.");
        }
    }

    public static void main(String[] args) {
        loadCategories();
        switch (args[0]) {
            case "get":
                getCategories();
                break;
            case "create":
                createCategory(new Category(Integer.parseInt(args[1]), args[2], args[3], Integer.parseInt(args[4])));
                saveCategories();
                break;
            case "delete":
                deleteCategory(Integer.parseInt(args[1]));
                saveCategories();
                break;
            default:
                break;
        }
    }
}      