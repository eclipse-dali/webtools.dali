/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.resource.common.translators.BooleanTranslator;
import org.eclipse.jpt.core.internal.resource.common.translators.EmptyTagBooleanTranslator;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class BasicTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public BasicTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlBasicImpl();
	}

	@Override
	protected Translator[] getChildren() {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createFetchTranslator(),
			createOptionalTranslator(),
			createColumnTranslator(), 
			createLobTranslator(),
			createTemporalTranslator(),
			createEnumeratedTranslator()
		};
	}
	
	protected Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createFetchTranslator() {
		return new Translator(FETCH, ORM_PKG.getXmlBasic_Fetch(), DOM_ATTRIBUTE);
	}
	
	protected Translator createOptionalTranslator() {
		return new BooleanTranslator(OPTIONAL, ORM_PKG.getXmlBasic_Optional(), DOM_ATTRIBUTE);
	}
	
	protected Translator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, ORM_PKG.getColumnMapping_Column());
	}
	
	protected Translator createLobTranslator() {
		return new EmptyTagBooleanTranslator(LOB, ORM_PKG.getXmlConvertibleMapping_Lob());
	}
	
	protected Translator createTemporalTranslator() {
		return new Translator(TEMPORAL, ORM_PKG.getXmlConvertibleMapping_Temporal());
	}
	
	protected Translator createEnumeratedTranslator() {
		return new Translator(ENUMERATED, ORM_PKG.getXmlConvertibleMapping_Enumerated());
	}
}
