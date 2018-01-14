package com.szhis.frsoft.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicUpdate;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name = "SSSP_EMPLOYEES")
@Entity
@DynamicUpdate
public class Employee {
	private Integer id;
	private String lastName;
	private String email;
	private Date birth;
	private Date createTime;
	private Department department;

	@Id
	@GeneratedValue(generator = "Employee_id", strategy = GenerationType.TABLE)
	@TableGenerator(name = "Employee_id", table = "FRID_DS", pkColumnName = "name", valueColumnName = "next", pkColumnValue = "Employee_id", allocationSize = 1, initialValue = 100)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @desc 
	 * @Temporal 保存数据库时使用的格式
	 * @DateTimeFormat json接收格式
	 * @author jaden.liu
	 * @createTime 2018年1月3日 下午8:19:27
	 * @return Date
	 */
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	//@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * @desc 这个是扩展表，要斩断与主表的json关联
	 * @author jaden.liu
	 * @createTime 2018年1月3日 下午8:06:03
	 * @return Department
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "DEPARTMENT_ID")
	@JsonIgnore
	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", lastName=" + lastName + ", email=" + email + ", birth=" + birth
				+ ", createTime=" + createTime + ", department=" + department + "]";
	}

}
