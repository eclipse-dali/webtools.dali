/*******************************************************************************
 * Copyright (c) 2006 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.Translator;

/**
 * This translator is to be used for empty xml tags that correspond
 * to a boolean attribute in the emf model.  
 * cascade-persist is an example from the orm.xsd:
 * 
 * 	<persistence-unit-defaults>
 * 		<cascade-persist/>        
 * 	</persistence-unit-defaults>  ==>  cascadePersist == true
 * 
 * vs.
 * 
 * 	<persistence-unit-defaults>  
 * 	</persistence-unit-defaults>  ==>  cascadePersist == false
 * 
 */
public class EmptyTagBooleanTranslator extends Translator
{
	public EmptyTagBooleanTranslator(String domNameAndPath, EStructuralFeature feature) {
		super(domNameAndPath, feature, EMPTY_TAG | BOOLEAN_FEATURE);
	}

	public EmptyTagBooleanTranslator(String domNameAndPath, EStructuralFeature aFeature, int style) {
		super(domNameAndPath, aFeature, style | EMPTY_TAG | BOOLEAN_FEATURE);
	}
	
	@Override
	public Object getMOFValue(EObject mofObject) {
		// I am overriding this method.  This is so the tag will be removed when 
		// the value is false.
		// I'm not sure if this is a bug in the ecore or maybe in the translators, 
		// but I really don't think that we should have to depend on the boolean
		// being "unset" to remove the tag.
		Boolean value = (Boolean) super.getMOFValue(mofObject);
		return (value == true) ? value : null;
	}

}
