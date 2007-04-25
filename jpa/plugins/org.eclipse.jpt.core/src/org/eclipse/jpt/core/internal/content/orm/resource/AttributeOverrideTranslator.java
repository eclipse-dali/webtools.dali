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
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.IAttributeOverride;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class AttributeOverrideTranslator extends Translator implements OrmXmlMapper
{
	private AttributeOverrideBuilder attributeOverrideBuilder;

	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	private Translator[] children;	
	private ColumnTranslator columnTranslator;
	
	public AttributeOverrideTranslator(String domNameAndPath, EStructuralFeature aFeature, AttributeOverrideBuilder attributeOverrideBuilder) {
		super(domNameAndPath, aFeature);
		this.attributeOverrideBuilder = attributeOverrideBuilder;
		this.columnTranslator = createColumnTranslator();
	}
	
	private ColumnTranslator createColumnTranslator() {
		return new ColumnTranslator(ATTRIBUTE_OVERRIDE_COLUMN, JPA_CORE_XML_PKG.getIXmlColumnMapping_ColumnForXml());
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
			columnTranslator,
		};
	}
	protected Translator createNameTranslator() {
		return new Translator(ATTRIBUTE_OVERRIDE_NAME, MAPPINGS_PKG.getIOverride_Name(), DOM_ATTRIBUTE);
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		IAttributeOverride attributeOverride = this.attributeOverrideBuilder.createAttributeOverride();
		this.columnTranslator.setColumnMapping(attributeOverride);
		return attributeOverride;
	}
	
	public interface AttributeOverrideBuilder {
		IAttributeOverride createAttributeOverride();
	}
}
