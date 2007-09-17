/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class NamedQueryTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public NamedQueryTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createQueryTranslator(),
			createHintTranslator()
		};
	}

	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getNamedQuery_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createQueryTranslator() {
		return new Translator(QUERY, ORM_PKG.getNamedQuery_Query());
	}
	
	private Translator createHintTranslator() {
		return new QueryHintTranslator(HINT, ORM_PKG.getNamedQuery_Hints());
	}
}
