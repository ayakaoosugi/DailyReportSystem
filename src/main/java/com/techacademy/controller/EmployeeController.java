package com.techacademy.controller;

import java.time.LocalDateTime;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.techacademy.entity.Employee;
import com.techacademy.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {
	private final EmployeeService service;

	public EmployeeController(EmployeeService service) {
		this.service = service;
	}

	/** 一覧画面を表示 */
	@GetMapping("/list")
	public String getList(Model model) {
		// 全件検索結果をModelに登録
		model.addAttribute("employeelist", service.getEmployeeList());
		int count=service.getEmployeeList().size();
		model.addAttribute("count",count);
		//↑全何件の出し方。
		// employee/list.htmlに画面遷移
		return "employee/list";
	}

	// ----- 追加:ここから -----
	/** employee登録画面を表示 */
	@GetMapping("/register")
	public String getRegister(@ModelAttribute Employee employee) {
		// employee登録画面に遷移
		return "employee/register";
	}

	/** Employee登録処理 */
	@PostMapping("/register")
	public String postRegister(Employee employee) {
		try {
			employee.setCreatedAt(LocalDateTime.now());
	    	employee.setUpdatedAt(LocalDateTime.now());
			employee.setDeleteFlag(0);
			// Employee登録
			service.saveEmployee(employee);
		} catch (Exception e) {
			return "employee/register";
		}
		// 一覧画面にリダイレクト
		return "redirect:/employee/list";
	}

	// ----- 追加:ここから -----
	// ----- 詳細画面 -----
	@GetMapping(value = { "/detail", "/detail/{id}/" })
	public String getEmployee(@PathVariable(name = "id", required = false) Integer id, Model model) {
		// idが指定されていたら検索結果、無ければ空のクラスを設定
		Employee employee = id != null ? service.getEmployee(id) : new Employee();
		// Modelに登録
		model.addAttribute("employee", employee);
		// employee/detail.htmlに画面遷移
		return "employee/detail";
	}

	// -----更新画面-----
	@GetMapping(value = { "/update", "/update/{id}/" })
	public String getUpdate(@PathVariable(name = "id", required = false) Integer id, Model model) {
		// idが指定されていたら検索結果、無ければ空のクラスを設定
		Employee employee = id != null ? service.getEmployee(id) : new Employee();
		employee.getAuthentication().setPassword("");
		// Modelに登録
		model.addAttribute("employee", employee);
		// employee/update.htmlに画面遷移
		return "employee/update";

	}

	// ** Employee更新処理 */
	@PostMapping("/update/{id}")
    public String postUpdate(Employee employee) {
       Employee updateEmployee =service.getEmployee(employee.getId());
       //テーブルを持ってくるために必要な部分↑
       // ↓ここを追加　氏名、パスワード、権限を画面側から来た値へ置き換え
       updateEmployee.setName(employee.getName());
       updateEmployee.getAuthentication().setRole(employee.getAuthentication().getRole());
       updateEmployee.setUpdatedAt(LocalDateTime.now());
       // ↑ここを追加
            // Employee登録
          //分岐分を作成する　パスワードが空の場合とパスワードが入ってる場合　ifぶんで
            //からっぽの場合はテーブル側のパスワードをsetする　からっぽじゃない場合　入ったものを暗号化してsetする
           if(!employee.getAuthentication().getPassword().equals("")) {
        updateEmployee.getAuthentication().setPassword(employee.getAuthentication().getPassword());
           }
           service.saveEmployee(updateEmployee);
            // 一覧画面にリダイレクト
            return "redirect:/employee/list";
            
            
    }

	/** Employee削除処理 */
	@GetMapping("/delete/{id}") // ←このidからemployeeテーブルのレコードを取得
	public String getDelete(@PathVariable(name = "id", required = true) Integer id) {
		Employee employee = service.getEmployee(id); // ここで実際の取得
		employee.setDeleteFlag(1);
		// Employee登録
		service.saveEmployee(employee);
		// 一覧画面にリダイレクト
		return "redirect:/employee/list";
	}
}
