/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public abstract class AttributeMappingTranslator extends Translator 
	implements OrmXmlMapper 
{
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;

	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	protected static final OrmFactory JPA_CORE_XML_FACTORY =
		OrmFactory.eINSTANCE;
	
	
	private Translator[] children;
	
	
	public AttributeMappingTranslator(String domNameAndPath, int style) {
		super(domNameAndPath, JPA_CORE_XML_PKG.getXmlPersistentType_SpecifiedAttributeMappings(), style);
		dependencyFeature = JPA_CORE_XML_PKG.getXmlAttributeMapping_PersistentAttribute();
	}
	
	
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
	
	protected abstract Translator[] createChildren();
	
	
	protected Translator createNameTranslator() {
		return new AttributeNameTranslator();
	}
}
