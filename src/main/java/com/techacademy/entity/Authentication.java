package com.techacademy.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "authentication")
public class Authentication {


    /** 主キー。自動生成.社員番号 */
    @Id
    @Column(length = 20, nullable = false)
    private String code;

    /** パスワード。225桁。null不許可 */
    @Column(length = 225, nullable = false)
    private String password;

    /** 権限 */
    public static enum Role{
    一般,管理者
    }
    @Column(length = 10,nullable=false)
    @Enumerated(EnumType.STRING)
    private Role role;
    

    /** 従業員のテーブルID */
    @OneToOne
    @JoinColumn(name="employee_id", referencedColumnName="id",nullable = false)
    private Employee employee;
}
    
    