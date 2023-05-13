package com.techacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;
import javax.persistence.CascadeType;

@Data
@Entity
@Table(name = "report")
public class  Report{


    /** 主キー。自動生成 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    /** 日報の日付 */
    @Column(nullable = false)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate reportDate;

    /** タイトル。255桁。null不許可 */
    @Column(length = 255, nullable = false)
    private String title;

    /** 内容 */
    @Column(nullable = false)
    @Type(type="text")
    private  String content;
    
    /** 従業員のテーブルID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id",nullable = false)
    private Employee employee;
    
    /** 登録日時 */
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    /** 更新日時 */
    @Column(nullable = false)
    private LocalDateTime updatedAt;
    
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL)
    private List<Report> reports;

	public Report getReportDate(String localdate) {
		// TODO 自動生成されたメソッド・スタブ
		return null;
	}
    
}