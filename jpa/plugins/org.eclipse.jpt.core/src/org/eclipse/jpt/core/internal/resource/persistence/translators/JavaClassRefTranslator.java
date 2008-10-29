/*******************************************************************************
 *  Copyright (c) 2006, 2007  Oracle. 
 *  All rights reserved.  This program and the accompanying materials are 
 *  made available under the terms of the Eclipse Public License v1.0 which 
 *  accompanies this distribution, and is available at 
 *  http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: 
 *  	Oracle - initial API and implementation
 *******************************************************************************/

package org.eclipse.jpt.core.internal.resource.persistence.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.resource.persistence.PersistencePackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class JavaClassRefTranslator extends Translator
{
	private static Translator[] children;
	
	
	public JavaClassRefTranslator(String domNameAndPath, EStructuralFeature feature, int style) {
		super(domNameAndPath, feature, style);
	}
	
	public JavaClassRefTranslator(String domNameAndPath, EStructuralFeature feature) {
		super(domNameAndPath, feature);
	}
	
	@Override
	protected Translator[] getChildren() {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	private static Translator[] createChildren() {
		return new Translator[] {
			new Translator(TEXT_ATTRIBUTE_VALUE, PersistencePackage.eINSTANCE.getXmlJavaClassRef_JavaClass())
		};
	}
}
