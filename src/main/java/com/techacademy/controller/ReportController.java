package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.service.ReportService;
import com.techacademy.service.UserDetail;

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
	public String getRegister(@AuthenticationPrincipal UserDetail userDetail, @ModelAttribute Report report) {
		report.setEmployee(userDetail.getEmployee());
		// report登録画面に遷移
		return "report/register";
	}

	/** report登録処理 */
	@PostMapping("/register")
	public String postRegister(@AuthenticationPrincipal UserDetail userDetail, Report report) {
		report.setEmployee(userDetail.getEmployee());
		if (report.getTitle().equals("") || report.getContent().equals("") || report.getReportDate().equals("")) {
			return "report/register";
		}
		
		report.setCreatedAt(LocalDateTime.now());
		report.setUpdatedAt(LocalDateTime.now());

		service.saveReport(report);

		// 一覧画面にリダイレクト
		return "redirect:/report/list";
	}

	// ----- 追加:ここから -----
	// ----- 詳細画面 -----
	@GetMapping("/detail/{id}/")
	public String getReport(@AuthenticationPrincipal UserDetail userDetail,@PathVariable(name = "id", required = false) Integer id, Model model) {
		// idが指定されていたら検索結果、無ければ空のクラスを設定
		Report report = id != null ? service.getReport(id) : new Report();
		// Modelに登録
		model.addAttribute("report", report);
		int flag=0;
		if(report.getEmployee().getId()==userDetail.getEmployee().getId()) {
			flag=1;
		}
		model.addAttribute("flag", flag);
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
	@PostMapping("/update/{id}/")
	public String postUpdate(@AuthenticationPrincipal UserDetail userDetail,@PathVariable(name = "id", required = false) Integer id,Report report) {
		report.setEmployee(userDetail.getEmployee());
		if (report.getTitle().equals("") || report.getContent().equals("") || report.getReportDate().equals("")) {
			return "report/register";
		}
		Report updateReport = service.getReport(report.getId());
		updateReport.setReportDate(report.getReportDate());
		updateReport.setTitle(report.getTitle());
		updateReport.setContent(report.getContent());
		updateReport.setUpdatedAt(LocalDateTime.now());
		// テーブルを持ってくるために必要な部分↑

		service.saveReport(updateReport);
		// 一覧画面にリダイレクト
		return "redirect:/report/list";

	}
}
