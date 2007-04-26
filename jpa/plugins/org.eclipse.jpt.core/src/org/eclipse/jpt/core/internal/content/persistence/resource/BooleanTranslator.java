/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.persistence.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class BooleanTranslator extends Translator
{
	public BooleanTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, BOOLEAN_FEATURE | BOOLEAN_LOWERCASE);
	}
	
	public BooleanTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, BOOLEAN_FEATURE | BOOLEAN_LOWERCASE | style);
	}
		
	public Object convertStringToValue(String strValue, EObject owner) {
		return Boolean.valueOf(strValue);
	}
	
	public String convertValueToString(Object value, EObject owner) {
		return ((Boolean) value).toString();
	}
}
