package com.techacademy.service;


import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.techacademy.entity.Employee;
import com.techacademy.entity.Report;
import com.techacademy.repository.ReportRepository;

@Service
public class ReportService {
    private final ReportRepository reportRepository ;

    public ReportService(ReportRepository repository) {
        this.reportRepository = repository;
    }

    /** 全件を検索して返す */
    public List<Report> getReportList() {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findAll();
    }
        
    /** reportの登録を行なう */
    @Transactional
    public Report saveReport(Report report) {
        return reportRepository.save(report);
    }
   
    /**reportを1件検索して返す */
	public Report getReport(Integer id) {
		// TODO 自動生成されたメソッド・スタブ
		return reportRepository.findById(id).get() ;
	}

	/** Employeeの削除を行なう */
	@Transactional
	public void deleteReport(Set<Integer> idck) {
		for (Integer id : idck) {
	       reportRepository.deleteById(id);
	     
		}	
	}
	/** 全件を検索して返す */
    public List<Report> getReportEmployeeList(Employee employee) {
        // リポジトリのfindAllメソッドを呼び出す
        return reportRepository.findByEmployee(employee);
    }
}
