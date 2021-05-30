package com.jupitor.image.jpimageloader.model;


public class ImageData extends ViewCredential {
	private String foreignKey;
	private String path;
	private boolean defaults;
	private String comment;
	public ImageData(String key, String path, boolean defaults, String comment) {
		super();
		this.foreignKey = key;
		this.path = path;
		this.defaults = defaults;
		this.comment = comment;
	}
	public ImageData(){
		super();
		this.foreignKey = null;
		this.path = "";
		this.defaults = false;
		this.comment = "";
	}
	
	public String getPath() {
		return path;
	}
	public boolean isDefaults() {
		return defaults;
	}
	public String getComment() {
		return comment;
	}

	public void setPath(String path) {
		this.path = path;
	}
	public void setDefaults(boolean defaults) {
		this.defaults = defaults;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getForeignKey() {
		return foreignKey;
	}
	public void setForeignKey(String foreignKey) {
		this.foreignKey = foreignKey;
	}
}
