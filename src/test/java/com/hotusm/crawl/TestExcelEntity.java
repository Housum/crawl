package com.hotusm.crawl;

import java.util.Date;

import com.hotusm.crawl.excel.annotation.Row;
import com.hotusm.crawl.excel.annotation.Sheet;

@Sheet("测试Excel")
public class TestExcelEntity {

	@Row(index=0,columnName="索引")
	private int index;
	@Row(index=1,columnName="权重")
	private double weight;
	@Row(index=2,columnName="标题")
	private String title;
	@Row(index=3,columnName="创建时间")
	private Date createBy;
	
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public double getWeight() {
		return weight;
	}
	public void setWeight(double weight) {
		this.weight = weight;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Date getCreateBy() {
		return createBy;
	}
	public void setCreateBy(Date createBy) {
		this.createBy = createBy;
	}
	@Override
	public String toString() {
		return "TestExcelEntity [index=" + index + ", weight=" + weight + ", title=" + title + ", createBy=" + createBy
				+ "]";
	}
	
	
}
