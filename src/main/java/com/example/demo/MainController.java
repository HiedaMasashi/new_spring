package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MainController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView indexGet(ModelAndView mv) {
		mv.addObject("name", "名前がここに入ります");
		mv.addObject("age", "年齢がここに入ります");
		mv.addObject("height", "身長がここに入ります");
		mv.setViewName("index");
		return mv;
	}

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ModelAndView indexPost(ModelAndView mv, @RequestParam("nameVal") String name,
			@RequestParam("ageVal") String age, @RequestParam("heightVal") String height) {
		mv.addObject("name", name);
		mv.addObject("age", age);
		mv.addObject("height", height);
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping("/{name}")
	public ModelAndView index(@PathVariable String name, ModelAndView mv) {
		mv.addObject("name", name);
		mv.setViewName("index");
		return mv;
	}
	
	@RequestMapping(value = "/html100")
	public ModelAndView indexPost(ModelAndView mv) {
		ArrayList<String[]> customers = new ArrayList<String[]>();
		customers.add(new String[] { "佐藤HTML太郎", "35歳", "男性" });
		customers.add(new String[] { "鈴木Java五郎", "24歳", "男性" });
		customers.add(new String[] { "高橋CSS子", "29歳", "女性" });
		mv.addObject("customers", customers);
		mv.setViewName("html100");
		return mv;
	}
	
	@Autowired
	UserDataRepository repository;

	@RequestMapping(value = "/userData", method = RequestMethod.GET)
	public ModelAndView userDataGet(ModelAndView mv) {
		List<UserData> customers = repository.findAll();
		mv.addObject("customers", customers);
		mv.setViewName("userData");
		return mv;
	}

	@RequestMapping(value = "/userData", method = RequestMethod.POST)
	public ModelAndView userDataPost(@ModelAttribute("formModel") UserData userData, ModelAndView mv) {
		repository.saveAndFlush(userData);
		return new ModelAndView("redirect:/userData");
	}
	
	@RequestMapping(value = "/mypage/", method = RequestMethod.GET)
	public ModelAndView mypage(@ModelAttribute UserData userData,
			ModelAndView mv) {
		List<UserData> user = repository.findByIdIsNotNullOrderByIdDesc();
		mv.addObject("user", user);
		mv.setViewName("mypage");
		return mv;
	}
	
	@RequestMapping(value = "/mypage/{id}", method = RequestMethod.GET)
	public ModelAndView mypageGet(@ModelAttribute UserData userData, ModelAndView mv,
			@PathVariable long id) {
		Optional<UserData> user = repository.findById(id);
		mv.addObject("userData", user.get());
		mv.setViewName("mypage");
		return mv;
	}

	@RequestMapping(value = "/mypage/", method = RequestMethod.POST)
	public ModelAndView mypagePost(@ModelAttribute UserData userData, ModelAndView mv) {
		repository.saveAndFlush(userData);
		return new ModelAndView("redirect:/userData");
	}
	
	@RequestMapping(value = "/delete/", method = RequestMethod.POST)
	public ModelAndView delete(@RequestParam("id") long id, ModelAndView mv) {
		repository.deleteById(id);
		return new ModelAndView("redirect:/userData");
	}
	
}