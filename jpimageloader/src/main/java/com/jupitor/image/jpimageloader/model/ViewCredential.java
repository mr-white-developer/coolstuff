package com.jupitor.image.jpimageloader.model;

import java.util.Date;

public class ViewCredential {

	private int currentRow = -1;
	private int maxRowsPerPage = -1;
	private String orderby = "";
	private String sorting = "";
	
	private String id;
	private String code;
	private String name;
	private int status;
	private Date cdate;
	private Date mdate;
	
	private String n1;
	private String n2;
	private String n3;
	private String n4;
	private String n5;
	private String n6;
	private String n7; 
	private String n8;
	private String n9;
	private String n10;

	private String t1;
	private String t2;
	private String t3;
	private String t4;
	private String t5;
	private String t6;
	private String t7;
	private String t8;
	private String t9;
	private String t10;
	
	
	private int s1;
	private int s2;
	private int s3;
	private int s4;
	private int s5;
	
	private boolean b1;
	private boolean b2;
	
	public ViewCredential(){
		this.id = "-1";
		this.code = "";
		this.name = "";
		this.status = -1;
		
		this.n1 = "-1";
		this.n2 = "-1";
		this.n3 = "-1";
		this.n4 = "-1";
		this.n5 = "-1";
		this.n6 = "-1";
		this.n7 = "-1";
		this.n8 = "-1";
		this.n9 = "-1";
		this.n10 = "-1";
		this.t1 = "";
		this.t2 = "";
		this.t3 = "";
		this.t4 = "";
		this.t5 = "";
		this.t6 = "";
		this.t7 = "";
		this.t8 = "";
		this.t9 = "";
		this.t10 = "";
		
		this.s1 = -1;
		this.s2 = -1;
		this.s3 = -1;
		this.s4 = -1;
		this.s5 = -1;
		
		this.b1 = false;
		this.b2 = false;
	}
	
	public int getCurrentRow() {
		return currentRow;
	}
	public void setCurrentRow(int currentRow) {
		this.currentRow = currentRow;
	}
	public int getMaxRowsPerPage() {
		return maxRowsPerPage;
	}
	public void setMaxRowsPerPage(int maxRowsPerPage) {
		this.maxRowsPerPage = maxRowsPerPage;
	}
	
	public String getN2() {
		return n2;
	}
	public void setN2(String n2) {
		this.n2 = n2;
	}
	public String getN3() {
		return n3;
	}
	public void setN3(String n3) {
		this.n3 = n3;
	}
	public String getN4() {
		return n4;
	}
	public void setN4(String n4) {
		this.n4 = n4;
	}
	public String getN5() {
		return n5;
	}
	public void setN5(String n5) {
		this.n5 = n5;
	}
	public String getN6() {
		return n6;
	}
	public void setN6(String n6) {
		this.n6 = n6;
	}
	public String getN7() {
		return n7;
	}
	public void setN7(String n7) {
		this.n7 = n7;
	}
	public String getN8() {
		return n8;
	}
	public void setN8(String n8) {
		this.n8 = n8;
	}
	public String getN9() {
		return n9;
	}
	public void setN9(String n9) {
		this.n9 = n9;
	}
	public String getN10() {
		return n10;
	}
	public void setN10(String n10) {
		this.n10 = n10;
	}
	public String getT1() {
		return t1;
	}
	public void setT1(String t1) {
		this.t1 = t1;
	}
	public String getT2() {
		return t2;
	}
	public void setT2(String t2) {
		this.t2 = t2;
	}
	public String getT3() {
		return t3;
	}
	public void setT3(String t3) {
		this.t3 = t3;
	}
	public String getT4() {
		return t4;
	}
	public void setT4(String t4) {
		this.t4 = t4;
	}
	public String getT5() {
		return t5;
	}
	public void setT5(String t5) {
		this.t5 = t5;
	}
	public String getT6() {
		return t6;
	}
	public void setT6(String t6) {
		this.t6 = t6;
	}
	public int getS1() {
		return s1;
	}
	public void setS1(int s1) {
		this.s1 = s1;
	}
	public int getS2() {
		return s2;
	}
	public void setS2(int s2) {
		this.s2 = s2;
	}
	public int getS3() {
		return s3;
	}
	public void setS3(int s3) {
		this.s3 = s3;
	}
	public int getS4() {
		return s4;
	}
	public void setS4(int s4) {
		this.s4 = s4;
	}
	public int getS5() {
		return s5;
	}
	public void setS5(int s5) {
		this.s5 = s5;
	}
	public boolean isB1() {
		return b1;
	}
	public void setB1(boolean b1) {
		this.b1 = b1;
	}
	public boolean isB2() {
		return b2;
	}
	public void setB2(boolean b2) {
		this.b2 = b2;
	}

	public String getN1() {
		return n1;
	}

	public void setN1(String n1) {
		this.n1 = n1;
	}

	public String getOrderby() {
		return orderby;
	}

	public String getSorting() {
		return sorting;
	}

	public void setOrderby(String orderby) {
		this.orderby = orderby;
	}

	public void setSorting(String sorting) {
		this.sorting = sorting;
	}

	public String getT7() {
		return t7;
	}

	public String getT8() {
		return t8;
	}

	public String getT9() {
		return t9;
	}

	public String getT10() {
		return t10;
	}

	public void setT7(String t7) {
		this.t7 = t7;
	}

	public void setT8(String t8) {
		this.t8 = t8;
	}

	public void setT9(String t9) {
		this.t9 = t9;
	}

	public String getId() {
		return id;
	}

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public int getStatus() {
		return status;
	}

	public Date getCdate() {
		return cdate;
	}

	public Date getMdate() {
		return mdate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setCdate(Date cdate) {
		this.cdate = cdate;
	}

	public void setMdate(Date mdate) {
		this.mdate = mdate;
	}

	public void setT10(String t10) {
		this.t10 = t10;
	}
	
}
