/*******************************************************************************
 *  Copyright (c) 2006, 2007 Oracle. All rights reserved. This
 *  program and the accompanying materials are made available under the terms of
 *  the Eclipse Public License v1.0 which accompanies this distribution, and is
 *  available at http://www.eclipse.org/legal/epl-v10.html
 *  
 *  Contributors: Oracle. - initial API and implementation
 *******************************************************************************/
package org.eclipse.jpt.core.internal.resource.orm.translators;

import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class IdTranslator extends Translator 
	implements OrmXmlMapper
{
	private Translator[] children;	
	
	
	public IdTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
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
			createColumnTranslator(),
			createGeneratedValueTranslator(),
			createTemporalTranslator(),
			createTableGeneratorTranslator(),
			createSequenceGeneratorTranslator()
		};
	}
	
	private Translator createNameTranslator() {
		return new Translator(NAME, ORM_PKG.getId_Name(), DOM_ATTRIBUTE);
	}
	
	private Translator createColumnTranslator() {
		return new Translator(COLUMN, ORM_PKG.getId_Column());
	}
	
	private Translator createGeneratedValueTranslator() {
		return new GeneratedValueTranslator(GENERATED_VALUE, ORM_PKG.getId_GeneratedValue());
	}
	
	private Translator createTemporalTranslator() {
		return new Translator(TEMPORAL, ORM_PKG.getId_Temporal());
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, ORM_PKG.getId_TableGenerator());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, ORM_PKG.getId_SequenceGenerator());
	}
}
