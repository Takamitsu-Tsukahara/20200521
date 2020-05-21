package demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.domainModel.ModelUser;
import demo.repository.RepositoryUser;
import demo.request.RequestUser;

@Controller
public class ControllerUser {

    @Autowired
    RepositoryUser useRepository;

    @GetMapping("/")
    public String index(Model model) {

    	//新規入力画面
    	model.addAttribute("input_screen", new RequestUser());

        return "index";
    }

	@RequestMapping("/add")
	public String add(@ModelAttribute ("YMD") RequestUser YMD, Model model) {
		System.out.println(YMD);
		LocalDate y = LocalDate.parse(YMD.getYMD());
		System.out.println(y);
		ModelUser UserData = useRepository.selectByPrimaryKey(1);
		System.out.println(UserData);
		System.out.println(y.plusYears(1));
		System.out.println(y.plusYears(UserData.getYear()));

		ModelUser loopUserData;
		List<ModelUser> list = useRepository.selectByExample();
		for (int i=1;i<=list.size();i++) {
			loopUserData = useRepository.selectByPrimaryKey(i);
			System.out.println(loopUserData);
		}
		return "add";
	}
}