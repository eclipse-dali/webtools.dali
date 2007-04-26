/*******************************************************************************
 * Copyright (c) 2006, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the terms of
 * the Eclipse Public License v1.0, which accompanies this distribution and is available at
 * http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jpt.core.internal.mappings.IColumnMapping;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class ColumnTranslator extends AbstractColumnTranslator
	implements OrmXmlMapper
{		
	
	private IColumnMapping columnMapping;

	public ColumnTranslator(String domNameAndPath, EStructuralFeature aFeature) {
		super(domNameAndPath, aFeature);
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return getColumnMapping().getColumn();
	}	
	
	private IColumnMapping getColumnMapping() {
		return this.columnMapping;
	}
	
	void setColumnMapping(IColumnMapping columnMapping) {
		this.columnMapping = columnMapping;
	}
	
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createUniqueTranslator(),
			createNullableTranslator(),
			createInsertableTranslator(),
			createUpdatableTranslator(),
			createColumnDefinitionTranslator(),
			createTableTranslator(),
			createLengthTranslator(),
			createPrecisionTranslator(),
			createScaleTranslator(),
		};
	}
	
	protected Translator createLengthTranslator() {
		return new Translator(COLUMN__LENGTH, JPA_CORE_XML_PKG.getXmlColumn_LengthForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createPrecisionTranslator() {
		return new Translator(COLUMN__PRECISION, JPA_CORE_XML_PKG.getXmlColumn_PrecisionForXml(), DOM_ATTRIBUTE);
	}
	
	protected Translator createScaleTranslator() {
		return new Translator(COLUMN__SCALE, JPA_CORE_XML_PKG.getXmlColumn_ScaleForXml(), DOM_ATTRIBUTE);
	}
}
