/*******************************************************************************
 *  Copyright (c) 2007, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.eclipselink.core.resource.orm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class CascadeTypeTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public CascadeTypeTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createCascadeTypeImpl();
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createCascadeAllTranslator(),
			createCascadePersistTranslator(),
			createCascadeMergeTranslator(),
			createCascadeRemoveTranslator(),
			createCascadeRefreshTranslator()
		};
	}
	
	private Translator createCascadeAllTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_ALL, ECLIPSELINK_ORM_PKG.getCascadeType_CascadeAll());
	}
	
	private Translator createCascadePersistTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_PERSIST, ECLIPSELINK_ORM_PKG.getCascadeType_CascadePersist());
	}
	
	private Translator createCascadeMergeTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_MERGE, ECLIPSELINK_ORM_PKG.getCascadeType_CascadeMerge());
	}
	
	private Translator createCascadeRemoveTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_REMOVE, ECLIPSELINK_ORM_PKG.getCascadeType_CascadeRemove());
	}

	private Translator createCascadeRefreshTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE_REFRESH, ECLIPSELINK_ORM_PKG.getCascadeType_CascadeRefresh());
	}
}
