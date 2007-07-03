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
import org.eclipse.jpt.core.internal.content.orm.resource.JoinColumnTranslator.JoinColumnBuilder;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.IMultiRelationshipMapping;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.IJoinTable.InverseJoinColumnOwner;
import org.eclipse.jpt.core.internal.mappings.IJoinTable.JoinColumnOwner;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class JoinTableTranslator extends AbstractTableTranslator
{

	private IMultiRelationshipMapping mapping;
	

	public JoinTableTranslator() {
		super(JOIN_TABLE, JPA_CORE_XML_PKG.getXmlMultiRelationshipMappingForXml_JoinTableForXml());
	}
	
	protected IMultiRelationshipMapping getMapping() {
		return this.mapping;
	}
	
	void setMapping(IMultiRelationshipMapping mapping) {
		this.mapping = mapping;
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return getMapping().getJoinTable();
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createCatalogTranslator(),
			createSchemaTranslator(),
			createJoinColumnsTranslator(),
			createInverseJoinColumnsTranslator(),
			createUniqueConstraintTranslator()
		};
	}
	
	protected Translator createJoinColumnsTranslator() {
		return new JoinColumnTranslator(
				JOIN_COLUMN,  
				JpaCoreMappingsPackage.eINSTANCE.getIJoinTable_SpecifiedJoinColumns(),
				buildJoinColumnsBuilder());
	}
	
	private JoinColumnBuilder buildJoinColumnsBuilder() {
		return new JoinColumnBuilder() {
			public IJoinColumn createJoinColumn() {
				return OrmFactory.eINSTANCE.createXmlJoinColumn(new JoinColumnOwner(getMapping().getJoinTable()));
			}
		};
	}
	
	protected Translator createInverseJoinColumnsTranslator() {
		return new JoinColumnTranslator(
				INVERSE_JOIN_COLUMN,  
				JpaCoreMappingsPackage.eINSTANCE.getIJoinTable_SpecifiedInverseJoinColumns(),
				buildInverseJoinColumnsBuilder());
	}	
	
	private JoinColumnBuilder buildInverseJoinColumnsBuilder() {
		return new JoinColumnBuilder() {
			public IJoinColumn createJoinColumn() {
				return OrmFactory.eINSTANCE.createXmlJoinColumn(new InverseJoinColumnOwner(getMapping().getJoinTable()));
			}
		};
	}

}
