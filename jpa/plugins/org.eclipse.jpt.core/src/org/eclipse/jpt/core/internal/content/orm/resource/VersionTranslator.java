/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 * 
 * Contributors:
 *     Oracle - initial API and implementation
 ******************************************************************************/
package org.eclipse.jpt.core.internal.content.orm.resource;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.jpt.core.internal.mappings.IVersion;
import org.eclipse.jpt.core.internal.mappings.JpaCoreMappingsPackage;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class VersionTranslator extends AttributeMappingTranslator 
{
	private ColumnTranslator columnTranslator;

	public VersionTranslator() {
		super(VERSION, NO_STYLE);
		this.columnTranslator = createColumnTranslator();
	}
	
	private ColumnTranslator createColumnTranslator() {
		return new ColumnTranslator(COLUMN, JPA_CORE_XML_PKG.getIXmlColumnMapping_ColumnForXml());
	}
	
	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		IVersion version = JPA_CORE_XML_FACTORY.createXmlVersion();
		setVersion(version);
		return version;
	}
	
	protected void setVersion(IVersion version) {
		this.columnTranslator.setColumnMapping(version);		
	}
	
	@Override
	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			columnTranslator, 
			createTemporalTranslator(),
		};
	}

	private Translator createTemporalTranslator() {
		return new TemporalTypeElementTranslator(TEMPORAL, JpaCoreMappingsPackage.eINSTANCE.getIVersion_Temporal(), NO_STYLE);
	}

}
