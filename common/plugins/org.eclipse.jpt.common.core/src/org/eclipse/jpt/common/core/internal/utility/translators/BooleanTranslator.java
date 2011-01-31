/*******************************************************************************
 * Copyright (c) 2006, 2009 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class BooleanTranslator
	extends Translator
{
	public BooleanTranslator(String domPathAndNames, EStructuralFeature structuralFeature) {
		super(domPathAndNames, structuralFeature, BOOLEAN_FEATURE | BOOLEAN_LOWERCASE);
	}
	
	public BooleanTranslator(String domPathAndNames, EStructuralFeature structuralFeature, int style) {
		super(domPathAndNames, structuralFeature, BOOLEAN_FEATURE | BOOLEAN_LOWERCASE | style);
	}
	
	@Override
	public Object convertStringToValue(String string, EObject owner) {
		return Boolean.valueOf(string);
	}
	
	@Override
	public String convertValueToString(Object value, EObject owner) {
		return ((Boolean) value).toString();
	}
}
