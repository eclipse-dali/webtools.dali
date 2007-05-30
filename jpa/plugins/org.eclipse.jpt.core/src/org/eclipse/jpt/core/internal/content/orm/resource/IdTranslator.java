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
import org.eclipse.jpt.core.internal.mappings.IBasic;
import org.eclipse.jpt.core.internal.mappings.IId;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class IdTranslator extends AttributeMappingTranslator 
{
	private ColumnTranslator columnTranslator;

	public IdTranslator() {
		super(ID, NO_STYLE);
		this.columnTranslator = createColumnTranslator();
	}
	
	private ColumnTranslator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, JPA_CORE_XML_PKG.getIXmlColumnMapping_ColumnForXml());
	}	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		IId xmlId = JPA_CORE_XML_FACTORY.createXmlId();
		this.setId(xmlId);
		return xmlId;
	}
	
	protected void setId(IId id) {
		this.columnTranslator.setColumnMapping(id);		
	}

	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			columnTranslator,
			createGeneratedValueTranslator(),
			createTemporalTranslator(),
			createTableGeneratorTranslator(),
			createSequenceGeneratorTranslator(),
		};
	}
		
	private Translator createGeneratedValueTranslator() {
		return new GeneratedValueTranslator(GENERATED_VALUE, JpaCoreMappingsPackage.eINSTANCE.getIId_GeneratedValue());
	}	
	
	private Translator createTemporalTranslator() {
		return new TemporalTypeElementTranslator(ID__TEMPORAL, JpaCoreMappingsPackage.eINSTANCE.getIId_Temporal(), NO_STYLE);
	}
	
	private Translator createTableGeneratorTranslator() {
		return new TableGeneratorTranslator(TABLE_GENERATOR, JpaCoreMappingsPackage.eINSTANCE.getIId_TableGenerator());
	}
	
	private Translator createSequenceGeneratorTranslator() {
		return new SequenceGeneratorTranslator(SEQUENCE_GENERATOR, JpaCoreMappingsPackage.eINSTANCE.getIId_SequenceGenerator());
	}
}
