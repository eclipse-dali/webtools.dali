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
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class SecondaryTableTranslator extends AbstractTableTranslator
{

	private IEntity entity;

	public SecondaryTableTranslator() {
		super(SECONDARY_TABLE, JPA_CORE_XML_PKG.getXmlEntity_SpecifiedSecondaryTables());
	}
	
	
	protected IEntity getEntity() {
		return this.entity;
	}
	
	void setEntity(IEntity entity) {
		this.entity = entity;
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return getEntity().createSecondaryTable(0);
	}
	
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createCatalogTranslator(),
			createSchemaTranslator(),
			createPrimaryKeyJoinColumnTranslator(),
			createUniqueConstraintTranslator(),
		};
	}
	
	//placeholder until we support primaryKeyJoinColumns
	protected Translator createPrimaryKeyJoinColumnTranslator() {
		return new Translator(SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMN, (EStructuralFeature) null);
	}

}
