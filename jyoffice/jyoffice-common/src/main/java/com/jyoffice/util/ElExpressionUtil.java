package com.jyoffice.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.el.ExpressionFactory;
import javax.el.ValueExpression;

import de.odysseus.el.ExpressionFactoryImpl;
import de.odysseus.el.util.SimpleContext;

public class ElExpressionUtil {

	public static Object execute(String expression, Map<String, String> param) {
		if (expression == null || expression.length() == 0) {
			return null;
		}

		ExpressionFactory factory = new ExpressionFactoryImpl();

		SimpleContext context = new SimpleContext();
		if(param != null){
			for (Entry<String, String> entry : param.entrySet()) {
				context.setVariable(entry.getKey(),
						factory.createValueExpression(entry.getValue(), String.class));
	
			}
		}
		ValueExpression e = factory.createValueExpression(context, expression, String.class);
		Object result = e.getValue(context);
		return result;
	}
	
	public static void main(String args[]){
		Map<String, String> param =  new HashMap<String, String>();
		param.put("empType", "jszxkz");
		System.out.println(ElExpressionUtil.execute("${empType eq 'jszxyg'}", param));
	}
}
