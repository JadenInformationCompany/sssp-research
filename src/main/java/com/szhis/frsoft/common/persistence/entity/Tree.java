package com.szhis.frsoft.common.persistence.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

/*
 * @JsonIdentityInfo在反序列化json出错Could not read JSON: (was java.lang.NullPointerException) 
 * (through reference chain: com.szhis.frsoft.entity.fr.xtcs.XTCSFL["id"]);
 * json中id如果传字符空就会出现上述错误，但是json中不存在id域解析又不报错,改为
 * 使用ObjectIdGenerators.IntSequenceGenerator.class问题解决.
 * 猜想：generator=ObjectIdGenerators.PropertyGenerator.class是使用对象某个属性的值作为唯一识别，
 * 如果传入json中的主键属性值为null导致不能确定实体唯一性，所以报错。jackson版本2.3.1
 */
/*@JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="id")*/
@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
@MappedSuperclass
public abstract class Tree <ID extends Serializable> {
	protected ID id;//id
	protected String mc;//名称
	protected ID sjid;//上级ID
	protected String ccbh;//层次编号
	protected Integer ccxh;//层次序号
	protected Integer sfyzjd; // 是否叶子节点

	
	@Id
	@GeneratedValue(generator = "tree_gen",strategy=GenerationType.TABLE)
	@Column(name="id")
	public ID getId() {
		return id;
	}
	
	public void setId(ID id) {
		this.id = id;
	}
	
	@Column(name="mc")
	public String getMc() {
		return mc;
	}
	
	public void setMc(String mc) {
		this.mc = mc;
	}
	

	@Column(name="sjid")
	public ID getSjid() {
		return sjid;
	}

	public void setSjid(ID sjid) {
		this.sjid = sjid;
	}
	
	@Column(name="ccbh")
	public String getCcbh() {
		return ccbh;
	}
	
	public void setCcbh(String ccbh) {
		this.ccbh = ccbh;
	}
	
	@Column(name="ccxh")
	public Integer getCcxh() {
		return ccxh;
	}
	
	public void setCcxh(Integer ccxh) {
		this.ccxh = ccxh;
	}

	@Column(name="sfyzjd")
	public Integer getSfyzjd() {
		return sfyzjd;
	}

	public void setSfyzjd(Integer sfyzjd) {
		this.sfyzjd = sfyzjd;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((mc == null) ? 0 : mc.hashCode());
		return result;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tree other = (Tree) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (mc == null) {
			if (other.mc != null)
				return false;
		} else if (!mc.equals(other.mc))
			return false;
		return true;
	}	
}
