/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.eclipselink.core.internal.resource.elorm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.eclipselink.core.resource.elorm.EclipseLinkOrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class IdTranslator extends Translator 
	implements EclipseLinkOrmXmlMapper
{
	private Translator[] children;	
	
	
	public IdTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return EclipseLinkOrmFactory.eINSTANCE.createXmlIdImpl();
	}

	@Override
	public Translator[] getChildren(Object target, int versionID) {
		if (this.children == null) {
			this.children = createChildren();
		}
		return this.children;
	}
		
	protected Translator[] createChildren() {
		return new Translator[] {
			createNameTranslator(),
			createColumnTranslator(),
			createGeneratedValueTranslator(),
			createTemporalTranslator(),
			createTableGeneratorTranslator(),
			createSequenceGeneratorTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ECLIPSELINK_ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, ECLIPSELINK_ORM_PKG.getColumnMapping_Column());
	}
	
	private Translator createGeneratedValueTranslator() {
		return new GeneratedValueTranslator(GENERATED_VALUE, ECLIPSELINK_ORM_PKG.getXmlId_GeneratedValue());
	}
	
	private Translator createTemporalTranslator() {
		return new Translator(TEMPORAL, ECLIPSELINK_ORM_PKG.getXmlId_Temporal());
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ECLIPSELINK_ORM_PKG.getXmlId_TableGenerator());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ECLIPSELINK_ORM_PKG.getXmlId_SequenceGenerator());
	}
}
