package app.config;

import app.util.FileUtil;
import app.util.FileUtilImpl;
import org.modelmapper.ModelMapper;

import javax.enterprise.inject.Produces;

public class ApplicationBeanConfiguration {

    @Produces
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
