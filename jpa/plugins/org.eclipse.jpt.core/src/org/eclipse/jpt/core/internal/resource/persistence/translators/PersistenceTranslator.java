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

import org.eclipse.wst.common.internal.emf.resource.ConstantAttributeTranslator;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.RootTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PersistenceTranslator extends RootTranslator
	implements PersistenceXmlMapper
{
	public static PersistenceTranslator INSTANCE = new PersistenceTranslator();
	
	private Translator[] children;
	
	
	public PersistenceTranslator() {
		super(PERSISTENCE, PERSISTENCE_PKG.eINSTANCE.getXmlPersistence());
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
			createVersionTranslator(),
			new ConstantAttributeTranslator(XML_NS, PERSISTENCE_NS_URL),
			new ConstantAttributeTranslator(XML_NS_XSI, XSI_NS_URL),
			new ConstantAttributeTranslator(XSI_SCHEMA_LOCATION, PERSISTENCE_NS_URL + ' ' + PERSISTENCE_SCHEMA_LOC_1_0),
			createPersistenceUnitTranslator()
		};
	}
	
	private Translator createVersionTranslator() {
		return new Translator(VERSION, PERSISTENCE_PKG.getXmlPersistence_Version(), DOM_ATTRIBUTE);
	}
	
	private Translator createPersistenceUnitTranslator() {
		return new PersistenceUnitTranslator(PERSISTENCE_UNIT, PERSISTENCE_PKG.getXmlPersistence_PersistenceUnits());
	}
}
