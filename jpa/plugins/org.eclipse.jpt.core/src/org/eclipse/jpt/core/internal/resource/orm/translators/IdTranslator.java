/*******************************************************************************
 *  Copyright (c) 2006, 2008 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.resource.orm.OrmFactory;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class IdTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public IdTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return OrmFactory.eINSTANCE.createXmlIdImpl();
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
	
	protected Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getXmlAttributeMapping_Name(), DOM_ATTRIBUTE);
	}
	
	protected Translator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, ORM_PKG.getColumnMapping_Column());
	}
	
	protected Translator createGeneratedValueTranslator() {
		return new GeneratedValueTranslator(GENERATED_VALUE, ORM_PKG.getXmlId_GeneratedValue());
	}
	
	protected Translator createTemporalTranslator() {
		return new Translator(TEMPORAL, ORM_PKG.getXmlConvertibleMapping_Temporal());
	}
	
	protected Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getXmlId_TableGenerator());
	}
	
	protected Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getXmlId_SequenceGenerator());
	}
}
