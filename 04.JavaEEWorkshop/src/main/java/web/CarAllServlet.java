package web;

import domain.model.view.CarViewModel;
import org.modelmapper.ModelMapper;
import service.api.CarService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/cars/all")
public class CarAllServlet extends HttpServlet {

    private final CarService carService;

    private final ModelMapper modelMapper;

    @Inject
    public CarAllServlet(CarService carService, ModelMapper modelMapper) {
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CarViewModel> carViewModels = this.carService
                .getAllCars()
                .stream()
                .map(csv -> this.modelMapper.map(csv, CarViewModel.class))
                .collect(Collectors.toList());

        req.setAttribute("carViewModels", carViewModels);

        req.getRequestDispatcher("/jsp/cars-all.jsp")
                .forward(req, resp);
    }
}
