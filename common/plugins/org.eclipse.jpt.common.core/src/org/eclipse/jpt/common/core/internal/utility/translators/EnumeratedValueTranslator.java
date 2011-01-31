/*******************************************************************************
 *  Copyright (c) 2009, 2011 Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class EnumeratedValueTranslator
	extends Translator
{
	public EnumeratedValueTranslator(String domPathAndNames, EStructuralFeature structuralFeature) {
		super(domPathAndNames, structuralFeature);
	}
	
	public EnumeratedValueTranslator(String domPathAndNames, EStructuralFeature structuralFeature, int style) {
		super(domPathAndNames, structuralFeature, style);
	}
	
	
	protected abstract Iterable<?> getEnumeratedObjectValues();
	
	@Override
	public Object convertStringToValue(String string, EObject owner) {
		for (Object each : getEnumeratedObjectValues()) {
			if (each.toString().equals(string)) {
				return each;
			}
		}
		return null;
	}
	
	@Override
	public String convertValueToString(Object value, EObject owner) {
		return value.toString();
	}
}
