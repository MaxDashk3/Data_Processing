package crud;

import entity.Entity;
import fileIO.FileIO;
import fileIO.FileIOInterface;

public class FileCrud implements Lab2CrudInterface {

    FileIOInterface fio;
    public FileCrud(){
        this.fio = new FileIO();
    }
    @Override
    public Entity readEntity() {
        return (Entity) fio.LoadFromFile();
    }

    @Override
    public void updateEntity(Entity entity) {
        fio.SaveToFile(entity);
    }
}
