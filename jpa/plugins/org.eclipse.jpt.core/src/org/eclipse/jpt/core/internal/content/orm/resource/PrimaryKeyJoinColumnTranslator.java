/*******************************************************************************
 * Copyright (c) 2007 Oracle. All rights reserved.
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
import org.eclipse.jpt.core.internal.mappings.IPrimaryKeyJoinColumn;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class PrimaryKeyJoinColumnTranslator extends AbstractColumnTranslator
	implements OrmXmlMapper
{	
	private PrimaryKeyJoinColumnBuilder joinColumnBuilder;
	
	public PrimaryKeyJoinColumnTranslator(String domNameAndPath, EStructuralFeature aFeature, PrimaryKeyJoinColumnBuilder joinColumnBuilder) {
		super(domNameAndPath, aFeature);
		this.joinColumnBuilder = joinColumnBuilder;
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return joinColumnBuilder.createPrimaryKeyJoinColumn();
	}	

	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createReferencedColumnNameTranslator(),
			createColumnDefinitionTranslator(),
		};
	}
	
	protected Translator createReferencedColumnNameTranslator() {
		return new Translator(REFERENCED_COLUMN_NAME, JPA_CORE_XML_PKG.getXmlPrimaryKeyJoinColumn_SpecifiedReferencedColumnNameForXml(), DOM_ATTRIBUTE);
	}

	public interface PrimaryKeyJoinColumnBuilder {
		IPrimaryKeyJoinColumn createPrimaryKeyJoinColumn();
	}
}
