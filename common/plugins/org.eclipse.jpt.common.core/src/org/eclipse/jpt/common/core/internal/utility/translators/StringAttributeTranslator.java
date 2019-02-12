/*******************************************************************************
 *  Copyright (c) 2013  Oracle. All rights reserved.
 *  This program and the accompanying materials are made available under the
 *  terms of the Eclipse Public License 2.0, which accompanies this distribution
 *  and is available at https://www.eclipse.org/legal/epl-2.0/
 *
 *  SPDX-License-Identifier: EPL-2.0
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Simple fix for String attribute translation:  maps null to null instead of ""
 */
public class StringAttributeTranslator
		extends Translator {
	
	public StringAttributeTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature, Translator.DOM_ATTRIBUTE);
	}
	
	public StringAttributeTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, Translator.DOM_ATTRIBUTE | style);
	}
	
	@Override
	public Object convertStringToValue(String strValue, EObject owner) {
		if (strValue == null) {
			return null;
		}
		return super.convertStringToValue(strValue, owner);
	}
}
