package aeca.aladdin.domain.dto;

public class CsrResponseDTO {

    private String file;

    private int id;

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CsrResponseDTO() {
    }

    public CsrResponseDTO(String file, int id) {
        this.file = file;
        this.id = id;
    }

    @Override
    public String toString() {
        return "CsrResponseDTO{" +
                "id='" + id + '\'' +
                ", file=" + file +
                '}';
    }
}