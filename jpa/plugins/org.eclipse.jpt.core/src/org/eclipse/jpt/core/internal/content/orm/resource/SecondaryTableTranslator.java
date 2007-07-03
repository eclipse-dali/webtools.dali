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
import org.eclipse.jpt.core.internal.content.orm.OrmFactory;
import org.eclipse.jpt.core.internal.content.orm.resource.PrimaryKeyJoinColumnTranslator.PrimaryKeyJoinColumnBuilder;
import org.eclipse.jpt.core.internal.mappings.IEntity;
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.jpt.core.internal.mappings.ISecondaryTable;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class SecondaryTableTranslator extends AbstractTableTranslator
{

	private IEntity entity;

	private ISecondaryTable secondaryTable;
	
	public SecondaryTableTranslator() {
		super(SECONDARY_TABLE, MAPPINGS_PKG.getIEntity_SpecifiedSecondaryTables());
	}
	
	
	protected IEntity getEntity() {
		return this.entity;
	}
	
	void setEntity(IEntity entity) {
		this.entity = entity;
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		this.secondaryTable = getEntity().createSecondaryTable(0);
		return this.secondaryTable;
	}
	
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createCatalogTranslator(),
			createSchemaTranslator(),
			createPrimaryKeyJoinColumnsTranslator(),
			createUniqueConstraintTranslator()
		};
	}
	
	protected Translator createPrimaryKeyJoinColumnsTranslator() {
		return new PrimaryKeyJoinColumnTranslator(
			SECONDARY_TABLE__PRIMARY_KEY_JOIN_COLUMN,  
				JpaCoreMappingsPackage.eINSTANCE.getISecondaryTable_SpecifiedPrimaryKeyJoinColumns(),
				buildPrimaryKeyJoinColumnsBuilder());
	}
	
	private PrimaryKeyJoinColumnBuilder buildPrimaryKeyJoinColumnsBuilder() {
		return new PrimaryKeyJoinColumnBuilder() {
			public IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn() {
				return OrmFactory.eINSTANCE.createXmlPrimaryKeyJoinColumn(new ISecondaryTable.PrimaryKeyJoinColumnOwner(secondaryTable));
			}
		};
	}

}
