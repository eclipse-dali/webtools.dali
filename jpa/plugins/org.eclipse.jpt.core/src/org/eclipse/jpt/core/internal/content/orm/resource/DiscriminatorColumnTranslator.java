/*******************************************************************************
 *  Copyright (c) 2006 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.OrmPackage;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class DiscriminatorColumnTranslator extends Translator
	implements OrmXmlMapper
{	
	private Translator[] children;
	
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	protected static final OrmFactory JPA_CORE_XML_FACTORY =
		OrmFactory.eINSTANCE;
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	
	private IEntity entity;

	public DiscriminatorColumnTranslator() {
		super(ENTITY__DISCRIMINATOR_COLUMN, JPA_CORE_XML_PKG.getXmlEntityForXml_DiscriminatorColumnForXml(), END_TAG_NO_INDENT);
	}
	public Translator[] getChildren(Object target, int versionID) {
		if (children == null) {
			children = createChildren();
		}
		return children;
	}
		
	
	protected IEntity getEntity() {
		return this.entity;
	}
	
	void setEntity(IEntity entity) {
		this.entity = entity;
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return getEntity().getDiscriminatorColumn();
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createDiscrminiatorTypeTranslator(),
			createColumnDefinitationTranslator(),
			createLengthTranslator(),
		};
	}

	protected Translator createNameTranslator() {
		return new Translator(DISCRIMINATOR_COLUMN__NAME, JPA_CORE_XML_PKG.getXmlDiscriminatorColumn_SpecifiedNameForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createDiscrminiatorTypeTranslator() {
		return new EnumeratorTranslator(DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE, JPA_CORE_XML_PKG.getXmlDiscriminatorColumn_DiscriminatorTypeForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createColumnDefinitationTranslator() {
		return new Translator(DISCRIMINATOR_COLUMN__COLUMN_DEFINITION, JPA_CORE_XML_PKG.getXmlDiscriminatorColumn_ColumnDefinitionForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createLengthTranslator() {
		return new Translator(DISCRIMINATOR_COLUMN__LENGTH, JPA_CORE_XML_PKG.getXmlDiscriminatorColumn_SpecifiedLengthForXml(), DOM_ATTRIBUTE);
	}

	
}
