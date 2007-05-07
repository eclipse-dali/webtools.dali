/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
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

public class DiscriminatorColumnTranslator extends AbstractColumnTranslator
	implements OrmXmlMapper
{	
	
	private IEntity entity;

	public DiscriminatorColumnTranslator() {
		super(ENTITY__DISCRIMINATOR_COLUMN, JPA_CORE_XML_PKG.getXmlEntityForXml_DiscriminatorColumnForXml());
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
			createColumnDefinitionTranslator(),
			createLengthTranslator(),
		};
	}
	
	protected Translator createDiscrminiatorTypeTranslator() {
		return new EnumeratorTranslator(DISCRIMINATOR_COLUMN__DISCRIMINATOR_TYPE, JPA_CORE_XML_PKG.getXmlDiscriminatorColumn_DiscriminatorTypeForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createLengthTranslator() {
		return new Translator(DISCRIMINATOR_COLUMN__LENGTH, JPA_CORE_XML_PKG.getXmlDiscriminatorColumn_SpecifiedLengthForXml(), DOM_ATTRIBUTE);
	}

	
}
