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
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class BasicTranslator extends AttributeMappingTranslator 
{
	private ColumnTranslator columnTranslator;
	

	public BasicTranslator() {
		super(BASIC, NO_STYLE);
		this.columnTranslator = createColumnTranslator();
	}
	
	private ColumnTranslator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, JPA_CORE_XML_PKG.getIXmlColumnMapping_ColumnForXml());
	}
	
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		IBasic xmlBasic = JPA_CORE_XML_FACTORY.createXmlBasic();
		this.setBasic(xmlBasic);
		return xmlBasic;
	}
	
	protected void setBasic(IBasic basic) {
		this.columnTranslator.setColumnMapping(basic);		
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createFetchTypeTranslator(),
			createOptionalTranslator(),
			columnTranslator, 
			createLobTranslator(),
			createTemporalTranslator(),
			createEnumeratedTranslator(),
		};
	}

	private Translator createFetchTypeTranslator() {
		return new EnumeratorTranslator(FETCH, JpaCoreMappingsPackage.eINSTANCE.getIBasic_Fetch(), DOM_ATTRIBUTE);
	}

	private Translator createOptionalTranslator() {
		return new BooleanEnumeratorTranslator(OPTIONAL, JpaCoreMappingsPackage.eINSTANCE.getIBasic_Optional(), DOM_ATTRIBUTE);
	}
	
	private Translator createLobTranslator() {
		return new EmptyTagBooleanTranslator(LOB, JpaCoreMappingsPackage.eINSTANCE.getIBasic_Lob());
	}

	private Translator createTemporalTranslator() {
		return new TemporalTypeElementTranslator(TEMPORAL, JpaCoreMappingsPackage.eINSTANCE.getIBasic_Temporal(), NO_STYLE);
	}
	
	private Translator createEnumeratedTranslator() {
		return new EnumeratedTypeElementTranslator(BASIC__ENUMERATED, JpaCoreMappingsPackage.eINSTANCE.getIBasic_Enumerated(), NO_STYLE);
	}


}
