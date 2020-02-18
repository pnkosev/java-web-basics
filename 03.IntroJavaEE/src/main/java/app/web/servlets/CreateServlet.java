package app.web.servlets;

import app.domain.models.binding.CarCreateBindingModel;
import app.domain.models.service.CarServiceModel;
import app.service.CarService;
import app.util.FileUtil;
import org.modelmapper.ModelMapper;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/create")
public class CreateServlet extends HttpServlet {

    private final static String CREATE_PATH = "src/main/webapp/views/create.html";

    private final FileUtil fileUtil;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Inject
    public CreateServlet(FileUtil fileUtil, CarService carService, ModelMapper modelMapper) {
        this.fileUtil = fileUtil;
        this.carService = carService;
        this.modelMapper = modelMapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        String createHTML = this.fileUtil.readFile("C:\\Users\\CDA1\\Desktop\\Projets\\Java\\Java-Web\\03.IntroJavaEE\\src\\main\\webapp\\views\\create.html");

        PrintWriter out = res.getWriter();

        out.println(createHTML);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        CarCreateBindingModel carBindingModel = new CarCreateBindingModel();
        carBindingModel.setBrand(req.getParameter("brand"));
        carBindingModel.setModel(req.getParameter("model"));
        carBindingModel.setYear(Integer.parseInt(req.getParameter("year")));
        carBindingModel.setEngine(req.getParameter("engine"));

        this.carService.createCar(this.modelMapper.map(carBindingModel, CarServiceModel.class));

        res.sendRedirect("/all");
    }
}
