package web;

import domain.entity.Engine;
import domain.model.binding.CarCreateBindingModel;
import domain.model.service.CarServiceModel;
import domain.model.service.UserServiceModel;
import org.modelmapper.ModelMapper;
import service.api.CarService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/cars/create")
public class CarCreateServlet extends HttpServlet {

    private final CarService carService;

    private final ModelMapper modelMapper;

    @Inject
    public CarCreateServlet(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/jsp/car-create.jsp")
                .forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CarCreateBindingModel carCreateBindingModel = new CarCreateBindingModel();

        carCreateBindingModel.setBrand(req.getParameter("brand"));
        carCreateBindingModel.setModel(req.getParameter("model"));
        carCreateBindingModel.setYear(req.getParameter("year"));
        carCreateBindingModel.setEngine(Engine.valueOf(req.getParameter("engine").toUpperCase())); // TO CHECK

        String usernameOfCreator = req.getSession().getAttribute("username").toString();

        UserServiceModel userServiceModel = new UserServiceModel();
        userServiceModel.setUsername(usernameOfCreator);

        CarServiceModel carServiceModel = this.modelMapper.map(carCreateBindingModel, CarServiceModel.class);
        carServiceModel.setUser(userServiceModel);

        this.carService.createCar(carServiceModel);

        resp.sendRedirect("/home");
    }
}
