package demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.domainModel.ModelUser;
import demo.listYearMonthDay.ListYearMonthDay;
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
//		//取り込んだ日にち確認
//		System.out.println(YMD);
		// (1) LocalData型、変数holdingYearMonthDayに入力された日にちを入れる。
		LocalDate holdingYearMonthDay = LocalDate.parse(YMD.getYMD());
//		//確認
//		System.out.println(holdingYearMonthDay);
//		System.out.println("****************************************");
		// (2) DBから取り出し
		ModelUser putDB_Id_Name_Year_Month_Day = useRepository.selectByPrimaryKey(1);
//		System.out.println(putDB_Id_Name_Year_Month_Day);
//		System.out.println(holdingYearMonthDay.plusYears(1));
//		System.out.println(holdingYearMonthDay.plusYears(putDB_Id_Name_Year_Month_Day.getYear()));
//		System.out.println("****************************************");
		// (3) 処理開始
		//ループの回数を取り出すためにDBから取り出し。
		List<ModelUser> list = useRepository.selectByExample();
		// (4) 処理中の日付の枠
		LocalDate inLoopYearMonthDay = holdingYearMonthDay;
		List<ListYearMonthDay> listYMD = new ArrayList<>();
//		System.out.println("****************************************");
		// (5) DB内の数だけ処理をする。
		for (int i=1;i<=list.size();i++) {
			// (6) 変数 i の変化で取り出す ID が変化
			putDB_Id_Name_Year_Month_Day = useRepository.selectByPrimaryKey(i);
//			//確認
//			System.out.println("入力日付："+holdingYearMonthDay);
//			System.out.println("DB取り出し："+putDB_Id_Name_Year_Month_Day);
			// (7) 入力
			inLoopYearMonthDay = holdingYearMonthDay.plusYears(putDB_Id_Name_Year_Month_Day.getYear());
			inLoopYearMonthDay = inLoopYearMonthDay.plusMonths(putDB_Id_Name_Year_Month_Day.getMonth());
			inLoopYearMonthDay = inLoopYearMonthDay.plusDays(putDB_Id_Name_Year_Month_Day.getDay());
//			//確認
//			System.out.println(putDB_Id_Name_Year_Month_Day.getId());
//			System.out.println(putDB_Id_Name_Year_Month_Day.getName());
//			System.out.println(inLoopYearMonthDay);
//			System.out.println("****************************************");
			// (8) リストに入る形にする
			ListYearMonthDay LYMD = new ListYearMonthDay();
			LYMD.setId(putDB_Id_Name_Year_Month_Day.getId());
			LYMD.setName(putDB_Id_Name_Year_Month_Day.getName());
			LYMD.setYear(putDB_Id_Name_Year_Month_Day.getYear());
			LYMD.setMonth(putDB_Id_Name_Year_Month_Day.getMonth());
			LYMD.setDay(putDB_Id_Name_Year_Month_Day.getDay());
			LYMD.setYearMonthDay(inLoopYearMonthDay);
			listYMD.add(LYMD);
//			//確認
//			System.out.println(listYMD);
//			System.out.println("****************************************");
		}
		// (9) 表示出力
		//リスト表示
		model.addAttribute("List", listYMD);
    	//新規入力画面
    	model.addAttribute("input_screen", new RequestUser());
    	// (10) 表示するHTML名選択
		return "index";
	}
}














