package com.techacademy.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;

@Controller
@RequestMapping("report")
public class ReportController {
	private static final String LocalDate = null;
	private final ReportService service;
	@Autowired
	public ReportController(ReportService service) {
		this.service = service;
	}

	/** 一覧画面を表示 */
	@GetMapping("/list")
	public String getList(Model model) {
		// 全件検索結果をModelに登録
		model.addAttribute("reportlist", service.getReportList());
		int count = service.getReportList().size();
		model.addAttribute("count", count);
		// ↑全何件の出し方。
		// employee/list.htmlに画面遷移
		return "report/list";
	}

	// ----- 追加:ここから -----
	/** report登録画面を表示 */
	@GetMapping("/register")
	public String getRegister(@ModelAttribute Report report) {
		// report登録画面に遷移
		return "report/register";
	}

	/** report登録処理 */
	@PostMapping("/register")
	public String postRegister(Report report) {
	
			return "report/register";
	}
	

	// ----- 追加:ここから -----
	// ----- 詳細画面 -----
	@GetMapping("/detail")
	public String getReport(@PathVariable(name = "id", required = false) Integer id, Model model) {
		// idが指定されていたら検索結果、無ければ空のクラスを設定
		Report report = id != null ? service.getReport(id) : new Report();
		// Modelに登録
		model.addAttribute("report", report);
		// report/detail.htmlに画面遷移
		return "report/detail";
	}

	// -----更新画面-----
	@GetMapping(value = { "/update", "/update/{id}/" })
	public String getUpdate(@PathVariable(name = "id", required = false) Integer id, Model model) {
		// idが指定されていたら検索結果、無ければ空のクラスを設定
		Report report = id != null ? service.getReport(id) : new Report();

		// Modelに登録
		model.addAttribute("report", report);
		// report/update.htmlに画面遷移
		return "report/update";

	}

	// ** Employee更新処理 */
	@PostMapping("/update/{id}")
	public String postUpdate(Report report) {
		if (report.getTitle().equals("") || report.getContent() == null) {
			return "report/update";
		}
		Report updateReport = service.getReport(report.getId());
		// テーブルを持ってくるために必要な部分↑

		service.saveReport(updateReport);
		// 一覧画面にリダイレクト
		return "redirect:/Report/list";

	}
}
