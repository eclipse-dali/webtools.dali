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

public class CascadeTypeTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public CascadeTypeTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createCascadeAllTranslator(),
			createCascadePersistTranslator(),
			createCascadeMergeTranslator(),
			createCascadeRemoveTranslator(),
			createCascadeRefreshTranslator()
		};
	}
	
	private Translator createCascadeAllTranslator() {
		return new Translator(CASCADE_ALL, ORM_PKG.getCascadeType_CascadeAll());
	}
	
	private Translator createCascadePersistTranslator() {
		return new Translator(CASCADE_PERSIST, ORM_PKG.getCascadeType_CascadePersist());
	}
	
	private Translator createCascadeMergeTranslator() {
		return new Translator(CASCADE_MERGE, ORM_PKG.getCascadeType_CascadeMerge());
	}
	
	private Translator createCascadeRemoveTranslator() {
		return new Translator(CASCADE_REMOVE, ORM_PKG.getCascadeType_CascadeRemove());
	}

	private Translator createCascadeRefreshTranslator() {
		return new Translator(CASCADE_REFRESH, ORM_PKG.getCascadeType_CascadeRefresh());
	}
}
