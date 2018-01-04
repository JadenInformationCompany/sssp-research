package com.szhis.frsoft.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

@Table(name = "test_JavaPojo")
@Entity
public class JavaPojo {
	private Integer lsh;
	private String name;

	/**
	 * @desc 描述该方法的功能
	 * @TableGenerator
	 * table主键映射表名
	 * pkColumnName主键字段名
	 * valueColumnName生成主键值字段名
	 * pkColumnValue主键字段值
	 * allocationSize每次变化大小
	 * initialValue初始值
	 * @author jaden.liu
	 * @createTime 2017年12月15日 下午7:04:58
	 * @return Integer
	 */
	@Id
	@GeneratedValue(generator = "JavaPojo_ID", strategy = GenerationType.TABLE)
	@TableGenerator(name = "JavaPojo_ID", table = "FRID_DS", pkColumnName = "name", valueColumnName = "next", pkColumnValue = "JavaPojo_id", allocationSize = 1, initialValue = 100)
	@Column(name = "id")
	public Integer getLsh() {
		return lsh;
	}

	public void setLsh(Integer lsh) {
		this.lsh = lsh;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "JavaPojo [lsh=" + lsh + ", name=" + name + "]";
	}

}
