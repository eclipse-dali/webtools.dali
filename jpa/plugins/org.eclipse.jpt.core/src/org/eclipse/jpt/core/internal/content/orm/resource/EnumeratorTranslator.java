/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;
import org.eclipse.wst.common.internal.emf.resource.TranslatorPath;

public class EnumeratorTranslator extends Translator
{
	
	public EnumeratorTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, style);
	}
	
	public EnumeratorTranslator(String domNameAndPath, EStructuralFeature aFeature, TranslatorPath translatorPath) {
		super(domNameAndPath, aFeature, translatorPath);
	}
	
	/**
	 * Overriding this because the default functionality is to return
	 * the toString of the Enumerator.  This returns the literal value
	 * while we want to return the name instead. 
	 * 
	 * An example is fetchType where the literal value is going to be "Lazy"
	 * and the name is "LAZY". The xml needs "LAZY" to be placed in it
	 * while the ui wants to display "Lazy"
	 */
	@Override
	public String convertValueToString(Object value, EObject owner) {
		Enumerator enumerator = (Enumerator) value;
		return enumerator.getName();
	}
}
