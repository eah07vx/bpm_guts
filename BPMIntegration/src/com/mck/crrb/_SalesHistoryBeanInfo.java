/**
 * 
 */
package com.mck.crrb;

import java.beans.MethodDescriptor;
import java.beans.ParameterDescriptor;
import java.beans.SimpleBeanInfo;
import java.lang.reflect.Method;

/**
 * Descriptor of SalesHistory bean
 * 
 * @author akatre
 * 
 */
public class _SalesHistoryBeanInfo extends SimpleBeanInfo {

	@SuppressWarnings("rawtypes")
	private Class beanClass = _SalesHistory.class;

	@Override
	public MethodDescriptor[] getMethodDescriptors() {
		try {
			MethodDescriptor methodDescriptor1 = getMethodDescription(
					"getSalesHistory",	//methodName
					new String[] { "invoiceURL (String)", "curPriceURL (String)", "histPriceURL (String)", "httpMethod (String)", "sslAlias (String)", "filtersJSON (String)", "isCurrentCorrection (boolean)", "sopDebug (boolean)" }, // parameters/arguments names
					new Class[] { String.class, String.class, String.class, String.class, String.class, String.class, boolean.class, boolean.class }); // corresponding parameter types/classes

			return new MethodDescriptor[] { methodDescriptor1 };
		} catch (Exception e) {
			return super.getMethodDescriptors();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private MethodDescriptor getMethodDescription(String methodName,
			String parameters[], Class classes[]) throws NoSuchMethodException {
		MethodDescriptor methodDescriptor = null;
		Method method = beanClass.getMethod(methodName, classes);

		if (method != null) {
			ParameterDescriptor paramDescriptors[] = new ParameterDescriptor[parameters.length];
			for (int i = 0; i < parameters.length; i++) {
				ParameterDescriptor param = new ParameterDescriptor();
				param.setShortDescription(parameters[i]);
				param.setDisplayName(parameters[i].substring(0, parameters[i].indexOf(" ")));
				paramDescriptors[i] = param;
			}
			methodDescriptor = new MethodDescriptor(method, paramDescriptors);
		}

		return methodDescriptor;
	}
}
