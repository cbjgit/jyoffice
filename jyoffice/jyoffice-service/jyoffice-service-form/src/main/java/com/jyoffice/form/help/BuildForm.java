package com.jyoffice.form.help;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jyoffice.ca.SessionUtil;
import com.jyoffice.form.model.FrmElement;
import com.jyoffice.form.model.FrmForm;
import com.jyoffice.form.model.FrmOption;
import com.jyoffice.util.DateUtil;

public class BuildForm {

	public static String layout(FrmForm form, List<FrmElement> elementList, List<FrmOption> optList,HttpServletRequest request) {
		int cols = 0;
		int size = elementList.size();

		String title = "<div style=\"width:100%;text-align:center;font-size:20pt;\">"
				+ form.getFormName() + "</div>\n";
		StringBuffer bf = new StringBuffer(title);
		bf.append("<table class=\"formtable table-bordered\" style=\"\">");
		String required = "<font color='red'>*</font>";

		for (int i = 0; i < size; i++) {
			FrmElement elm = elementList.get(i);
			cols += elm.getColumnNumber();

			// 每行开始列或每行的所有列数之和大于表单规定的列数时换行
			if (cols == elm.getColumnNumber() || cols > form.getColumnNumber()) {
				if (i > 0) {
					if (cols > form.getColumnNumber()) {
						int c = (form.getColumnNumber() - (cols - elm.getColumnNumber())) * 2;
						bf.append("<td ").append("colspan=\"").append(c).append("\"></td>");
					}
					bf.append("</tr>\n");
				}
				bf.append("<tr>\n");
			}
			bf.append("\t<td>").append(elm.getFieldTitle());
			if (elm.getRequired() == 1) {
				bf.append(required);
			}
			bf.append("</td>\n");
			bf.append("\t<td colspan=\"").append(elm.getColumnNumber() * 2 - 1).append("\">");

			bf.append(buildControl(request,elm, optList));
			if (elm.getFieldDescption() != null) {
				bf.append(elm.getFieldDescption());
			}
			bf.append("</td>\n");

			// 每行的所有列数之和等于表单规定的列数时换行
			if (cols == form.getColumnNumber()) {
				cols = 0;
			}
			// 表格最后一列加上结束符
			if (i == size - 1) {
				bf.append("</tr>\n");
			}
		}
		bf.append("</table>");

		bf.append("<div style=\"padding:10px;text-align:left\">").append(form.getDescption())
				.append("</div>");

		return bf.toString();
	}

	private static StringBuffer buildControl(HttpServletRequest request, FrmElement elm, List<FrmOption> optList) {
		if (ElementType.FIELD_TEXT.equals(elm.getFieldType())) {

			return text(elm,request);

		} else if (ElementType.FIELD_TEXTAREA.equals(elm.getFieldType())) {
			return textarea(elm);

		} else if (ElementType.FIELD_SELECT.equals(elm.getFieldType())) {

			return select(elm, optList);

		} else if (ElementType.FIELD_RADIO.equals(elm.getFieldType())
				|| ElementType.FIELD_CHECKBOX.equals(elm.getFieldType())) {
			return radiochecbox(elm, optList);
		}
		return new StringBuffer();
	}

	@SuppressWarnings("unchecked")
	private static StringBuffer text(FrmElement elm,HttpServletRequest request) {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map = null;
		try {
			map = mapper.readValue(elm.getOtherParam(), Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}

		StringBuffer bf = new StringBuffer();
		bf.append("<input datetype=\"").append(elm.getDataType())
				.append("\" class=\"form-control\" name=\"").append(elm.getFieldName()).append("\"");
		if (ElementType.DATA_EMAIL.equals(elm.getDataType())) {
			bf.append(" type=\"email\" ");
		} else if (ElementType.DATA_INTEGER.equals(elm.getDataType())) {
			bf.append(" type=\"digits\" ");
		} else if (ElementType.DATA_FLOAT.equals(elm.getDataType())) {
			bf.append(" type=\"number\" ");
		} else {
			bf.append(" type=\"text\" ");
		}

		if (elm.getDataType().equals("date")) {
			if ("yyyy-MM-dd".equals(map.get("datestyle"))) {
				bf.append(" data-date-format=\"yyyy-mm-dd\"");
			} else if ("yyyy-MM-dd HH:mm".equals(map.get("datestyle"))) {
				bf.append(" data-date-format=\"yyyy-mm-dd HH:ii\"");
			} else if ("HH:mm".equals(map.get("datestyle"))) {
				bf.append(" data-date-format=\"HH:ii\"");
			}
		}

		if ("sysdate".equals(elm.getDefaultValue())) {
			bf.append(" value=\"");
			if (elm.getDataType().equals("date") && map != null) {
				bf.append(DateUtil.formatDate(new Date(), map.get("datestyle")));
			} else {
				bf.append(DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
			}
			bf.append("\"");
		} else if ("login".equals(elm.getDefaultValue())) {
			bf.append(" value=\"").append(SessionUtil.getUserId(request)).append("\"");
		} else if ("loginJob".equals(elm.getDefaultValue())) {
			bf.append(" value=\"").append(SessionUtil.getUser(request)).append("\"");
		} else if ("loginDept".equals(elm.getDefaultValue())) {
			bf.append(" value=\"").append(SessionUtil.getUser(request)).append("\"");
		}

		required(elm, bf);
		flowvar(elm, bf);
		
		bf.append(">");
		return bf;
	}

	@SuppressWarnings("unchecked")
	private static StringBuffer textarea(FrmElement elm) {
		StringBuffer bf = new StringBuffer();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> param = new HashMap<String, String>();
		param.put("width", "500");
		param.put("height", "100");
		try {
			param = mapper.readValue(elm.getOtherParam(), Map.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		bf.append("<textarea class=\"form-control\" name=\"").append(elm.getFieldName())
				.append("\" style=\"width:").append(param.get("width")).append("px;height:")
				.append(param.get("height")).append("px\"");
		required(elm, bf);
		bf.append("></textarea>");
		return bf;
	}

	private static StringBuffer select(FrmElement elm, List<FrmOption> optList) {
		StringBuffer bf = new StringBuffer();
		bf.append("<select class=\"form-control\" style=\"width:190px;\" name=\"")
				.append(elm.getFieldName()).append("\"");

		required(elm, bf);
		flowvar(elm, bf);
		
		bf.append(">");
		bf.append("<option value=\"\">--请选择--</option>");
		if (elm.getDataSource() == 1) {
			for (int o = 0; o < optList.size(); o++) {
				if (optList.get(o).getFieldId() == elm.getId()) {
					bf.append("<option value=\"").append(optList.get(o).getOptionValue()).append("\">")
							.append(optList.get(o).getOptionText()).append("</option>");
				}
			}
		}
		bf.append("</select>");
		return bf;
	}

	@SuppressWarnings("unchecked")
	private static StringBuffer radiochecbox(FrmElement elm, List<FrmOption> optList) {
		StringBuffer bf = new StringBuffer();
		ObjectMapper mapper = new ObjectMapper();
		Map<String, String> map;
		String br = "&nbsp;&nbsp;";
		try {
			map = mapper.readValue(elm.getOtherParam(), Map.class);
			if ("2".equals(map.get("showtype"))) {
				br = "<br/>";
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		int n = 0;
		if (elm.getDataSource() == 1) {
			for (int o = 0; o < optList.size(); o++) {
				if (optList.get(o).getFieldId() == elm.getId()) {
					
					bf.append("<input type=\"").append(elm.getFieldType()).append("\"");
					bf.append(" name=\"").append(elm.getFieldName()).append("\"");
					bf.append(" value=\"").append(optList.get(o).getOptionValue()).append("\"");
					
					if(n == 0)
						required(elm, bf);
					
					flowvar(elm, bf);
					
					bf.append(">&nbsp;");
					bf.append(optList.get(o).getOptionText()).append(br);
					
					n++;
				}
			}
			bf.append("<label for=\"").append(elm.getFieldName()).append("\" class=\"error\"></label>");
		}
		return bf;
	}

	private static void required(FrmElement elm, StringBuffer bf) {
		if (elm.getRequired() == 1) {
			bf.append(" required ");
		}
	}
	
	private static void flowvar(FrmElement elm, StringBuffer bf) {
		if (elm.getFlowVar() == 1) {
			bf.append(" flowvar=\"true\" ");
		}
	}
	

}
