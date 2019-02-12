/*******************************************************************************
 * Copyright (c) 2006, 2013 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0, which accompanies this distribution
 * and is available at https://www.eclipse.org/legal/epl-2.0/.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.common.core.internal.utility.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.common.utility.internal.ObjectTools;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * Translator for xml schema type "boolean" to object type Boolean.
 * Accepts text values of "true", "false", "0", and "1".  
 * Any text value that is none of those will return an object value of null.
 */
public class BooleanTranslator
		extends Translator {
	
	static final String TRUE_STRING = "true"; //$NON-NLS-1$
	static final String FALSE_STRING = "false"; //$NON-NLS-1$
	static final String ONE_STRING = "1"; //$NON-NLS-1$
	static final String ZERO_STRING = "0"; //$NON-NLS-1$
	
	
	public BooleanTranslator(String domPathAndNames, EStructuralFeature structuralFeature) {
		super(domPathAndNames, structuralFeature, BOOLEAN_FEATURE | BOOLEAN_LOWERCASE);
	}
	
	public BooleanTranslator(String domPathAndNames, EStructuralFeature structuralFeature, int style) {
		super(domPathAndNames, structuralFeature, BOOLEAN_FEATURE | BOOLEAN_LOWERCASE | style);
	}
	
	@Override
	public Object convertStringToValue(String string, EObject owner) {
		if (ObjectTools.equals(TRUE_STRING, string) || ObjectTools.equals(ONE_STRING, string)) {
			return Boolean.TRUE;
		}
		else if (ObjectTools.equals(FALSE_STRING, string) || ObjectTools.equals(ZERO_STRING, string)) {
			return Boolean.FALSE;
		}
		return null;
	}
	
	@Override
	public String convertValueToString(Object value, EObject owner) {
		return ((Boolean) value).toString();
	}
}
