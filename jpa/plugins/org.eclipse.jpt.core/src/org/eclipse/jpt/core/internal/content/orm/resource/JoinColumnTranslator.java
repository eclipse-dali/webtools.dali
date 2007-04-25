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
import org.eclipse.jpt.core.internal.mappings.IJoinColumn;
import org.eclipse.wst.common.internal.emf.resource.IDTranslator;
import org.eclipse.wst.common.internal.emf.resource.Translator;

public class JoinColumnTranslator extends AbstractColumnTranslator
	implements OrmXmlMapper
{	
	private JoinColumnBuilder joinColumnBuilder;
	
	public JoinColumnTranslator(String domNameAndPath, EStructuralFeature aFeature, JoinColumnBuilder joinColumnBuilder) {
		super(domNameAndPath, aFeature);
		this.joinColumnBuilder = joinColumnBuilder;
	}

	@Override
	public EObject createEMFObject(String nodeName, String readAheadName) {
		return joinColumnBuilder.createJoinColumn();
	}	

	protected Translator[] createChildren() {
		return new Translator[] {
			IDTranslator.INSTANCE,
			createNameTranslator(),
			createReferencedColumnNameTranslator(),
			createUniqueTranslator(),
			createNullableTranslator(),
			createInsertableTranslator(),
			createUpdatableTranslator(),
			createColumnDefinitionTranslator(),
			createTableTranslator(),
		};
	}
	
	protected Translator createReferencedColumnNameTranslator() {
		return new Translator(REFERENCED_COLUMN_NAME, JPA_CORE_XML_PKG.getXmlJoinColumn_SpecifiedReferencedColumnNameForXml(), DOM_ATTRIBUTE);
	}

	public interface JoinColumnBuilder {
		IJoinColumn createJoinColumn();
	}
}
