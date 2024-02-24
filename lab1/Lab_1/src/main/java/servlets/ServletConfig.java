package servlets;

import crud.Lab2CrudInterface;
import mock.Lab2CrudMock;

public class ServletConfig implements ServletConfigInterface{
    @Override
    public Lab2CrudInterface getCrud() {
        return new Lab2CrudMock();
    }
}
