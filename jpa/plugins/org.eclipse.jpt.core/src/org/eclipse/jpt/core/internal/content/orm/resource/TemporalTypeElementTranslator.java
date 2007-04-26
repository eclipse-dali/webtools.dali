/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 * program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0 which accompanies this distribution, and is
 * available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import java.util.Collections;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.mappings.TemporalType;

/**
 * There is a bug in the translator framework that causes
 * enumerators in xml elements and enumerators in xml attributes
 * to be treated differently.  When the enumerator is an attribute
 * setting the model to the default causes the attribute to be removed.
 * With an element is causes the default literal to be placed in
 * the tag.
 * 
 * The problem is wrapped up in the emf unsettable attribute as well.
 * For attributes the eIsSet method returns false for the default value
 * For elements the eIsSet method returns true for the default value.
 * I don't want to have to use the unsettable option in emf since that would
 * require that I call different api.  I am not sure yet what the bug is in
 * the translator, so I have entered one ~KFM
 */
public class TemporalTypeElementTranslator extends EnumeratorTranslator
{
	
	public TemporalTypeElementTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, style);
	}
	
	@Override
	public Object getMOFValue(EObject mofObject) {
		TemporalType type = (TemporalType)  super.getMOFValue(mofObject);
		if (type == TemporalType.NULL) {
			return null;
		}
		return type;
	}
 
	@Override
	public List getMOFChildren(EObject mofObject) {
		List result = super.getMOFChildren(mofObject);
		if(result != null && result.size() > 0) {
			TemporalType type = (TemporalType) result.get(0);
			if(type.getValue() == TemporalType.NULL_VALUE) 
				result = Collections.EMPTY_LIST;
		}
		return result;
	}
}
