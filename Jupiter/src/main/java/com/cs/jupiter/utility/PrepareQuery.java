package com.cs.jupiter.utility;

import java.util.ArrayList;
import java.util.List;

public class PrepareQuery {
	private List<String> preList;
	private List<String> selectCol;
	public int ID = 0;
	public int VARCHAR = 1;
	public int NUMBER = 2;
	public int DATE = 3;

	public enum Operator {
		EQUAL(0, "="), LESSTHEN(1, "<"), LESSTHEN_EQUAL(2, "<="), GREATERTHEN(3, ">"), GREATERTHEN_EQUAL(4,
				">="), LIKE_START(5, "like"), LIKE_END(6, "like"), LIKE_ALL(7, "like");

		Operator(int code, String description) {
			this.code = code;
			this.description = description;
		}

		private final int code;
		private final String description;

		public int getCode() {
			return code;
		}

		public String getDescription() {
			return description;
		}
	}

	public enum Type {
		ID, VARCHAR, DATE, NUMBER,BOOLEAN;
		Type() {
		}
	}

	private String getDesc(Operator code) {
		if (code == Operator.EQUAL) {
			return Operator.EQUAL.getDescription();
		} else if (code == Operator.LESSTHEN) {
			return Operator.LESSTHEN.getDescription();
		} else if (code == Operator.LESSTHEN_EQUAL) {
			return Operator.LESSTHEN_EQUAL.getDescription();
		} else if (code == Operator.GREATERTHEN) {
			return Operator.GREATERTHEN.getDescription();
		} else if (code == Operator.GREATERTHEN_EQUAL) {
			return Operator.GREATERTHEN_EQUAL.getDescription();
		} else {
			return Operator.LIKE_START.getDescription();
		}
	}

	public PrepareQuery() {
		this.preList = new ArrayList<>();
		this.selectCol = new ArrayList<>();
	}

	public void add(String col, Object val, Operator e, Type type) throws Exception {

		if (val.getClass() == String.class) {
			if (val.equals("") || val.equals("-1"))
				return;
		} else if (val.getClass() == Integer.class) {
			if ((int) val == -1) {
				return;
			}
		} else if (val.getClass() == Double.class) {
			if ((double) val == -1) {
				return;
			}
		} else if (val.getClass() == Long.class) {
			if ((long) val == -1 || (long) val == 0)
				return;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(col);
		if (type == Type.VARCHAR) {

			sb.append(" " + getDesc(e));
			if (e == Operator.LIKE_START) {
				sb.append("'" + val + "%'");
			} else if (e == Operator.LIKE_END) {
				sb.append("'%" + val + "'");
			} else if (e == Operator.LIKE_ALL) {
				sb.append("'%" + val + "%'");
			} else {
				sb.append("'" + val + "'");
			}

		} else if (type == Type.DATE) {
			sb.append(" " + getDesc(e));
			sb.append("'" + val + "'");
		} else if (type == Type.NUMBER || type == Type.ID) {
			sb.append(" " + getDesc(e));
			sb.append(val);
		}else{
			sb.append(" " + getDesc(e));
			sb.append(val);
		}
		preList.add(sb.toString());
	}

	

	public void addSelect(String col) throws Exception {
		this.selectCol.add(col);
	}

	private String createWhereQuery() {
		StringBuilder sb = new StringBuilder();
		if (preList.size() != 0)
			sb.append(" where");

		for (int i = 0; i < preList.size(); i++) {
			if (i != 0) {
				sb.append(" and ");
			}
			sb.append(" " + preList.get(i));
		}
		return sb.toString();
	}

	public String createWhereStatement() {
		return createWhereQuery();
	}

	public String createWhereStatement(int current, int max) {
		String sql = createWhereQuery();
		String pg = "";
		if (current != -1 && max != -1) {
			pg = " offset " + current + " rows fetch next " + max + " rows only";
			sql += pg;
		}
		return sql;
	}

	public String createWhereStatement(int current, int max, String orderbyCol, String sorting) {
		String sql = createWhereQuery();
		String pg = "";
		if (!orderbyCol.equals("") && !sorting.equals("")) {
			sql += " order by " + orderbyCol + " " + sorting;
		}
		if (current != -1 && max != -1) {
			pg = " offset " + current + " rows fetch next " + max + " rows only";
			sql += pg;
		}
		return sql;
	}

	public String createWhereStatement(int current, int max, String select, String orderbyCol, String sorting) {
		String sql = createWhereQuery();
		String pg = "";
		if (!select.equals("")) {
			sql += (" group by ").concat(select);
		}
		if (!orderbyCol.equals("") && !sorting.equals("")) {
			sql += " order by " + orderbyCol + " " + sorting;
		}
		if (current != -1 && max != -1) {
			pg = " offset " + current + " rows fetch next " + max + " rows only";
			sql += pg;
		}
		return sql;
	}

	private String createSetQuery() {
		StringBuilder sb = new StringBuilder();
		if (preList.size() != 0)
			sb.append(" set");

		for (int i = 0; i < preList.size(); i++) {
			sb.append(" " + preList.get(i));
			if (i != preList.size() - 1)
				sb.append(" , ");
		}
		return sb.toString();
	}

	public String createSetStatement() {
		return createSetQuery();
	}

	public String getSelectStatement() {
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		for (int i = 0; i < this.selectCol.size(); i++) {
			sb.append(" " + this.selectCol.get(i));
			if (i != this.selectCol.size() - 1) {
				sb.append(",");
			}
		}
		return sb.toString();
	}
}
