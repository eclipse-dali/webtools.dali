/*******************************************************************************
 *  Copyright (c) 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class CascadeTranslator extends Translator implements OrmXmlMapper
{
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	private Translator[] children;	
	
	public CascadeTranslator(String domNameAndPath, EStructuralFeature aFeature) {
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
			createCascadeRefreshTranslator(),
		};
	}
	
	protected Translator createCascadeAllTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE__CASCADE_ALL, MAPPINGS_PKG.getICascade_All());
	}
	
	protected Translator createCascadePersistTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE__CASCADE_PERSIST, MAPPINGS_PKG.getICascade_Persist());
	}
	
	protected Translator createCascadeMergeTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE__CASCADE_MERGE, MAPPINGS_PKG.getICascade_Merge());
	}

	protected Translator createCascadeRemoveTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE__CASCADE_REMOVE, MAPPINGS_PKG.getICascade_Remove());
	}

	protected Translator createCascadeRefreshTranslator() {
		return new EmptyTagBooleanTranslator(CASCADE__CASCADE_REFRESH, MAPPINGS_PKG.getICascade_Refresh());
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlCascade();
	}
}
