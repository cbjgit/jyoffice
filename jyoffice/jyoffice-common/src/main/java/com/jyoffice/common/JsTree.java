package com.jyoffice.common;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/*// Expected format of the node (there are no required fields)
{
  id          : "string" // will be autogenerated if omitted
  text        : "string" // node text
  icon        : "string" // string for custom
  state       : {
    opened    : boolean  // is the node open
    disabled  : boolean  // is the node disabled
    selected  : boolean  // is the node selected
  },
  children    : []  // array of strings or objects
  li_attr     : {}  // attributes for the generated LI node
  a_attr      : {}  // attributes for the generated A node
}*/
public class JsTree {

	private String id;
	
	private String parent;
	
	private String text;
	
	private String icon;
	
	private boolean children;
	
	private Map<String, String> state;
	
	private Map<String, String> aAttr;
	
	private Map<String, String> liAttr;

	public JsTree(){}
	public JsTree(String id,String parent,String text){
		this.id = id;
		this.parent = parent;
		this.text = text;
	}
	public JsTree(String id,String text){
		this.id = id;
		this.text = text;
	}
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Map<String, String> getState() {
		return state;
	}

	public void setState(Map<String, String> state) {
		this.state = state;
	}

	public Map<String, String> getaAttr() {
		return aAttr;
	}

	public boolean isChildren() {
		return children;
	}
	public void setChildren(boolean children) {
		this.children = children;
	}
	public void setaAttr(Map<String, String> aAttr) {
		this.aAttr = aAttr;
	}

	public Map<String, String> getLiAttr() {
		return liAttr;
	}

	public void setLiAttr(Map<String, String> liAttr) {
		this.liAttr = liAttr;
	}
	
	public String toJson() {
		try {
			return new ObjectMapper().setSerializationInclusion(Include.NON_NULL).writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return "{}";
	}
}
