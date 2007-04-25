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
import org.eclipse.jpt.core.internal.content.orm.resource.JoinColumnTranslator.JoinColumnBuilder;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride;
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.jpt.core.internal.mappings.IAssociationOverride.JoinColumnOwner;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class AssociationOverrideTranslator extends Translator implements OrmXmlMapper
{
	private AssociationOverrideBuilder associationOverrideBuilder;

	private IAssociationOverride associationOverride;
	
	protected static final JpaCoreMappingsPackage MAPPINGS_PKG = 
		JpaCoreMappingsPackage.eINSTANCE;
	protected static final OrmPackage JPA_CORE_XML_PKG = 
		OrmPackage.eINSTANCE;
	
	
	private Translator[] children;	
	private JoinColumnTranslator joinColumnsTranslator;
	
	public AssociationOverrideTranslator(String domNameAndPath, EStructuralFeature aFeature, AssociationOverrideBuilder associationOverrideBuilder) {
		super(domNameAndPath, aFeature);
		this.associationOverrideBuilder = associationOverrideBuilder;
		this.joinColumnsTranslator = createJoinColumnsTranslator();
	}
	
	protected JoinColumnTranslator createJoinColumnsTranslator() {
		return new JoinColumnTranslator(
				JOIN_COLUMN,  
				JpaCoreMappingsPackage.eINSTANCE.getIAssociationOverride_SpecifiedJoinColumns(),
				buildJoinColumnsBuilder());
	}
	
	private JoinColumnBuilder buildJoinColumnsBuilder() {
		return new JoinColumnBuilder() {
			public IJoinColumn createJoinColumn() {
				return OrmFactory.eINSTANCE.createXmlJoinColumn(new JoinColumnOwner(associationOverride));
			}
		};
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
			joinColumnsTranslator,
		};
	}
	protected Translator createNameTranslator() {
		return new Translator(ATTRIBUTE_OVERRIDE_NAME, MAPPINGS_PKG.getIOverride_Name(), DOM_ATTRIBUTE);
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		this.associationOverride = this.associationOverrideBuilder.createAssociationOverride();
		return this.associationOverride;
	}
	
	public interface AssociationOverrideBuilder {
		IAssociationOverride createAssociationOverride();
	}
}
