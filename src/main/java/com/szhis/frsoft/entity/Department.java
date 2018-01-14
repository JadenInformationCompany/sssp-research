package com.szhis.frsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.DynamicUpdate;

/**
 * @desc @Cacheable(true)使用二级缓存
 * @author jaden.liu
 * @createTime 2017年12月15日 下午8:00:18
 * @version v1.0
 */
@Table(name = "SSSP_DEPARTMENTS")
@Entity
@DynamicUpdate
public class Department {
	private Integer id;
	private String departmentName;

	@Id
	@GeneratedValue(generator = "Department_id", strategy = GenerationType.TABLE)
	@TableGenerator(name = "Department_id", table = "FRID_DS", pkColumnName = "name", valueColumnName = "next", pkColumnValue = "Department_id", allocationSize = 1, initialValue = 100)
	@Column(name = "id")
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "departmentName")
	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", departmentName=" + departmentName + "]";
	}

}
